package xyz.ivyxjc.coderoad.netty.demo

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.util.CharsetUtil
import xyz.ivyxjc.coderoad.netty.utils.loggerFor
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader


fun main() {
    val eventGroup = NioEventLoopGroup()
    try {
        val bootstrap = Bootstrap()
        bootstrap.group(eventGroup).channel(NioSocketChannel::class.java)
            .handler(MyChatClientInitializer())
        val channel = bootstrap.connect("127.0.0.1", 8124).sync().channel()
        val br = BufferedReader(InputStreamReader(System.`in`) as Reader?)
        while (true) {
            channel.writeAndFlush(br.readLine() + "\r\n")
        }
    } finally {
        eventGroup.shutdownGracefully()
    }

}

class MyChatClientInitializer : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel?) {
        val pipeline = ch!!.pipeline()
        pipeline.addLast(DelimiterBasedFrameDecoder(4096, *Delimiters.lineDelimiter()))
        pipeline.addLast(StringDecoder(CharsetUtil.UTF_8))
        pipeline.addLast(StringEncoder(CharsetUtil.UTF_8))
        pipeline.addLast(MyChatClientHandler())
    }
}

class MyChatClientHandler : SimpleChannelInboundHandler<String>() {
    companion object {
        private val log = loggerFor(MyChatClientHandler::class.java)
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        println(msg)
        log.info(msg)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        log.info("server exception", cause)
        ctx!!.close()
    }
}