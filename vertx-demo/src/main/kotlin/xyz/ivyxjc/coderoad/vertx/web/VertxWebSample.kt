package xyz.ivyxjc.coderoad.vertx.web

import io.vertx.core.Vertx
import io.vertx.ext.web.Router


fun main() {
    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()
    val router = Router.router(vertx)

    val route = router.route("/some/path")
    route.handler {
        val response = it.response()
        response.isChunked = true
        response.write("router1\n")
        it.vertx().setTimer(1000) { tid -> it.next() }
    }

    route.handler {
        val response = it.response()
        response.write("route2\n")
        it.vertx().setTimer(2000) { tid -> it.next() }

    }
    route.handler {
        val response = it.response()
        response.write("route3")
        response.end()
    }

    server.requestHandler(router).listen(8123)


}