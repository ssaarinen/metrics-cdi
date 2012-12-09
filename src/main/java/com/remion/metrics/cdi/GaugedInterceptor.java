package com.remion.metrics.cdi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.remion.metrics.cdi.annotation.ApplicationMetrics;
import com.remion.metrics.cdi.annotation.Gauged;
import com.yammer.metrics.core.Gauge;
import com.yammer.metrics.core.MetricName;

@Interceptor
@Gauged
public class GaugedInterceptor {


	@Inject @ApplicationMetrics
	MetricsRegistryWrapper metricsRegistry;
	
	
	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		Gauged annotation = ic.getMethod().getAnnotation(Gauged.class);
		if(annotation != null) {
			MetricName metricName = MetricNameUtil.forGaugedMethod(ic.getTarget().getClass().getSuperclass(), ic.getMethod(), annotation);
			metricsRegistry.newGauge(metricName, new ReflectionMethodGauge(ic.getMethod(),  ic.getTarget()));
		}
		return ic.proceed();
	}

	
	private static class ReflectionMethodGauge extends Gauge<Object> {

		final Method method;
		final Object target;
		
		public ReflectionMethodGauge(Method method, Object target) {
			super();
			this.method = method;
			this.target = target;
		}

		@Override
		public Object value() {
			try {
				return method.invoke(target);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
}
