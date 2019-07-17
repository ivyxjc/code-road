package xyz.ivyxjc.coderoad.netty.demo

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.util.CharsetUtil
import xyz.ivyxjc.coderoad.netty.utils.loggerFor
import java.time.LocalDateTime


fun main() {
    val eventGroup = NioEventLoopGroup()
    try {
        val bootstrap = Bootstrap()
        bootstrap.group(eventGroup).channel(NioSocketChannel::class.java)
            .handler(MyClientInitializer())
        val channelFuture = bootstrap.connect("127.0.0.1", 8123).sync()
        channelFuture.channel().closeFuture().sync()
    } finally {
        eventGroup.shutdownGracefully()
    }

}

private class MyClientInitializer : ChannelInitializer<SocketChannel>() {

    override fun initChannel(ch: SocketChannel?) {
        val pipeline = ch!!.pipeline()
        pipeline.addLast(LengthFieldBasedFrameDecoder(Int.MAX_VALUE, 0, 4, 0, 4))
        pipeline.addLast(LengthFieldPrepender(4))
        pipeline.addLast(StringDecoder(CharsetUtil.UTF_8))
        pipeline.addLast(StringEncoder(CharsetUtil.UTF_8))
        pipeline.addLast(MyClientHandler())
    }
}

private class MyClientHandler : SimpleChannelInboundHandler<String>() {
    companion object {
        private val log = loggerFor(MyClientInitializer::class.java)
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        log.info("{}", ctx!!.channel().remoteAddress())
        log.info("client output: {}", msg)
        ctx.writeAndFlush("from client: ${LocalDateTime.now()}")
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        ctx!!.writeAndFlush("from client: first msg")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        log.error("client exception caught", cause)
        ctx!!.close()
    }
}

