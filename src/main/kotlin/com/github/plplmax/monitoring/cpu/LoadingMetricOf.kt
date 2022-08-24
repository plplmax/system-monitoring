package com.github.plplmax.monitoring.cpu

import com.github.plplmax.monitoring.core.Metric
import com.github.plplmax.monitoring.core.MetricOf
import io.prometheus.client.CollectorRegistry

class LoadingMetricOf(
    registry: CollectorRegistry,
    name: String = "machine_cpu_loading",
    help: String = "Show current cpu loading",
    labels: Array<String> = arrayOf("cpu")
) : Metric by MetricOf(name, help, labels, registry)
