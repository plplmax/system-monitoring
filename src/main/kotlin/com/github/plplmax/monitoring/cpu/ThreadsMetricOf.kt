package com.github.plplmax.monitoring.cpu

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class ThreadsMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_cpu_threads",
    help: String = "Show total cpu threads",
    labels: Array<String> = arrayOf("cpu")
) : Metric by MetricOf(name, help, labels, registry)
