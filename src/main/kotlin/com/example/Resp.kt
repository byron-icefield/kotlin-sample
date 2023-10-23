package com.example

import kotlinx.serialization.Serializable

/**
 * created by byron at 2023/10/20
 */
@Serializable
data class Resp(val success: Boolean, val msg: String?) {

    companion object {
        fun fail(msg: String?): Resp {
            return Resp(false, msg)
        }

        fun success(): Resp {
            return success("SUCCESS");
        }

        fun success(msg: String?): Resp {
            return Resp(true, msg)
        }
    }
}