package com.remion.metrics.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import com.remion.metrics.cdi.annotation.ApplicationMetrics;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.reporting.JmxReporter;


public class MetricsRegistryProducer {
	
	@Produces @ApplicationScoped @ApplicationMetrics
	public MetricsRegistryWrapper produceMetricsRegistry() {
		MetricsRegistry registry = new MetricsRegistry();
		JmxReporter jmxReporter = new JmxReporter(registry);
		jmxReporter.start();
		return new MetricsRegistryWrapper(registry, jmxReporter);
	}
	
	
	public void closeRegistry(@Disposes @ApplicationMetrics MetricsRegistryWrapper metricsRegistry) {
		metricsRegistry.shutdown();
	}
	
}
