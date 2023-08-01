package com.lastminute.recruitment.rest.exception;

import com.lastminute.recruitment.rest.exception.constraint.validation.ConstraintViolationProblemDetail;
import com.lastminute.recruitment.rest.exception.constraint.validation.ConstraintViolationProblemDetailParamInfo;
import com.lastminute.recruitment.rest.exception.constraint.validation.ConstraintViolationProblemDetailParamConverter;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ConstraintViolationProblemDetailParamConverter constraintViolationProblemDetailParamConverter;

    public GlobalExceptionHandler(ConstraintViolationProblemDetailParamConverter constraintViolationProblemDetailParamConverter) {
        this.constraintViolationProblemDetailParamConverter = constraintViolationProblemDetailParamConverter;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleCustomException(ConstraintViolationException ex) {
        List<ConstraintViolationProblemDetailParamInfo> params = constraintViolationProblemDetailParamConverter.convert(ex);

        return new ConstraintViolationProblemDetail(HttpStatus.BAD_REQUEST.value(), params);
    }

}
