package de.conrad.codeworkshop.factory.services.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.MathContext;

public class QuantityValidator
        implements ConstraintValidator<QuantityConstraint, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        //Divisible by 10
        MathContext mc = new MathContext(0);
        BigDecimal remainder = value.remainder(BigDecimal.TEN, mc);
        if (remainder.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        //more than 0 and less than 1
        if (value.compareTo(BigDecimal.ZERO) > 0 &&
                value.compareTo(BigDecimal.ONE) < 0) {
            return true;
        }
        //==42.42
        if (value.compareTo(BigDecimal.valueOf(42.42)) == 0) {
            return true;
        }
        //inappropriate value
        return false;
    }
}
