package com.github.plplmax.monitoring.core

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.Counter
import io.prometheus.client.Gauge

class MetricOf(
    private val name: String,
    private val help: String,
    private val labels: Array<String>,
    private val registry: CollectorRegistry
) : Metric {
    override fun asCounter(): Counter {
        return Counter.build(name, help).labelNames(*labels).register(registry)
    }

    override fun asGauge(): Gauge {
        return Gauge.build(name, help).labelNames(*labels).register(registry)
    }
}
