package com.lastminute.recruitment.rest.wiki.validation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Constraint(validatedBy = WikiLinkValidator.class)
@Target({ PARAMETER })
@Retention(RUNTIME)
@Documented
public @interface WikiLink {
    String message() default "{com.lastminute.recruitment.validation.constraints.wikilink.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
