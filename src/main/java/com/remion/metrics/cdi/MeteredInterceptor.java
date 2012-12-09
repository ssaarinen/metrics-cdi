package com.remion.metrics.cdi;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.remion.metrics.cdi.annotation.ApplicationMetrics;
import com.remion.metrics.cdi.annotation.Metered;
import com.yammer.metrics.core.Meter;
import com.yammer.metrics.core.MetricName;


@Interceptor
@Metered
public class MeteredInterceptor {

	@Inject @ApplicationMetrics
	MetricsRegistryWrapper metricsRegistry;
	
	@AroundInvoke
	public Object aroundInvoke(InvocationContext ctx) throws Exception {
		Metered metered = ctx.getMethod().getAnnotation(Metered.class);
		if(metered == null) {
			metered = ctx.getTarget().getClass().getAnnotation(Metered.class);
		}
		
		if(metered != null) {
			MetricName metricName = MetricNameUtil.forMeteredMethod(ctx.getTarget().getClass().getSuperclass(), ctx.getMethod(), metered);
			Meter meter = metricsRegistry.newMeter(metricName, metered.eventType(), metered.rateUnit());
			meter.mark();
		}
		return ctx.proceed();
	}
	
}
