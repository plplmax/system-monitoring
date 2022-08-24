package com.github.plplmax.monitoring.core

import io.prometheus.client.Counter
import io.prometheus.client.Gauge

interface Metric {
    fun asCounter(): Counter
    fun asGauge(): Gauge
}
