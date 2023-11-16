package com.example

import kotlinx.serialization.Serializable

/**
 * created by byron at 2023/10/20
 */
@Serializable
sealed class Resp(val success: Boolean, val msg: String?) {
    
    data class Fail(val error: String?) : Resp(false, error)

    data object Success : Resp(true, null)

}