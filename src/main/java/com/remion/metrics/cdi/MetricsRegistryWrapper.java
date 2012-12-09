package com.remion.metrics.cdi;

import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.yammer.metrics.core.Counter;
import com.yammer.metrics.core.Gauge;
import com.yammer.metrics.core.Histogram;
import com.yammer.metrics.core.Meter;
import com.yammer.metrics.core.Metric;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.MetricPredicate;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.core.MetricsRegistryListener;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.reporting.JmxReporter;


public class MetricsRegistryWrapper {
	
	private final MetricsRegistry metricsRegistry;
	private final JmxReporter jmxReporter;
	
	public MetricsRegistryWrapper() {
		this(null, null);
	}
	
	public MetricsRegistryWrapper(MetricsRegistry metricsRegistry,
			JmxReporter jmxReporter) {
		super();
		this.metricsRegistry = metricsRegistry;
		this.jmxReporter = jmxReporter;
	}
	
	public MetricsRegistry getMetricsRegistry() {
		return metricsRegistry;
	}
	
	public JmxReporter getJmxReporter() {
		return jmxReporter;
	}

	public <T> Gauge<T> newGauge(Class<?> klass, String name, Gauge<T> metric) {
		return metricsRegistry.newGauge(klass, name, metric);
	}

	public <T> Gauge<T> newGauge(Class<?> klass, String name, String scope,
			Gauge<T> metric) {
		return metricsRegistry.newGauge(klass, name, scope, metric);
	}

	public <T> Gauge<T> newGauge(MetricName metricName, Gauge<T> metric) {
		return metricsRegistry.newGauge(metricName, metric);
	}

	public Counter newCounter(Class<?> klass, String name) {
		return metricsRegistry.newCounter(klass, name);
	}

	public Counter newCounter(Class<?> klass, String name, String scope) {
		return metricsRegistry.newCounter(klass, name, scope);
	}

	public Counter newCounter(MetricName metricName) {
		return metricsRegistry.newCounter(metricName);
	}

	public Histogram newHistogram(Class<?> klass, String name, boolean biased) {
		return metricsRegistry.newHistogram(klass, name, biased);
	}

	public Histogram newHistogram(Class<?> klass, String name, String scope,
			boolean biased) {
		return metricsRegistry.newHistogram(klass, name, scope, biased);
	}

	public Histogram newHistogram(Class<?> klass, String name) {
		return metricsRegistry.newHistogram(klass, name);
	}

	public Histogram newHistogram(Class<?> klass, String name, String scope) {
		return metricsRegistry.newHistogram(klass, name, scope);
	}

	public Histogram newHistogram(MetricName metricName, boolean biased) {
		return metricsRegistry.newHistogram(metricName, biased);
	}

	public Meter newMeter(Class<?> klass, String name, String eventType,
			TimeUnit unit) {
		return metricsRegistry.newMeter(klass, name, eventType, unit);
	}

	public Meter newMeter(Class<?> klass, String name, String scope,
			String eventType, TimeUnit unit) {
		return metricsRegistry.newMeter(klass, name, scope, eventType, unit);
	}

	public Meter newMeter(MetricName metricName, String eventType, TimeUnit unit) {
		return metricsRegistry.newMeter(metricName, eventType, unit);
	}

	public Timer newTimer(Class<?> klass, String name) {
		return metricsRegistry.newTimer(klass, name);
	}

	public Timer newTimer(Class<?> klass, String name, TimeUnit durationUnit,
			TimeUnit rateUnit) {
		return metricsRegistry.newTimer(klass, name, durationUnit, rateUnit);
	}

	public Timer newTimer(Class<?> klass, String name, String scope) {
		return metricsRegistry.newTimer(klass, name, scope);
	}

	public Timer newTimer(Class<?> klass, String name, String scope,
			TimeUnit durationUnit, TimeUnit rateUnit) {
		return metricsRegistry.newTimer(klass, name, scope, durationUnit,
				rateUnit);
	}

	public Timer newTimer(MetricName metricName, TimeUnit durationUnit,
			TimeUnit rateUnit) {
		return metricsRegistry.newTimer(metricName, durationUnit, rateUnit);
	}

	public Map<MetricName, Metric> allMetrics() {
		return metricsRegistry.allMetrics();
	}

	public SortedMap<String, SortedMap<MetricName, Metric>> groupedMetrics() {
		return metricsRegistry.groupedMetrics();
	}

	public SortedMap<String, SortedMap<MetricName, Metric>> groupedMetrics(
			MetricPredicate predicate) {
		return metricsRegistry.groupedMetrics(predicate);
	}

	public void shutdown() {
		metricsRegistry.shutdown();
		if(jmxReporter != null) {
			jmxReporter.shutdown();
		}
	}

	public ScheduledExecutorService newScheduledThreadPool(int poolSize,
			String name) {
		return metricsRegistry.newScheduledThreadPool(poolSize, name);
	}

	public void removeMetric(Class<?> klass, String name) {
		metricsRegistry.removeMetric(klass, name);
	}

	public void removeMetric(Class<?> klass, String name, String scope) {
		metricsRegistry.removeMetric(klass, name, scope);
	}

	public void removeMetric(MetricName name) {
		metricsRegistry.removeMetric(name);
	}

	public void addListener(MetricsRegistryListener listener) {
		metricsRegistry.addListener(listener);
	}

	public void removeListener(MetricsRegistryListener listener) {
		metricsRegistry.removeListener(listener);
	}
	
	

	
}
