package com.example

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*

/**
 * created by byron at 2023/10/20
 */

fun main() {
    embeddedServer(Netty, 8080) {

        // 拦截器
        intercept(ApplicationCallPipeline.Call) {
            context.attributes[AttributeKey("1243")]
            proceed()
        }

        // 转json
        install(ContentNegotiation) {
            json()
        }
        install(StatusPages) {
            // 异常 http code
            status(*HttpStatusCode.allStatusCodes.filter { httpStatusCode -> !httpStatusCode.isSuccess() }
                .toTypedArray()) { call, status ->
                call.respond(status, Resp.Fail(status.description))
            }
            // 异常处理
            exception<Throwable> { call, cause ->
                call.respond(Resp.Fail(cause.message))
            }
        }
        routing {
            get("/") {
                call.respondText("root")
            }
            get("/test") {
                call.respondText("OK!")
            }
        }
    }.start(wait = true)
}
