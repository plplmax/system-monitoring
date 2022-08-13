package com.github.plplmax.monitoring

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
    private val cores: Counter =
        Counter.build(CORES_METRIC_NAME, CORES_METRIC_HELP).labelNames(CPU_LABEL).register(registry)
    private val threads: Counter =
        Counter.build(THREADS_METRIC_NAME, THREADS_METRIC_HELP).labelNames(CPU_LABEL).register(registry)
    private val freq: Counter =
        Counter.build(FREQUENCY_METRIC_NAME, FREQUENCY_METRIC_HELP).labelNames(CPU_LABEL, FREQ_LABEL).register(registry)
    private val loading: Gauge =
        Gauge.build(LOADING_METRIC_NAME, LOADING_METRIC_HELP).labelNames(CPU_LABEL).register(registry)
    private val temperature: Gauge =
        Gauge.build(TEMPERATURE_METRIC_NAME, TEMPERATURE_METRIC_HELP).labelNames(CPU_LABEL)
            .register(registry)

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
        private const val CPU_LABEL = "cpu"
        private const val FREQ_LABEL = "freq"

        private const val CORES_METRIC_NAME = "machine_cpu_cores"
        private const val CORES_METRIC_HELP = "Show total cpu cores"

        private const val THREADS_METRIC_NAME = "machine_cpu_threads"
        private const val THREADS_METRIC_HELP = "Show total cpu threads"

        private const val FREQUENCY_METRIC_NAME = "machine_cpu_vendor_freq"
        private const val FREQUENCY_METRIC_HELP = "Show cpu vendor frequency"

        private const val LOADING_METRIC_NAME = "machine_cpu_loading"
        private const val LOADING_METRIC_HELP = "Show current cpu loading"

        private const val TEMPERATURE_METRIC_NAME = "machine_cpu_temperature"
        private const val TEMPERATURE_METRIC_HELP = "Show current cpu temperature"

        private const val ONE_SECOND_IN_MILLIS = 1000L
    }
}