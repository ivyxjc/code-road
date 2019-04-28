package xyz.ivyxjc.coderoad.vertx.web

import io.vertx.core.Vertx
import io.vertx.ext.web.Router

fun main() {
    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()
    val router = Router.router(vertx)

    val route = router.route("/some")

    route.handler {
        val response = it.response()
        val user = it.request().getParam("user")
        val time = it.request().getParam("time")
        println(user)
        println(time)
        response.end(user)

    }

    server.requestHandler(router).listen(8124)

}