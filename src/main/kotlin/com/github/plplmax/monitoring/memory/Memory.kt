package com.github.plplmax.monitoring.memory

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.Counter
import io.prometheus.client.Gauge
import oshi.hardware.GlobalMemory
import oshi.util.FormatUtil

class Memory(private val memory: GlobalMemory, registry: CollectorRegistry) {
    private val consumption: Gauge = ConsumptionMetricOf(registry).asGauge()
    private val type: Counter = TypeMetricOf(registry).asCounter()
    private val slot: Counter = SlotMetricOf(registry).asCounter()
    private val capacity: Counter = CapacityMetricOf(registry).asCounter()
    private val speed: Counter = SpeedMetricOf(registry).asCounter()
    private val manufacturer: Counter = ManufacturerMetricOf(registry).asCounter()

    init {
        memory.physicalMemory.forEach {
            type.labels(it.bankLabel, it.memoryType)
            slot.labels(it.bankLabel)
            capacity.labels(it.bankLabel, FormatUtil.formatBytes(it.capacity))
            speed.labels(it.bankLabel, FormatUtil.formatHertz(it.clockSpeed))
            manufacturer.labels(it.bankLabel, it.manufacturer)
        }
    }

    fun refresh() {
        val consInGb = (memory.total - memory.available) / (ONE_GB_IN_BYTES * 1.0)
        consumption.set(consInGb)
    }

    companion object {
        private const val ONE_GB_IN_BYTES = 1073741824
    }
}
