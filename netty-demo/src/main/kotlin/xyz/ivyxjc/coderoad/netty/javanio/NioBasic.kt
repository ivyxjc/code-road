package xyz.ivyxjc.coderoad.netty.javanio

import java.nio.ByteBuffer

fun main() {
    channelDemo()
    scatterDemo()
    transferDemo()
}


private fun channelDemo() {
    val file = buildFile()

    file.use {
        val inChannel = it.channel

        val buf = ByteBuffer.allocate(48)

        var bytesRead = inChannel.read(buf)

        while (bytesRead != -1) {
            var count = 0
            println("\nRead $bytesRead")
            buf.flip()  //make buffer ready for read
            while (buf.hasRemaining()) {
                print(buf.get().toChar())
                if (count == 0 && buf.position() == buf.limit()) {
                    buf.rewind()  //make position back to 0
                    count++
                }
            }
            /*
            clear(): position back to 0 and the limit to capacity, so you can write into the blank buf
            compact(): copied all unread data into the beginning of th Buffer and set position to right
            after the last unread element. And set limit to capacity, so you can still read unread element.
             */
            buf.clear()  //make buffer ready for write
            bytesRead = inChannel.read(buf)
        }
    }
}


private fun scatterDemo() {
    val header = ByteBuffer.allocate(128)
    val body = ByteBuffer.allocate(1024)
    val bufferArray = arrayOf(header, body)

    val file = buildFile()

    file.use {
        val inChannel = it.channel
        /*
        scattering fill up one buffer before moving on to he next,
        So you have the fixed-size header, you can use this way
         */
        inChannel.read(bufferArray)

        println("\nRead head\n")
        header.flip()  //make buffer ready for read
        while (header.hasRemaining()) {
            print(header.get().toChar())
        }

        /*
        if size of nio-data.txt is smaller than header's limit
        body is empty
         */
        println("\nRead body\n")
        body.flip()  //make buffer ready for read
        while (body.hasRemaining()) {
            print(body.get().toChar())
        }
    }
}


/**
 * this method will write to build directory
 */
private fun transferDemo() {
    val fromFile = buildFile("/data/fromFile.txt", "rw")
    val toFile = buildOutputFile("/data/toFile.txt", true)

    try {
        val fromChannel = fromFile.channel
        val toChannel = toFile.channel
        val position = 0L
        val count = fromChannel.size()
        fromChannel.transferTo(position, count, toChannel)
        println(toChannel.position())
    } finally {
        fromFile.close()
        toFile.close()
    }
}

