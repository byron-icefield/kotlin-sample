package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * created by byron at 2023/9/21
 */
@SpringBootApplication
class ExampleApp

fun main(args: Array<String>) {
    runApplication<ExampleApp>(*args)
}