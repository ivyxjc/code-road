package xyz.ivyxjc.coderoad.netty.javanio

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel


fun main() {
    serverSocketChannel()
    Thread.sleep(2000)
    socketChannel()
//    writeToSocketChannel()
}


fun socketChannel() {
    val socketChannel = SocketChannel.open()
    socketChannel.use {
        it.connect(InetSocketAddress(8234))
        val buf = ByteBuffer.allocate(48)
        var bytesRead = socketChannel.read(buf)
        while (bytesRead != -1) {
            while (buf.hasRemaining()) {
                print(buf.get().toChar())
            }
            bytesRead = socketChannel.read(buf)
            print("hello")
        }
    }
}

fun writeToSocketChannel() {
    val newData = "New String to write to file..." + System.currentTimeMillis()
    val socketChannel = SocketChannel.open()
    socketChannel.use {
        val buf = ByteBuffer.allocate(48)
        buf.clear()
        buf.put(newData.toByteArray())

        buf.flip()
        while (buf.hasRemaining()) {
            it.write(buf)
        }
    }
}

fun serverSocketChannel() {
    val serverSocketChannel = ServerSocketChannel.open()
    serverSocketChannel.socket().bind(InetSocketAddress(8234))
    //non-blocking mode
    serverSocketChannel.configureBlocking(false)
    while (true) {
        val socketChannel = serverSocketChannel.accept()
        if (socketChannel != null) {
            println("get one connection")
            val buf = ByteBuffer.allocate(48)
            buf.put("hello world".toByteArray())
            socketChannel.write(buf)
            socketChannel.close()
        }
    }

}