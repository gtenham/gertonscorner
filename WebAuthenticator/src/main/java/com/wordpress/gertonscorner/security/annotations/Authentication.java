package com.wordpress.gertonscorner.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authentication {
	String proxyName();
	String serviceName() default "users";
	boolean remoteAddressOnly() default false;
}
