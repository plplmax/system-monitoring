package com.github.plplmax.monitoring.memory

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class ConsumptionMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_memory_consumption",
    help: String = "Show a memory consumption",
    labels: Array<String> = emptyArray()
) : Metric by MetricOf(name, help, labels, registry)
