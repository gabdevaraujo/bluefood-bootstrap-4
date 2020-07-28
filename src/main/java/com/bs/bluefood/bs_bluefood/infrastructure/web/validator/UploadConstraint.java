package com.bs.bluefood.bs_bluefood.infrastructure.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bs.bluefood.bs_bluefood.utils.FileType;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = UploadValidator.class)
public @interface UploadConstraint {
    
    String message() default "Arquivo Inválido";
    FileType[] acceptedTypes();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}