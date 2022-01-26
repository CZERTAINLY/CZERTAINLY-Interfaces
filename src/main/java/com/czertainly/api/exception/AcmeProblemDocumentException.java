package com.czertainly.api.exception;

import com.czertainly.api.model.core.acme.Problem;
import com.czertainly.api.model.core.acme.ProblemDocument;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

public class AcmeProblemDocumentException extends Exception {
    @Schema(description = "HTTP Status code corresponding to the error")
    private int httpStatusCode;
    @Schema(description = "ACME Problem document described in RFC8555")
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

    public AcmeProblemDocumentException(HttpStatus httpStatus, Problem problem, String detail) {
        this(httpStatus, new ProblemDocument(problem));
        problem.setDetail(detail);
    }
}
