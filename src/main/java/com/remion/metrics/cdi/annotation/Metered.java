package com.remion.metrics.cdi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import javax.interceptor.InterceptorBinding;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@InterceptorBinding
public @interface Metered {
    /**
     * The group of the timer.
     */
    String group() default "";

    /**
     * The type of the timer.
     */
    String type() default "";

    /**
     * The name of the meter.
     */
    String name() default "";

    /**
     * The name of the type of events the meter is measuring.
     */
    String eventType() default "calls";

    /**
     * The time unit of the meter's rate.
     */
    TimeUnit rateUnit() default TimeUnit.SECONDS;
}
