package com.github.plplmax.monitoring.bios

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class NameMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_bios_name",
    help: String = "Show BIOS name",
    labels: Array<String> = arrayOf("motherboard", "bios")
) : Metric by MetricOf(name, help, labels, registry)
