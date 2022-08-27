package com.github.plplmax.monitoring.memory

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class CapacityMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_memory_capacity",
    help: String = "Show a memory capacity",
    labels: Array<String> = arrayOf("slot", "capacity")
) : Metric by MetricOf(name, help, labels, registry)
