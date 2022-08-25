package com.github.plplmax.monitoring

import com.github.plplmax.monitoring.cpu.Cpu
import com.github.plplmax.monitoring.motherboard.Motherboard
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.PushGateway
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import oshi.SystemInfo
import java.io.Reader

class AppOf(
    private val scope: CoroutineScope,
    private val info: SystemInfo = SystemInfo(),
    private val gateway: PushGateway = PushGateway("127.0.0.1:9091"),
    private val registry: CollectorRegistry = CollectorRegistry.defaultRegistry
) : App {
    private val stop: Reader = System.`in`.reader()

    override fun start() {
        scope.launch(Dispatchers.IO) {
            val cpu = Cpu(
                processor = info.hardware.processor,
                sensors = info.hardware.sensors,
                registry = registry
            )

            Motherboard(
                board = info.hardware.computerSystem.baseboard,
                firmware = info.hardware.computerSystem.firmware,
                registry = registry
            )

            while (isActive) {
                cpu.refresh()
                gateway.pushAdd(registry, JOB_NAME)
            }
        }

        onStopRequest(::stop)
    }

    override fun stop() {
        scope.coroutineContext.job.invokeOnCompletion { gateway.delete(JOB_NAME) }
        scope.cancel()
    }

    private fun onStopRequest(block: suspend () -> Unit) {
        scope.launch(Dispatchers.IO) {
            println("Enter something here to correctly stop the server...")

            while (isActive) {
                if (stop.ready()) {
                    block()
                    return@launch
                }
                delay(1000)
            }
        }
    }

    companion object {
        private const val JOB_NAME = "metrics"
    }
}
