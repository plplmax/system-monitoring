package com.github.plplmax.monitoring.memory

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class ManufacturerMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_memory_manufacturer",
    help: String = "Show a memory manufacturer",
    labels: Array<String> = arrayOf("slot", "manufacturer")
) : Metric by MetricOf(name, help, labels, registry)
