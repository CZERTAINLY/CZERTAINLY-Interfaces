package com.czertainly.api.exception;

import com.czertainly.api.model.core.acme.ProblemDocument;
import org.springframework.http.HttpStatus;

public class AcmeProblemDocumentException extends Exception {
    private int httpStatusCode;
    private ProblemDocument problemDocument;

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public ProblemDocument getProblemDocument() {
        return problemDocument;
    }

    public AcmeProblemDocumentException(HttpStatus httpStatus, ProblemDocument problemDocument) {
        this.httpStatusCode = httpStatus.value();
        this.problemDocument = problemDocument;
    }
}
