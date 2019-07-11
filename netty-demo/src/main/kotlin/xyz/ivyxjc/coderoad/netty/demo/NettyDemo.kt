package xyz.ivyxjc.coderoad.netty.demo

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.http.*
import io.netty.util.CharsetUtil
import xyz.ivyxjc.coderoad.netty.utils.loggerFor
import java.lang.Thread.sleep
import java.net.URI


fun main() {
    val baseGroup = NioEventLoopGroup()
    val workerGroup = NioEventLoopGroup()
    try {
        val bootstrap = ServerBootstrap()
        bootstrap.group(baseGroup, workerGroup).channel(NioServerSocketChannel::class.java)
            .childHandler(TestServerInitializer())

        val channelFuture = bootstrap.bind(9999).sync()
        channelFuture.channel().closeFuture().sync()
    } finally {
        baseGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }
}

class TestServerInitializer : ChannelInitializer<SocketChannel>() {

    override fun initChannel(ch: SocketChannel?) {
        val pipeline = ch!!.pipeline()
        pipeline.addLast("httpServerCodec", HttpServerCodec())
        pipeline.addLast("testHttpServerHandler", TestHttpServerHandler())
    }
}

class TestHttpServerHandler : SimpleChannelInboundHandler<HttpObject>() {
    companion object {
        private val log = loggerFor(TestHttpServerHandler::class.java)
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: HttpObject?) {
        log.info("execute channelRead0")
        log.info("type of msg is ${msg!!.javaClass}")
        log.info("remote port is ${ctx!!.channel().remoteAddress()}")
        sleep(8000L)
        if (msg is HttpRequest) {
            log.info("request method name is ${msg.method().name()}")
            log.debug("request uri is ${msg.uri()}")
            log.info("remote port is ${ctx!!.channel().remoteAddress()}")
            val uri = URI(msg.uri())
            if ("/favicon.ico".equals(uri.path)) {
                log.debug("request favicon.ico")
            }
            val content = Unpooled.copiedBuffer("Hello world", CharsetUtil.UTF_8)
            val response = DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content)
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes())
            ctx!!.writeAndFlush(response)
            ctx.channel().close()
        }
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        log.info("channel active")
        super.channelActive(ctx)
    }

    override fun channelRegistered(ctx: ChannelHandlerContext?) {
        log.info("channel registered")
        super.channelRegistered(ctx)
    }

    override fun handlerAdded(ctx: ChannelHandlerContext?) {
        log.info("handler added")
        super.handlerAdded(ctx)
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        log.info("channel inactive")
        super.channelInactive(ctx)
    }

    override fun channelUnregistered(ctx: ChannelHandlerContext?) {
        log.info("channel unregistered")
        super.channelUnregistered(ctx)
    }

}