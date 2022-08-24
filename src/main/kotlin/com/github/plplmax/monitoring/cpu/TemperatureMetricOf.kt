package com.github.plplmax.monitoring.cpu

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class TemperatureMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_cpu_temperature",
    help: String = "Show current cpu temperature",
    labels: Array<String> = arrayOf("cpu")
) : Metric by MetricOf(name, help, labels, registry)
