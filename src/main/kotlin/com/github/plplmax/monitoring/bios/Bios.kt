package com.github.plplmax.monitoring.bios

import io.prometheus.client.CollectorRegistry
import oshi.hardware.Firmware

class Bios(boardName: String, firmware: Firmware, registry: CollectorRegistry) {
    private val name: String = "${firmware.manufacturer} ${firmware.name}"

    init {
        NameMetricOf(registry).asCounter().labels(boardName, name)
    }
}
