package com.github.plplmax.monitoring

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.PushGateway
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import oshi.SystemInfo

class AppOf(
    private val info: SystemInfo = SystemInfo(),
    private val gateway: PushGateway = PushGateway("127.0.0.1:9091"),
    private val registry: CollectorRegistry = CollectorRegistry.defaultRegistry
) : App {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var refreshJob: Job? = null

    override fun start(job: Job) {
        refreshJob = scope.launch(job) {
            val cpu = Cpu(
                processor = info.hardware.processor,
                sensors = info.hardware.sensors,
                registry = registry
            )

            Bios(
                motherboard = info.hardware.computerSystem.baseboard,
                firmware = info.hardware.computerSystem.firmware,
                registry = registry
            )

            repeat(Int.MAX_VALUE) {
                cpu.refresh()
                gateway.pushAdd(registry, JOB_NAME)
            }
        }
    }

    override suspend fun stop() {
        refreshJob?.cancelAndJoin()
        gateway.delete(JOB_NAME)
    }

    companion object {
        private const val JOB_NAME = "metrics"
    }
}
