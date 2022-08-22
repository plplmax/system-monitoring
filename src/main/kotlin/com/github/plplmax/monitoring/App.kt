package com.github.plplmax.monitoring

import kotlinx.coroutines.Job

interface App {
    fun start(job: Job)
    suspend fun stop()
}
