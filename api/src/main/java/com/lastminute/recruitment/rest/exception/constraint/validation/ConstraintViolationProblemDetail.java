package com.lastminute.recruitment.rest.exception.constraint.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.ProblemDetail;

import java.util.List;

public class ConstraintViolationProblemDetail extends ProblemDetail {

    @JsonProperty("invalid-params")
    private final List<ConstraintViolationProblemDetailParamInfo> invalidParams;

    public ConstraintViolationProblemDetail(
        int rawStatusCode,
        List<ConstraintViolationProblemDetailParamInfo> invalidParams
    ) {
        super(rawStatusCode);
        this.invalidParams = invalidParams;
    }

    public List<ConstraintViolationProblemDetailParamInfo> getInvalidParams() {
        return invalidParams;
    }
}
