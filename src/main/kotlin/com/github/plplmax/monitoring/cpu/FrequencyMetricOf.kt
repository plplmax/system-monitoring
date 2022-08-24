package com.github.plplmax.monitoring.cpu

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class FrequencyMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_cpu_vendor_freq",
    help: String = "Show cpu vendor frequency",
    labels: Array<String> = arrayOf("cpu", "freq")
) : Metric by MetricOf(name, help, labels, registry)
