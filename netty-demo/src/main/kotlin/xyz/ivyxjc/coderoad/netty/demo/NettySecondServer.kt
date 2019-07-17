package xyz.ivyxjc.coderoad.netty.demo

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.util.CharsetUtil
import xyz.ivyxjc.coderoad.netty.utils.loggerFor
import java.util.*

fun main() {
    val bossGroup = NioEventLoopGroup()
    val workerGroup = NioEventLoopGroup()

    try {
        val serverBootstrap = ServerBootstrap()
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel::class.java)
            .childHandler(MyServerInitializer())

        val channelFuture = serverBootstrap.bind(8123).sync()
        channelFuture.channel().closeFuture().sync()
    } finally {
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }
}

private class MyServerInitializer : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel?) {
        val pipeline = ch!!.pipeline()
        pipeline.addLast(LengthFieldBasedFrameDecoder(Int.MAX_VALUE, 0, 4, 0, 4))
        pipeline.addLast(LengthFieldPrepender(4))
        pipeline.addLast(StringDecoder(CharsetUtil.UTF_8))
        pipeline.addLast(StringEncoder(CharsetUtil.UTF_8))
        pipeline.addLast(MyServerHandler())
    }
}

private class MyServerHandler : SimpleChannelInboundHandler<String>() {
    companion object {
        val log = loggerFor(MyServerHandler::class.java)
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        log.info("{}, {}", ctx!!.channel().remoteAddress(), msg)
        ctx.channel().writeAndFlush("from server:${UUID.randomUUID()}")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        log.info("server exception", cause)
        ctx!!.close()
    }
}






