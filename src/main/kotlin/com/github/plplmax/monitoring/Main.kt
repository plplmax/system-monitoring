package com.github.plplmax.monitoring

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.PushGateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import oshi.SystemInfo

fun main() {
    val job = Job()

    runBlocking(job) {
        val systemInfo = SystemInfo()

        val gateway = PushGateway("127.0.0.1:9091")
        val registry = CollectorRegistry.defaultRegistry

        val cpu = Cpu(
            processor = systemInfo.hardware.processor,
            sensors = systemInfo.hardware.sensors,
            registry = registry
        )

        val jobName = "metrics"
        val refreshJob = launch(Dispatchers.IO + job) {
            repeat(Int.MAX_VALUE) {
                cpu.refresh()
                gateway.pushAdd(registry, jobName)
            }
        }

        println("Enter something here to correctly stop the server...")
        readln()

        refreshJob.cancelAndJoin()
        gateway.delete(jobName)
    }
}
