package xyz.ivyxjc.coderoad.netty.javanio

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel


private fun basicChannelRead() {
    val rf = buildFile("/data/nio-data.txt", "rw")
    rf.use {
        val inChannel = rf.channel

        val buf = ByteBuffer.allocate(48)
        var byteReads = inChannel.read(buf)
        while (byteReads != -1) {
            buf.flip()
            while (buf.hasRemaining()) {
                print(buf.get().toChar())
            }
            buf.clear()
            byteReads = inChannel.read(buf)
        }
    }
}

private fun scatterDemo() {
    val rf = buildFile("/data/nio-data.txt", "rw")

    val header = ByteBuffer.allocate(128)
    val body = ByteBuffer.allocate(1024)

    val bufferArray = arrayOf(header, body)
    rf.use {
        val channel = rf.channel
        channel.read(bufferArray)

        println("\nRead header\n")
        header.flip()
        while (header.hasRemaining()) {
            print(header.get().toChar())
        }

        println("\nRead body\n")
        body.flip()  //make buffer ready for read
        while (body.hasRemaining()) {
            print(body.get().toChar())
        }
    }
}

private fun transferDemo() {
    val fromFile = buildFile("/data/fromFile.txt", "rw")
    val toFile = buildFile("/data/toFile.txt", "rw")
    try {
        val fromChannel = fromFile.channel
        val toChannel = toFile.channel
        val position = 0L
        val count = fromChannel.size()
        toChannel.transferFrom(fromChannel, position, count)
    } finally {
        fromFile.close()
        toFile.close()
    }
}

private fun selectorDemo() {
    val selector = Selector.open()

    val serverChannel = ServerSocketChannel.open()
    serverChannel.configureBlocking(true)
    try {
        serverChannel.bind(InetSocketAddress("localhost", 8123))

        while (true) {
            val readyChannel = selector.selectNow()

            if (readyChannel == 0) {
                continue
            }

            val selectedKeys = selector.selectedKeys()
            val keyIterator = selectedKeys.iterator()

            while (keyIterator.hasNext()) {
                val key = keyIterator.next()
                when {
                    key.isAcceptable -> {
                        println("accept")
                    }
                    key.isConnectable -> {
                        println("connect")
                    }
                    key.isReadable -> {
                        println("read")
                    }
                    key.isWritable -> {
                        println("write")
                    }
                }
                keyIterator.remove()
            }
        }


    } finally {
        serverChannel.close()
        selector.close()
    }

}


fun main() {
//    basicChannelRead()
//    scatterDemo()
//    transferDemo()
//    selectorDemo()

}

