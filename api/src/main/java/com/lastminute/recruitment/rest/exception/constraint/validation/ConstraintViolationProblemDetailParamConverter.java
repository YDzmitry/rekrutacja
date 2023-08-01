package com.lastminute.recruitment.rest.exception.constraint.validation;

import static java.util.Objects.nonNull;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ConstraintViolationProblemDetailParamConverter {

    public List<ConstraintViolationProblemDetailParamInfo> convert(ConstraintViolationException ex) {
        List<ConstraintViolationProblemDetailParamInfo> params = new ArrayList<>();
        if (nonNull(ex) && !CollectionUtils.isEmpty(ex.getConstraintViolations())) {
            Iterator<ConstraintViolation<?>> iterator = ex.getConstraintViolations().iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<?> violation = iterator.next();
                String field = null;
                for (Path.Node node : violation.getPropertyPath()) {
                    field = node.getName();
                }
                params.add(new ConstraintViolationProblemDetailParamInfo(field, violation.getMessage()));
            }
        }
        return params;
    }
}
