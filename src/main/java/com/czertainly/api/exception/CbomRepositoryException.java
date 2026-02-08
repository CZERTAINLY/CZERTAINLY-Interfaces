package com.czertainly.api.exception;

import java.util.Objects;

import org.springframework.http.ProblemDetail;

import lombok.Getter;

@Getter
public class CbomRepositoryException extends Exception {
    private final ProblemDetail problemDetail;

    public CbomRepositoryException(String message) {
        super(message);
        this.problemDetail = null;
    }

    public CbomRepositoryException(ProblemDetail problemDetail) {
        super(Objects.requireNonNull(problemDetail, "problemDetail must not be null").getDetail());
        this.problemDetail = problemDetail;
    }

    public CbomRepositoryException(String message, ProblemDetail problemDetail) {
        super(problemDetail != null
                ? message + ": " + problemDetail.getDetail()
                : message);
        this.problemDetail = problemDetail;
    }
}
