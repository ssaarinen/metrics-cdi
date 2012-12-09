package com.remion.metrics.cdi;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import com.remion.metrics.cdi.annotation.ApplicationMetric;
import com.remion.metrics.cdi.annotation.Gauged;
import com.remion.metrics.cdi.annotation.Metered;
import com.remion.metrics.cdi.annotation.Timed;
import com.yammer.metrics.core.MetricName;

public class MetricNameUtil {
	public static MetricName forTimedMethod(Class<?> klass, Method method, Timed annotation) {
		return new MetricName(chooseDomain(annotation.group(), klass),
				MetricName.chooseType(annotation.type(), klass),
				MetricName.chooseName(annotation.name(), method));
	}

	public static MetricName forMeteredMethod(Class<?> klass, Method method, Metered annotation) {
		return new MetricName(chooseDomain(annotation.group(), klass),
				MetricName.chooseType(annotation.type(), klass),
				MetricName.chooseName(annotation.name(), method));
	}

	public static MetricName forGaugedMethod(Class<?> klass, Method method, Gauged annotation) {
		return new MetricName(chooseDomain(annotation.group(), klass),
				MetricName.chooseType(annotation.type(), klass),
				MetricName.chooseName(annotation.name(), method));
	}
	
	public static MetricName forInjectedMetricField(Class<?> klass, Field field, ApplicationMetric annotation, String scope) {
		return new MetricName(chooseDomain(annotation.group(), klass),
							  MetricName.chooseType(annotation.type(), klass),
							  chooseName(annotation.name(), field),
							  scope);
	}
//
//	public static MetricName forExceptionMeteredMethod(Class<?> klass, Method method, com.yammer.metrics.annotation.ExceptionMetered annotation) {
//		return new MetricName(chooseDomain(annotation.group(), klass),
//				MetricName.chooseType(annotation.type(), klass),
//				annotation.name() == null || annotation.name().isEmpty() ?
//						method.getName() + ExceptionMetered.DEFAULT_NAME_SUFFIX :
//							annotation.name());
//	}

	private static String chooseDomain(String domain, Class<?> klass) {
		if(domain == null || domain.isEmpty()) {
			domain = getPackageName(klass);
		}
		return domain;
	}
	
	private static String chooseName(String name, Member field) {
		if(name == null || name.isEmpty()) {
			name = field.getName();
		}
		return name;
	}

	private static String getPackageName(Class<?> klass) {
		return klass.getPackage() == null ? "" : klass.getPackage().getName();
	}
}
