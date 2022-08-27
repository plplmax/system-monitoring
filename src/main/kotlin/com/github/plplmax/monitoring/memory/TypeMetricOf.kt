package com.github.plplmax.monitoring.memory

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class TypeMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_memory_type",
    help: String = "Show a memory type",
    labels: Array<String> = arrayOf("slot", "type")
) : Metric by MetricOf(name, help, labels, registry)
