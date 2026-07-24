package com.otilm.api.clients.mq.v2;

import com.otilm.api.clients.ApiClientConnectorInfo;
import com.otilm.api.clients.mq.ProxyClient;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Records the arguments of a proxy request and returns a configured response.
 * Unsupported overloads throw to expose unexpected client delegation.
 */
final class RecordingProxyClient implements ProxyClient {

    private ApiClientConnectorInfo connector;
    private String path;
    private String method;
    private Object body;
    private Class<?> responseType;
    private Object response;
    private ResponseEntity<?> entityResponse;
    private CompletableFuture<?> asyncResponse;
    private RuntimeException failure;

    static RecordingProxyClient aRecordingProxyClient() {
        return new RecordingProxyClient();
    }

    RecordingProxyClient respondWith(Object response) {
        this.response = response;
        return this;
    }

    RecordingProxyClient respondWithEntity(ResponseEntity<?> response) {
        entityResponse = response;
        return this;
    }

    RecordingProxyClient respondAsyncWith(Object response) {
        asyncResponse = CompletableFuture.completedFuture(response);
        return this;
    }

    RecordingProxyClient failWith(RuntimeException failure) {
        this.failure = failure;
        return this;
    }

    <T> T recordedBody(Class<T> bodyType) {
        return bodyType.cast(body);
    }

    void assertCall(ApiClientConnectorInfo expectedConnector, String expectedPath, String expectedMethod,
                    Object expectedBody, Class<?> expectedResponseType) {
        assertSame(expectedConnector, connector);
        assertEquals(expectedPath, path);
        assertEquals(expectedMethod, method);
        assertSame(expectedBody, body);
        assertEquals(expectedResponseType, responseType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T sendRequest(ApiClientConnectorInfo connector, String path, String method,
                             Object body, Class<T> responseType) {
        recordCall(connector, path, method, body, responseType);
        throwFailure();
        return (T) response;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ResponseEntity<T> sendRequestForEntity(ApiClientConnectorInfo connector, String path,
                                                      String method, Object body, Class<T> responseType) {
        recordCall(connector, path, method, body, responseType);
        throwFailure();
        return (ResponseEntity<T>) entityResponse;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<T> sendRequestAsync(ApiClientConnectorInfo connector, String path,
                                                     String method, Object body, Class<T> responseType) {
        recordCall(connector, path, method, body, responseType);
        throwFailure();
        return (CompletableFuture<T>) asyncResponse;
    }

    private void recordCall(ApiClientConnectorInfo connector, String path, String method,
                            Object body, Class<?> responseType) {
        this.connector = connector;
        this.path = path;
        this.method = method;
        this.body = body;
        this.responseType = responseType;
    }

    private void throwFailure() {
        if (failure != null) {
            throw failure;
        }
    }

    @Override
    public <T> T sendRequest(ApiClientConnectorInfo connector, String path, String method,
                             Object body, Class<T> responseType, Duration timeout) {
        throw unsupported("sendRequest with timeout");
    }

    @Override
    public <T> T sendRequest(ApiClientConnectorInfo connector, String path, String method,
                             Map<String, String> pathVariables, Object body, Class<T> responseType) {
        throw unsupported("sendRequest with path variables");
    }

    @Override
    public <T> CompletableFuture<T> sendRequestAsync(ApiClientConnectorInfo connector, String path,
                                                     String method, Object body, Class<T> responseType,
                                                     Duration timeout) {
        throw unsupported("sendRequestAsync with timeout");
    }

    @Override
    public <T> CompletableFuture<T> sendRequestAsync(ApiClientConnectorInfo connector, String path,
                                                     String method, Map<String, String> pathVariables,
                                                     Object body, Class<T> responseType, Duration timeout) {
        throw unsupported("sendRequestAsync with path variables");
    }

    @Override
    public void sendFireAndForget(ApiClientConnectorInfo connector, String path, String method, Object body) {
        throw unsupported("sendFireAndForget");
    }

    @Override
    public void sendFireAndForget(ApiClientConnectorInfo connector, String path, String method,
                                  Object body, String messageType) {
        throw unsupported("sendFireAndForget with message type");
    }

    private static UnsupportedOperationException unsupported(String operation) {
        return new UnsupportedOperationException("Unexpected proxy operation: " + operation);
    }
}
