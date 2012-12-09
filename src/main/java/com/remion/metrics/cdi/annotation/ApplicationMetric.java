package com.remion.metrics.cdi.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Qualifier
@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
@Documented
public @interface ApplicationMetric {

	/**
	 * The group of the metric.
	 */
	@Nonbinding
	String group() default "";

	/**
	 * The type of the metric.
	 */
	@Nonbinding
	String type() default "";

	/**
	 * The name of the metric.
	 */
	@Nonbinding
	String name() default "";

	/**
	 * The name of the type of events the meter is measuring.
	 * Applies to Meter
	 */
	@Nonbinding
	String eventType() default "calls";

	/**
	 * The time unit of the meter's rate.
	 * Applies to Meter, Timer
	 */
	@Nonbinding
	TimeUnit rateUnit() default TimeUnit.SECONDS;

	/**
	 * The time unit of the timer's duration.
	 * Applies to Timer
	 */
	@Nonbinding
	TimeUnit durationUnit() default TimeUnit.MILLISECONDS;

	/**
	 * The type of sampling that should be performed.
	 * Applies to Histogram
	 */
	@Nonbinding
	boolean biased() default false;
}
