package com.xp.client.annotation;

import com.xp.client.autoconfig.AnnotationClientRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 扫描指定包下的client
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AnnotationClientRegistrar.class)
public @interface ReactClientScan {
    String[] packages() default {};
}
