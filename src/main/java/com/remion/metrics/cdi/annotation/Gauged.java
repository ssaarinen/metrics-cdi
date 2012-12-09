package com.remion.metrics.cdi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@InterceptorBinding
public @interface Gauged {
    /**
     * The gauge's group.
     */
    String group() default "";

    /**
     * The gauge's type.
     */
    String type() default "";

    /**
     * The gauge's name.
     */
    String name() default "";
}
