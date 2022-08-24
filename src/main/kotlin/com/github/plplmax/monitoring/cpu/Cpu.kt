package com.github.plplmax.monitoring.cpu

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.Counter
import io.prometheus.client.Gauge
import kotlinx.coroutines.delay
import oshi.hardware.CentralProcessor
import oshi.hardware.Sensors
import oshi.util.FormatUtil
import kotlin.math.roundToInt

class Cpu(
    private val processor: CentralProcessor,
    private val sensors: Sensors,
    registry: CollectorRegistry
) {
    private val name: String = processor.processorIdentifier.name.trim()
    private val cores: Counter = CoresMetricOf(registry).asCounter()
    private val threads: Counter = ThreadsMetricOf(registry).asCounter()
    private val freq: Counter = FrequencyMetricOf(registry).asCounter()
    private val loading: Gauge = LoadingMetricOf(registry).asGauge()
    private val temperature: Gauge = TemperatureMetricOf(registry).asGauge()

    init {
        with(processor) {
            val physicalCores = physicalProcessorCount.toDouble()
            val logicalCores = logicalProcessorCount.toDouble()
            val vendorFreq = processorIdentifier.vendorFreq.let(FormatUtil::formatHertz)

            cores.labels(name).inc(physicalCores)
            threads.labels(name).inc(logicalCores)
            freq.labels(name, vendorFreq)
        }
    }

    suspend fun refresh() {
        val cpuLoadingInPercents = (processor.getSystemCpuLoad(ONE_SECOND_IN_MILLIS) * 100).roundToInt()

        loading.labels(name).set(cpuLoadingInPercents.toDouble())
        temperature.labels(name).set(sensors.cpuTemperature)

        delay(100)
    }

    companion object {
        private const val ONE_SECOND_IN_MILLIS = 1000L
    }
}