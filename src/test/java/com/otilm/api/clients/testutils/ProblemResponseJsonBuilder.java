package com.otilm.api.clients.testutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.otilm.api.model.common.error.ErrorCode;

public final class ProblemResponseJsonBuilder {

    private final ObjectNode problem = new ObjectMapper().createObjectNode();

    private ProblemResponseJsonBuilder() {
    }

    public static ProblemResponseJsonBuilder aProblemResponse() {
        return new ProblemResponseJsonBuilder();
    }

    public ProblemResponseJsonBuilder withErrorCode(ErrorCode errorCode) {
        problem.put("type", "https://docs.otilm.com/problems/common/" + errorCode.name());
        problem.put("errorCode", errorCode.name());
        return this;
    }

    public ProblemResponseJsonBuilder withTitle(String title) {
        problem.put("title", title);
        return this;
    }

    public ProblemResponseJsonBuilder withStatus(int status) {
        problem.put("status", status);
        return this;
    }

    public ProblemResponseJsonBuilder withDetail(String detail) {
        problem.put("detail", detail);
        return this;
    }

    public ProblemResponseJsonBuilder withTimestamp(String timestamp) {
        problem.put("timestamp", timestamp);
        return this;
    }

    public ProblemResponseJsonBuilder withCorrelationId(String correlationId) {
        problem.put("correlationId", correlationId);
        return this;
    }

    public ProblemResponseJsonBuilder withRetryable(boolean retryable) {
        problem.put("retryable", retryable);
        return this;
    }

    public ProblemResponseJsonBuilder withRetryAfterSeconds(int retryAfterSeconds) {
        problem.put("retryAfterSeconds", retryAfterSeconds);
        return this;
    }

    public String build() {
        return problem.toString();
    }
}
