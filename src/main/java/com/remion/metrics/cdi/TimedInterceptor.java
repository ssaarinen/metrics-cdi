package com.remion.metrics.cdi;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.remion.metrics.cdi.annotation.ApplicationMetrics;
import com.remion.metrics.cdi.annotation.Timed;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;


@Interceptor
@Timed
public class TimedInterceptor {

	@Inject @ApplicationMetrics
	MetricsRegistryWrapper metricsRegistry;


	@AroundInvoke
	public Object aroundInvoke(InvocationContext ctx) throws Exception {
		Timed timed = ctx.getMethod().getAnnotation(Timed.class);
		if(timed == null) {
			timed = ctx.getTarget().getClass().getAnnotation(Timed.class);
		}
		
		if(timed != null) {
			MetricName metricName = MetricNameUtil.forTimedMethod(ctx.getTarget().getClass().getSuperclass(), ctx.getMethod(), timed);
			Timer timer = metricsRegistry.newTimer(metricName, timed.durationUnit(), timed.rateUnit());
			TimerContext timerCtx = timer.time();
			try {
				return ctx.proceed();
			} finally {
				timerCtx.stop();
			}

		} 
		return ctx.proceed();
	}

}
