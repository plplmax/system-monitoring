package com.github.plplmax.monitoring.memory

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class SpeedMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_memory_speed",
    help: String = "Show a memory clock speed",
    labels: Array<String> = arrayOf("slot", "speed")
) : Metric by MetricOf(name, help, labels, registry)
