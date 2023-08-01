package com.lastminute.recruitment.rest.exception.constraint.validation;

public class ConstraintViolationProblemDetailParamInfo {

    private final String name;
    private final String reason;

    public ConstraintViolationProblemDetailParamInfo(String name, String reason) {
        this.name = name;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }
}
