package com.remion.metrics.cdi;

import java.lang.reflect.Field;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.remion.metrics.cdi.annotation.ApplicationMetric;
import com.remion.metrics.cdi.annotation.ApplicationMetrics;
import com.yammer.metrics.core.Counter;
import com.yammer.metrics.core.Histogram;
import com.yammer.metrics.core.Meter;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.Timer;

public class MetricsProducer {
	private final Logger log = LoggerFactory.getLogger(MetricsProducer.class);
	
	@Inject @ApplicationMetrics
	MetricsRegistryWrapper metricsRegistry;
	
	@Produces @ApplicationMetric 
	public Timer produceTimer(InjectionPoint ip) {
		log.debug("creating timer for injectionpoint {}", ip);
		MetricName metricName = MetricNameUtil.forInjectedMetricField(ip.getMember().getDeclaringClass(), (Field)ip.getMember(), ip.getAnnotated().getAnnotation(ApplicationMetric.class), null);
		ApplicationMetric annotation = ip.getAnnotated().getAnnotation(ApplicationMetric.class);
		return metricsRegistry.newTimer(metricName, annotation.durationUnit(), annotation.rateUnit());
	}
	
	@Produces @ApplicationMetric 
	public Meter produceMeter(InjectionPoint ip) {
		log.debug("creating meter for injectionpoint {}", ip);
		MetricName metricName = MetricNameUtil.forInjectedMetricField(ip.getMember().getDeclaringClass(), (Field)ip.getMember(), ip.getAnnotated().getAnnotation(ApplicationMetric.class), null);
		ApplicationMetric annotation = ip.getAnnotated().getAnnotation(ApplicationMetric.class);
		return metricsRegistry.newMeter(metricName, annotation.eventType(), annotation.rateUnit());
	}
	
	@Produces @ApplicationMetric 
	public Counter produceCounter(InjectionPoint ip) {
		log.debug("creating counter for injectionpoint {}", ip);
		MetricName metricName = MetricNameUtil.forInjectedMetricField(ip.getMember().getDeclaringClass(), (Field)ip.getMember(), ip.getAnnotated().getAnnotation(ApplicationMetric.class), null);
		return metricsRegistry.newCounter(metricName);
	}
	
	@Produces @ApplicationMetric 
	public Histogram produceHistogram(InjectionPoint ip) {
		log.debug("creating histogram for injectionpoint {}", ip);
		MetricName metricName = MetricNameUtil.forInjectedMetricField(ip.getMember().getDeclaringClass(), (Field)ip.getMember(), ip.getAnnotated().getAnnotation(ApplicationMetric.class), null);
		ApplicationMetric annotation = ip.getAnnotated().getAnnotation(ApplicationMetric.class);
		return metricsRegistry.newHistogram(metricName, annotation.biased());
	}
	
}
