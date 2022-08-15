package com.github.plplmax.monitoring

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface App {
    fun start(scope: CoroutineScope, job: Job)
    suspend fun stop()
}
