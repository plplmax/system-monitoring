package com.github.plplmax.monitoring

import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

private val job = Job()

fun main() = runBlocking(job) {
    val app = AppOf()

    app.start(this, job)
    waitStopRequest()
    app.stop()
}

private fun waitStopRequest() {
    println("Enter something here to correctly stop the server...")
    readln()
}
