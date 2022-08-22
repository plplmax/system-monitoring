package com.github.plplmax.monitoring

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

private val job = Job()

fun main() = runBlocking(job) {
    val app = AppOf()

    app.start(job)
    waitStopRequest()
    app.stop()
}

private suspend fun waitStopRequest() {
    delay(5000)
    println("Enter something here to correctly stop the server...")
    readln()
}
