package com.github.plplmax.monitoring.motherboard

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class NameMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_motherboard_name",
    help: String = "Show motherboard name",
    labels: Array<String> = arrayOf("motherboard")
) : Metric by MetricOf(name, help, labels, registry)
