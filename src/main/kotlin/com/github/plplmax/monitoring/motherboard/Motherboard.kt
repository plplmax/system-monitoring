package com.github.plplmax.monitoring.motherboard

import com.github.plplmax.monitoring.bios.Bios
import io.prometheus.client.CollectorRegistry
import oshi.hardware.Baseboard
import oshi.hardware.Firmware

class Motherboard(board: Baseboard, firmware: Firmware, registry: CollectorRegistry) {
    private val name: String = "${board.manufacturer} ${board.serialNumber}"

    init {
        NameMetricOf(registry).asCounter().labels(name)
        Bios(boardName = name, firmware, registry)
    }
}
