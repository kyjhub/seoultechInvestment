package com.example.seoultechInvestment.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class statusValidator implements ConstraintValidator<StatusPattern, String> {
    @Override
    public void initialize(StatusPattern constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<String> statuses = new ArrayList<>(Arrays.asList("FAIL", "SUCCESS", "ONGOING"));
        return !statuses.contains(s);
    }
}
