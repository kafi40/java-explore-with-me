package ru.practicum.mainserver.validator;

import ru.practicum.mainserver.annotation.PositiveList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

public class PositiveListValidator implements ConstraintValidator<PositiveList, Collection<Long>> {

    @Override
    public void initialize(PositiveList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Collection<Long> longs, ConstraintValidatorContext constraintValidatorContext) {
        if (longs == null) {
            return true;
        }
        return longs.stream().noneMatch(l -> l < 1);
    }
}
