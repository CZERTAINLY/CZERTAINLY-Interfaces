package com.czertainly.api.exception;

import com.czertainly.api.model.core.acme.Problem;
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
        super();
        this.httpStatusCode = httpStatus.value();
        this.problemDocument = problemDocument;
    }

    public AcmeProblemDocumentException(HttpStatus httpStatus, Problem problem) {
        this(httpStatus, new ProblemDocument(problem));
    }
}
