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

/**
 * created by byron at 2023/10/20
 */

fun main() {
    embeddedServer(Netty, 8080) {

        // 拦截器
        intercept(ApplicationCallPipeline.Call) {
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
                call.respond(status, Resp.fail(status.description))
            }
            // 异常处理
            exception<Throwable> { call, cause ->
                call.respond(Resp.fail(cause.message ?: "error"))
            }
        }
        routing {
            get("/") {
                call.respond(Resp.success("ROOT"))
            }
            get("/file/list") {
                call.respond(
                    Resp.success(
                        ExcelParser.list()
                    )
                )
            }
            get("/file") {
                call.respond(
                    Resp.success(
                        ExcelParser.parse(
                            fileName = call.parameters["fileName"]!!
                        )
                    )
                )
            }
        }
    }.start(wait = true)
}
