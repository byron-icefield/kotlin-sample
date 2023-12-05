package com.example

import kotlinx.serialization.Serializable

/**
 * created by byron at 2023/10/20
 */
@Serializable
class Resp<T>(
    val success: Boolean = true, val msg: String = "SUCCESS", val data: T?
) {

    companion object {
        fun fail(msg: String): Resp<Nothing> {
            return Resp(success = false, msg = msg, data = null)
        }

        fun success(): Resp<Nothing> {
            return Resp(data = null)
        }

        fun <T> success(data: T): Resp<T> {
            return Resp(data = data)
        }

    }

}