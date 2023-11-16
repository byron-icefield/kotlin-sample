package com.example

/**
 * created by byron at 2023/11/16
 */

interface Yes {

    sealed class YesSon(val hello: String)

    data object YYY : Yes

    data object Son : YesSon("son")

}
