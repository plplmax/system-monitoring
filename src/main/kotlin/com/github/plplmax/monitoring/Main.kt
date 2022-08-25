package com.github.plplmax.monitoring

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    launch { AppOf(scope = this).start() }
}
