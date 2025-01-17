package it.inail.geodnotifapp;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@WithSecurityContext(factory = WithMockAuthenticationSecurityContextFactory.class)
public @interface WithMockAuthentication {

    String subject() default "";

    String[] authorities() default {};

    String headOffice() default "";

    String role() default "";

    String office() default "";
}
