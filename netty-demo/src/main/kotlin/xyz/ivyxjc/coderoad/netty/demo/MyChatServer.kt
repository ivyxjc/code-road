package xyz.ivyxjc.coderoad.netty.demo

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.group.DefaultChannelGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.util.CharsetUtil
import io.netty.util.concurrent.GlobalEventExecutor
import xyz.ivyxjc.coderoad.netty.utils.loggerFor


fun main() {
    val bossGroup = NioEventLoopGroup()
    val workerGroup = NioEventLoopGroup()

    try {
        val serverBootstrap = ServerBootstrap()
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel::class.java)
            .childHandler(MyChatServerInitializer())

        val channelFuture = serverBootstrap.bind(8124).sync()
        channelFuture.channel().closeFuture().sync()
    } finally {
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }
}

private class MyChatServerInitializer : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel?) {
        val pipeline = ch!!.pipeline()
        pipeline.addLast(DelimiterBasedFrameDecoder(4096, *Delimiters.lineDelimiter()))
//        pipeline.addLast(LengthFieldBasedFrameDecoder(Int.MAX_VALUE, 0, 4, 0, 4))
//        pipeline.addLast(LengthFieldPrepender(4))
        pipeline.addLast(StringDecoder(CharsetUtil.UTF_8))
        pipeline.addLast(StringEncoder(CharsetUtil.UTF_8))
        pipeline.addLast(MyChatServerHandler())
    }
}

private class MyChatServerHandler : SimpleChannelInboundHandler<String>() {

    companion object {
        private val channelGroup = DefaultChannelGroup(GlobalEventExecutor.INSTANCE)
        private val log = loggerFor(MyChatServerHandler::class.java)
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        val channel = ctx!!.channel()
        log.info("{}, {}", channel.remoteAddress(), msg)
        channel.writeAndFlush("hello world")
        println(channelGroup.size)
        channelGroup.forEach {
            if (it != channel) {
                it.writeAndFlush("${channel.remoteAddress()} send msg: $msg\n")
            } else {
                it.writeAndFlush("[MySelf]\n")
            }
        }
    }

    override fun handlerAdded(ctx: ChannelHandlerContext?) {
        log.info("handlerAdded")
        val channel = ctx!!.channel()
        channelGroup.writeAndFlush("[server]${channel.remoteAddress()} is online\n")
        log.info("handlerAdded after write")
        channelGroup.add(channel)
        println(channelGroup.size)
    }

    override fun handlerRemoved(ctx: ChannelHandlerContext?) {
        log.info("handlerRemoved")
        val channel = ctx!!.channel()
        channelGroup.writeAndFlush("[server]${channel.remoteAddress()} is offline\n")
        println(channelGroup.size)
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        val channel = ctx!!.channel()
        log.info("${channel.remoteAddress()} is online")
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        val channel = ctx!!.channel()
        log.info("${channel.remoteAddress()} is offline")
    }


    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        log.info("server exception", cause)
        ctx!!.close()
    }


}






