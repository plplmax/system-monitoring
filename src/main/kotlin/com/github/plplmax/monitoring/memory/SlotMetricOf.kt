package com.github.plplmax.monitoring.memory

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class SlotMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_memory_slot",
    help: String = "Show a memory slot",
    labels: Array<String> = arrayOf("slot")
) : Metric by MetricOf(name, help, labels, registry)
