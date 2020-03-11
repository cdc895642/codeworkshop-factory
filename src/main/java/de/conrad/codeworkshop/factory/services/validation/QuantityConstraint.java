package de.conrad.codeworkshop.factory.services.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = QuantityValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QuantityConstraint {
    String message() default "Quantity is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
