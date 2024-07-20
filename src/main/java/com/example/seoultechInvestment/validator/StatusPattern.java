package com.example.seoultechInvestment.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {statusValidator.class})
public @interface StatusPattern {
    String message() default "{com.example.seoultechInvestment.validator.message} 형식에 맞게 입력하세요";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
