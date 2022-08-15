package com.github.plplmax.monitoring

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.Counter
import oshi.hardware.Baseboard
import oshi.hardware.Firmware

class Bios(motherboard: Baseboard, firmware: Firmware, registry: CollectorRegistry) {
    private val manufacturer: String = firmware.manufacturer
    private val name: String = firmware.name

    init {
        val counter = Counter.build(BIOS_METRIC_NAME, BIOS_METRIC_HELP)
            .labelNames(MOTHERBOARD_LABEL, BIOS_LABEL)
            .register(registry)
        val motherboardName = "${motherboard.manufacturer} ${motherboard.serialNumber}"
        val biosName = "$manufacturer $name"

        counter.labels(motherboardName, biosName)
    }

    companion object {
        private const val MOTHERBOARD_LABEL = "motherboard"
        private const val BIOS_LABEL = "bios"

        private const val BIOS_METRIC_NAME = "machine_bios_name"
        private const val BIOS_METRIC_HELP = "Show BIOS name"
    }
}
