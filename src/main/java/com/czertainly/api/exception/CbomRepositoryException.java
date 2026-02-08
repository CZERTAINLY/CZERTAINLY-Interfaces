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

    public CbomRepositoryException(String message, Throwable cause) {
        super(message, cause);
        this.problemDetail = null;
    }

    public CbomRepositoryException(ProblemDetail problemDetail) {
        super(problemDetail.getDetail());
        this.problemDetail = problemDetail;
    }

    public CbomRepositoryException(ProblemDetail problemDetail, Throwable cause) {
        super(problemDetail != null ? problemDetail.getDetail() : null, cause);
        this.problemDetail = problemDetail;
    }

    public CbomRepositoryException(String message, ProblemDetail problemDetail) {
        super(problemDetail != null
                ? message + ": " + problemDetail.getDetail()
                : message);
        this.problemDetail = problemDetail;
    }

    public CbomRepositoryException(String message, ProblemDetail problemDetail, Throwable cause) {
        super(problemDetail != null
                ? message + ": " + problemDetail.getDetail()
                : message, cause);
        this.problemDetail = problemDetail;
    }
}
