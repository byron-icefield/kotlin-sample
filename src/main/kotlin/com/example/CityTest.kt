package com.example

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import java.io.File

/**
 * created by byron at 2023/10/25
 */

fun main() {

    val file = File("/Users/killgravelee/Downloads/乡镇级行政区划/geojson/xiangzhen.geojson")

    val readText = file.readText()

    val parseToJsonElement = Json.parseToJsonElement(readText)
    val jsonElement = parseToJsonElement.jsonObject["features"]

    jsonElement?.jsonArray?.forEach() {
        val properties = it.jsonObject["properties"]
        properties?.apply {
            val name = this.jsonObject["NAME"]
            if (name == null || name is JsonNull || "null" == name.toString()) {
                // nothing
            } else {
                println(name.toString().replace("\"", "", true))
            }
        }
    }

}