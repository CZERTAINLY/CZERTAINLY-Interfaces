package com.otilm.api.clients.discovery.v2;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.otilm.api.clients.BaseApiClient;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.exception.ConnectorProblemException;
import com.otilm.api.exception.ConnectorServerException;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.common.error.ErrorCode;
import com.otilm.api.model.connector.discovery.v2.DiscoveryDrainRequestDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryInitiateRequestDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryInitiateResponseDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryResultsResponseDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryRunRequestDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryStatusResponseDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryStopResponseDto;
import com.otilm.api.model.connector.discovery.v2.DiscoverySupportedResourceDto;
import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.connector.ConnectorDto;
import com.otilm.api.model.core.connector.ConnectorStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBufferLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class DiscoveryApiClientTest {

    private static final UUID RUN_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");

    /** Named so the swallowed-404 WARN can be asserted to identify the connector it came from. */
    private static final String CONNECTOR_NAME = "discovery-connector-under-test";

    private static final String CANCEL_PATH = "/v2/discoveryProvider/discoveries/cancel";

    private DiscoveryApiClient client;
    private ConnectorDto connector;
    private WireMockServer mockServer;

    @BeforeEach
    void setUp() {
        client = new DiscoveryApiClient(BaseApiClient.prepareWebClient(), null);

        mockServer = new WireMockServer(options().dynamicPort());
        mockServer.start();
        WireMock.configureFor("localhost", mockServer.port());

        connector = new ConnectorDto();
        connector.setName(CONNECTOR_NAME);
        connector.setUrl("http://localhost:" + mockServer.port());
        connector.setStatus(ConnectorStatus.CONNECTED);
    }

    @AfterEach
    void tearDown() {
        mockServer.stop();
    }

    @Test
    void listSupportedResources_returnsResources() throws ConnectorException {
        String json = """
                [
                  { "resource": "certificates" },
                  { "resource": "keys", "capabilities": [] }
                ]
                """;
        mockServer.stubFor(WireMock.get("/v2/discoveryProvider/resources")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(json)));

        List<DiscoverySupportedResourceDto> result = client.listSupportedResources(connector);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(Resource.CERTIFICATE, result.get(0).getResource());
        Assertions.assertEquals(Resource.CRYPTOGRAPHIC_KEY, result.get(1).getResource());
        // Absent (entry 0) vs empty (entry 1) capabilities are contractually distinct — see
        // DiscoverySupportedResourceDto's javadoc — and a client round-trip is exactly where a
        // careless deserializer would normalize one into the other.
        Assertions.assertNull(result.get(0).getCapabilities());
        Assertions.assertNotNull(result.get(1).getCapabilities());
        Assertions.assertTrue(result.get(1).getCapabilities().isEmpty());
    }

    /**
     * The list operations decode an array and wrap it, so the wrapper — not Jackson or Reactor —
     * decides mutability. Both transports must hand back a list a caller can sort or filter in place;
     * the MQ client documents the same contract, and a caller that works on one transport must not
     * break on the other.
     */
    @Test
    void listSupportedResources_returnsMutableList() throws ConnectorException {
        mockServer.stubFor(WireMock.get("/v2/discoveryProvider/resources")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("[{\"resource\":\"certificates\"}]")));

        List<DiscoverySupportedResourceDto> result = client.listSupportedResources(connector);

        Assertions.assertDoesNotThrow(() -> result.add(new DiscoverySupportedResourceDto()));
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void listRunAttributes_returnsAttributes() throws ConnectorException {
        String json = """
                [
                  { "uuid": "11111111-1111-1111-1111-111111111111", "name": "targetAddress", "type": "data", "contentType": "string", "version": 2 }
                ]
                """;
        mockServer.stubFor(WireMock.get("/v2/discoveryProvider/attributes")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(json)));

        List<BaseAttribute> result = client.listRunAttributes(connector);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("targetAddress", result.get(0).getName());
    }

    /**
     * The path segment is the resource's wire code ({@code "keys"}), never the Java enum name
     * ({@code CRYPTOGRAPHIC_KEY}) — verified by pinning the stub to the literal path.
     */
    @Test
    void listResourceAttributes_usesResourceWireCodeInPath() throws ConnectorException {
        String json = """
                [
                  { "uuid": "22222222-2222-2222-2222-222222222222", "name": "keySize", "type": "data", "contentType": "integer", "version": 2 }
                ]
                """;
        mockServer.stubFor(WireMock.get("/v2/discoveryProvider/keys/attributes")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(json)));

        List<BaseAttribute> result = client.listResourceAttributes(connector, Resource.CRYPTOGRAPHIC_KEY);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("keySize", result.get(0).getName());
    }

    /**
     * Body matchers, not just path and method: without them a client that dropped {@code .body(...)},
     * sent the wrong DTO, or lost {@code runId} would still match the stub and pass. The
     * {@code resources} entry is pinned to the wire code {@code "certificates"}, never the Java enum
     * name {@code CERTIFICATE} — the request body is the other half of the wire-code contract already
     * pinned on the path side by {@code listResourceAttributes_usesResourceWireCodeInPath}.
     */
    @Test
    void initiate_returnsMeta() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/initiate")
                .withRequestBody(WireMock.matchingJsonPath("$.runId", WireMock.equalTo(RUN_ID.toString())))
                .withRequestBody(WireMock.matchingJsonPath("$.resources[0]", WireMock.equalTo("certificates")))
                .willReturn(WireMock.aResponse()
                        .withStatus(202)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"meta\":[]}")));

        DiscoveryInitiateRequestDto request = new DiscoveryInitiateRequestDto();
        request.setRunId(RUN_ID);
        request.setResources(List.of(Resource.CERTIFICATE));

        DiscoveryInitiateResponseDto response = client.initiate(connector, request);

        Assertions.assertNotNull(response.getMeta());
        Assertions.assertTrue(response.getMeta().isEmpty());
    }

    /** Pins the run-request body shape (the DTO shared by status, stop, resume and cancel). */
    @Test
    void status_returnsRunState() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/status")
                .withRequestBody(WireMock.matchingJsonPath("$.runId", WireMock.equalTo(RUN_ID.toString())))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"state\":\"running\",\"highestSequence\":42}")));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        DiscoveryStatusResponseDto response = client.status(connector, request);

        Assertions.assertEquals("running", response.getState().getCode());
        Assertions.assertEquals(42L, response.getHighestSequence());
    }

    /**
     * Pins the drain body shape, including the {@code afterSequence} cursor — a drain that silently
     * lost its cursor would re-read the run from the start on every poll, which no response assertion
     * would notice.
     */
    @Test
    void results_returnsPage() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/results")
                .withRequestBody(WireMock.matchingJsonPath("$.runId", WireMock.equalTo(RUN_ID.toString())))
                .withRequestBody(WireMock.matchingJsonPath("$.afterSequence", WireMock.equalTo("12")))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"items\":[],\"highestSequence\":7,\"more\":false}")));

        DiscoveryDrainRequestDto request = new DiscoveryDrainRequestDto();
        request.setRunId(RUN_ID);
        request.setAfterSequence(12L);

        DiscoveryResultsResponseDto response = client.results(connector, request);

        Assertions.assertTrue(response.getItems().isEmpty());
        Assertions.assertEquals(7L, response.getHighestSequence());
        Assertions.assertEquals(Boolean.FALSE, response.getMore());
    }

    @Test
    void stop_returnsMeta() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/stop")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"meta\":[]}")));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        DiscoveryStopResponseDto response = client.stop(connector, request);

        Assertions.assertNotNull(response.getMeta());
        Assertions.assertTrue(response.getMeta().isEmpty());
    }

    @Test
    void resume_returnsMeta() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/resume")
                .willReturn(WireMock.aResponse()
                        .withStatus(202)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"meta\":[]}")));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        DiscoveryInitiateResponseDto response = client.resume(connector, request);

        Assertions.assertNotNull(response.getMeta());
        Assertions.assertTrue(response.getMeta().isEmpty());
    }

    @Test
    void cancel_204OnSuccess() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/cancel")
                .willReturn(WireMock.aResponse().withStatus(204)));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        ResponseEntity<Void> response = client.cancel(connector, request);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * A 404 on cancel means the run is already terminal — Core treats this as success, so the
     * client MUST surface it via the returned {@code ResponseEntity}'s status, never as a thrown
     * exception. This is the entire reason {@code cancel} returns {@code ResponseEntity<Void>}.
     */
    @Test
    void cancel_404IsReturnedAsStatusNotException() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/cancel")
                .willReturn(WireMock.aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .withBody(notTrackedProblemJson("OPERATION_NOT_TRACKED",
                                "https://docs.otilm.com/problems/connector/discovery/OPERATION_NOT_TRACKED"))));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        ResponseEntity<Void> response = client.cancel(connector, request);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * A conformant not-tracked 404 needs no operator attention — the connector said in so many words
     * that it does not track the run — so it must NOT produce the diagnostic WARN reserved for the
     * ambiguous bare-404 fallback. Without this, the WARN would fire on every routine cancel of an
     * already-finished run and train operators to ignore it.
     */
    @Test
    void cancel_conformantNotTracked404DoesNotWarn() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/cancel")
                .willReturn(WireMock.aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .withBody(notTrackedProblemJson("OPERATION_NOT_TRACKED",
                                "https://docs.otilm.com/problems/connector/discovery/OPERATION_NOT_TRACKED"))));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        ListAppender<ILoggingEvent> recorder = attachClientLogRecorder();
        ResponseEntity<Void> response;
        try {
            response = client.cancel(connector, request);
        } finally {
            detachClientLogRecorder(recorder);
        }

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertTrue(warnings(recorder).isEmpty(),
                () -> "a conformant not-tracked 404 must not warn, got " + recorder.list);
    }

    /**
     * The not-tracked rule is {@link com.otilm.api.model.common.error.ConnectorOperationErrorCodes},
     * the single shared definition beside {@code ErrorCode}, not a local one-code comparison — it
     * recognises {@code REGISTRATION_NOT_FOUND} as well as {@code OPERATION_NOT_TRACKED}. Pinning the
     * second code keeps this client, the MQ client and Core classifying a connector's answer
     * identically instead of each remembering a different subset of the codes.
     */
    @Test
    void cancel_registrationNotFoundIsSwallowedViaSharedRule() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/cancel")
                .willReturn(WireMock.aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .withBody(notTrackedProblemJson("REGISTRATION_NOT_FOUND",
                                "https://docs.otilm.com/problems/connector/authority/REGISTRATION_NOT_FOUND"))));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        ResponseEntity<Void> response = client.cancel(connector, request);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Regression guard on the 404-swallowing branch added for cancel: a 422 refusal (run past the
     * point of no return) is a real failure and MUST still throw, proving the special-case only
     * intercepts a not-tracked answer, not every {@link ConnectorProblemException}.
     */
    @Test
    void cancel_422StillThrows() {
        String problemJson = """
                {
                  "type": "https://docs.otilm.com/problems/connector/OPERATION_PAST_POINT_OF_NO_RETURN",
                  "title": "Cancel refused — operation past point of no return",
                  "status": 422,
                  "detail": "runId 33333333-3333-3333-3333-333333333333 already committed upstream",
                  "errorCode": "OPERATION_PAST_POINT_OF_NO_RETURN",
                  "timestamp": "2026-08-04T10:00:00Z",
                  "correlationId": "test-corr-cancel-422",
                  "retryable": false
                }
                """;
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/cancel")
                .willReturn(WireMock.aResponse()
                        .withStatus(422)
                        .withHeader("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .withBody(problemJson)));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        ConnectorProblemException ex = Assertions.assertThrows(
                ConnectorProblemException.class,
                () -> client.cancel(connector, request));

        Assertions.assertEquals(ErrorCode.OPERATION_PAST_POINT_OF_NO_RETURN, ex.getProblemDetail().getErrorCode());
        Assertions.assertEquals(connector, ex.getConnector());
    }

    /**
     * A terse-but-conformant connector can answer 404 without an {@code application/problem+json}
     * body (a framework-default error page, for instance). {@code BaseApiClient}'s legacy fallback
     * maps that shape to {@link com.otilm.api.exception.ConnectorEntityNotFoundException} rather
     * than {@link ConnectorProblemException} — cancel must still swallow it into a
     * {@code ResponseEntity} status, or a legitimately-terminal run would be reported as a failure.
     */
    @Test
    void cancel_legacyNotFoundIsReturnedAsStatusNotException() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/cancel")
                .willReturn(WireMock.aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(springDefaultErrorPage())));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        ResponseEntity<Void> response = client.cancel(connector, request);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * The terse 404 can be terser still: no body at all, and no {@code Content-Type}. A Go connector's
     * {@code w.WriteHeader(404)} sends exactly that, and cancel is the likeliest place to meet it since
     * the route is declared bodiless even on success.
     *
     * <p>That shape used to escape the mapping entirely. {@code BaseApiClient}'s legacy 404 branch reads
     * the body for the exception message with {@code bodyToMono(String.class).flatMap(...)}, which never
     * runs for a zero-length body — so the response filter completed empty, no connector exception was
     * ever raised, and an {@link IllegalStateException} escaped instead ("The underlying HTTP client
     * completed without emitting a response" on this bodiless route, {@code requireResponse}'s own
     * message on routes that decode a body). {@code processRequest}'s final {@code else} rethrows that
     * unmapped, so {@code cancel}'s {@code onErrorResume} never saw a
     * {@code ConnectorEntityNotFoundException} and a legitimately-terminal run reached Core as a hard
     * failure of an unrelated, uncatchable type.
     */
    @Test
    void cancel_bodiless404IsReturnedAsStatusNotIllegalState() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/cancel")
                .willReturn(WireMock.aResponse().withStatus(404)));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        ResponseEntity<Void> response = client.cancel(connector, request);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * The lenient bare-404 fallback above is kept because a terse 404 genuinely is ambiguous, but it
     * must be diagnosable: the shared connector contract documents 404 as "endpoint not found or not
     * implemented" too (see {@code AuthProtectedConnectorController}), and the body stubbed here is
     * exactly Spring's unmapped-route error page. A connector that never implemented
     * {@code /discoveries/cancel}, or one reached through a stale base URL, is otherwise reported to
     * Core as an already-terminal cancellation — a silently failed abort. So the swallow must log at
     * WARN, and the line must name both the connector and the path an operator has to go check.
     */
    @Test
    void cancel_bare404IsSwallowedButWarnsNamingConnectorAndPath() throws ConnectorException {
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/cancel")
                .willReturn(WireMock.aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(springDefaultErrorPage())));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        ListAppender<ILoggingEvent> recorder = attachClientLogRecorder();
        ResponseEntity<Void> response;
        try {
            response = client.cancel(connector, request);
        } finally {
            detachClientLogRecorder(recorder);
        }

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        List<ILoggingEvent> warnings = warnings(recorder);
        Assertions.assertEquals(1, warnings.size(),
                () -> "expected exactly one WARN for the swallowed bare 404, got " + recorder.list);
        String message = warnings.get(0).getFormattedMessage();
        Assertions.assertTrue(message.contains(CONNECTOR_NAME),
                () -> "the WARN must name the connector: " + message);
        Assertions.assertTrue(message.contains(CANCEL_PATH),
                () -> "the WARN must name the path: " + message);
    }

    /**
     * Every other lifecycle call (here: status) keeps the standard mapping — a 404 surfaces as
     * {@link ConnectorProblemException} with {@code errorCode=OPERATION_NOT_TRACKED} — unlike
     * cancel, which is special-cased.
     */
    @Test
    void status_404MapsToOperationNotTracked() {
        String problemJson = """
                {
                  "type": "https://docs.otilm.com/problems/connector/discovery/OPERATION_NOT_TRACKED",
                  "title": "Async operation no longer tracked by connector",
                  "status": 404,
                  "detail": "runId 33333333-3333-3333-3333-333333333333 not tracked",
                  "errorCode": "OPERATION_NOT_TRACKED",
                  "timestamp": "2026-08-04T10:00:00Z",
                  "correlationId": "test-corr-status-404",
                  "retryable": false
                }
                """;
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/status")
                .willReturn(WireMock.aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .withBody(problemJson)));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        ConnectorProblemException ex = Assertions.assertThrows(
                ConnectorProblemException.class,
                () -> client.status(connector, request));

        Assertions.assertEquals(ErrorCode.OPERATION_NOT_TRACKED, ex.getProblemDetail().getErrorCode());
        Assertions.assertEquals(connector, ex.getConnector());
    }

    @Test
    void initiate_422MapsToValidationFailed() {
        String problemJson = """
                {
                  "type": "https://docs.otilm.com/problems/common/VALIDATION_FAILED",
                  "title": "Validation failed",
                  "status": 422,
                  "detail": "resource type 'oids' is not supported",
                  "errorCode": "VALIDATION_FAILED",
                  "timestamp": "2026-08-04T10:00:00Z",
                  "correlationId": "test-corr-initiate-422",
                  "retryable": false
                }
                """;
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/initiate")
                .willReturn(WireMock.aResponse()
                        .withStatus(422)
                        .withHeader("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .withBody(problemJson)));

        DiscoveryInitiateRequestDto request = new DiscoveryInitiateRequestDto();
        request.setRunId(RUN_ID);
        request.setResources(List.of(Resource.CERTIFICATE));

        ConnectorProblemException ex = Assertions.assertThrows(
                ConnectorProblemException.class,
                () -> client.initiate(connector, request));

        Assertions.assertEquals(ErrorCode.VALIDATION_FAILED, ex.getProblemDetail().getErrorCode());
        Assertions.assertEquals(connector, ex.getConnector());
    }

    @Test
    void resume_410MapsToCheckpointLost() {
        String problemJson = """
                {
                  "type": "https://docs.otilm.com/problems/connector/discovery/CHECKPOINT_LOST",
                  "title": "Discovery checkpoint lost — run cannot be resumed",
                  "status": 410,
                  "detail": "checkpoint for runId 33333333-3333-3333-3333-333333333333 expired",
                  "errorCode": "CHECKPOINT_LOST",
                  "timestamp": "2026-08-04T10:00:00Z",
                  "correlationId": "test-corr-resume-410",
                  "retryable": false
                }
                """;
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/resume")
                .willReturn(WireMock.aResponse()
                        .withStatus(410)
                        .withHeader("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .withBody(problemJson)));

        DiscoveryRunRequestDto request = new DiscoveryRunRequestDto();
        request.setRunId(RUN_ID);

        ConnectorProblemException ex = Assertions.assertThrows(
                ConnectorProblemException.class,
                () -> client.resume(connector, request));

        Assertions.assertEquals(ErrorCode.CHECKPOINT_LOST, ex.getProblemDetail().getErrorCode());
        Assertions.assertEquals(connector, ex.getConnector());
    }

    /**
     * A {@code results} page larger than the configured read cap must fail fast — never buffer
     * an unbounded/malicious response into memory.
     *
     * <p>The read cap is {@code ClientTuning.maxInMemorySize} on the shared {@code WebClient} —
     * see {@code DiscoveryApiClient}'s class javadoc — so it can't be dialed down for just
     * this test via the shared, process-wide {@code BaseApiClient.prepareWebClient()} singleton
     * without either being ignored (if another test already claimed the default tuning first) or
     * permanently shrinking the cap for every other test in the same JVM run. Instead this test
     * builds its own throwaway {@code WebClient} with a small cap, proving the exact contract this
     * client depends on: whatever cap the injected {@code WebClient} enforces, {@code results}
     * surfaces the resulting failure through the standard error path rather than swallowing it.
     *
     * <p>The stubbed body is genuine, syntactically valid JSON — a real {@code DiscoveryResultsResponseDto}
     * shape plus one padding field — sized comfortably over the cap, so the failure demonstrably
     * comes from the size gate tripping before parsing, not from malformed input coincidentally
     * failing around the same size.
     *
     * <p>The cap is enforced by the codec while decoding the (2xx) response body, a step that
     * runs inside {@code WebClient.retrieve()} itself. Spring's {@code retrieve()} wraps any
     * body-decode failure — including a codec limit breach — into a {@link WebClientResponseException}
     * so the failure still carries the response's status/headers; the original
     * {@link DataBufferLimitException} survives as its cause. {@code BaseApiClient.processRequest}
     * now maps that (either shape) to {@code ConnectorServerException} (see {@code BaseApiClientTest}
     * for that mapping's own direct coverage) — asserted here via the class this client's caller
     * actually sees.
     */
    @Test
    void results_oversizedResponse_failsInsteadOfBuffering() {
        int smallCap = 8 * 1024;
        DiscoveryApiClient smallCapClient = smallCapClient(smallCap);

        // Genuinely valid JSON — a real DiscoveryResultsResponseDto shape with one oversized
        // padding string — at 4x the cap (32 KB body against an 8 KB cap).
        String oversizedBody = "{\"items\":[],\"highestSequence\":0,\"more\":false,\"padding\":\""
                + "a".repeat(smallCap * 4) + "\"}";
        mockServer.stubFor(WireMock.post("/v2/discoveryProvider/discoveries/results")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(oversizedBody)));

        DiscoveryDrainRequestDto request = new DiscoveryDrainRequestDto();
        request.setRunId(RUN_ID);

        ConnectorServerException ex = Assertions.assertThrows(
                ConnectorServerException.class,
                () -> smallCapClient.results(connector, request));

        // The status carried is the one the connector really sent (200), not a synthesized 413 — see
        // BaseApiClient.upstreamStatus and BaseApiClientTest for that mapping's own coverage.
        Assertions.assertEquals(HttpStatus.OK, ex.getHttpStatus());
        Assertions.assertInstanceOf(WebClientResponseException.class, ex.getCause());
        Assertions.assertInstanceOf(DataBufferLimitException.class, ex.getCause().getCause());
    }

    /**
     * The read cap must bound a whole list response, not each of its elements.
     *
     * <p>This is the case the cap silently failed to cover: {@code toEntityList(X.class)} streams the
     * array through Spring's {@code Jackson2Tokenizer}, whose {@code assertInMemorySize} resets its
     * byte counter every time a top-level element completes, so the cap degrades into a per-element
     * cap and the {@code collectList} that assembles the result is unbounded. Every element below is
     * a couple of dozen bytes — far under the cap — while the array as a whole is several times over
     * it, which is exactly the shape a connector returning a million tiny objects would produce, and
     * exactly the shape an element-wise cap waves through. Decoding an array type instead routes
     * through the codec's bounded {@code decodeToMono}, so the whole response is measured and the
     * call fails rather than assembling.
     */
    @Test
    void listSupportedResources_oversizedArrayOfSmallElements_failsInsteadOfAssembling() {
        int smallCap = 8 * 1024;
        DiscoveryApiClient smallCapClient = smallCapClient(smallCap);

        // ~27 bytes per element, 1000 elements: no single element is anywhere near the cap, the array
        // is roughly 3.5x over it.
        String oversizedArray = "[" + String.join(",",
                Collections.nCopies(1000, "{\"resource\":\"certificates\"}")) + "]";
        Assertions.assertTrue(oversizedArray.length() > smallCap * 3,
                "the stubbed array must be comfortably over the cap");
        mockServer.stubFor(WireMock.get("/v2/discoveryProvider/resources")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(oversizedArray)));

        ConnectorServerException ex = Assertions.assertThrows(
                ConnectorServerException.class,
                () -> smallCapClient.listSupportedResources(connector));

        // As above: the connector's own status (200), never a synthesized 413.
        Assertions.assertEquals(HttpStatus.OK, ex.getHttpStatus());
    }

    /**
     * A throwaway {@code WebClient} with its own small read cap. The shared
     * {@code BaseApiClient.prepareWebClient()} singleton cannot be re-tuned per test — see
     * {@code results_oversizedResponse_failsInsteadOfBuffering} for why — so cap-sensitive cases
     * build their own.
     */
    private DiscoveryApiClient smallCapClient(int maxInMemorySize) {
        WebClient smallCapWebClient = WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(c -> c.defaultCodecs().maxInMemorySize(maxInMemorySize))
                        .build())
                .build();
        return new DiscoveryApiClient(smallCapWebClient, null);
    }

    /** An RFC 9457 body whose {@code errorCode} the shared not-tracked rule must recognise. */
    private static String notTrackedProblemJson(String errorCode, String type) {
        return """
                {
                  "type": "%s",
                  "title": "Async operation no longer tracked by connector",
                  "status": 404,
                  "detail": "runId 33333333-3333-3333-3333-333333333333 not tracked",
                  "errorCode": "%s",
                  "timestamp": "2026-08-04T10:00:00Z",
                  "correlationId": "test-corr-cancel-404",
                  "retryable": false
                }
                """.formatted(type, errorCode);
    }

    /**
     * Spring's default unmapped-route error page: a 404 with no {@code application/problem+json} body,
     * which is what a connector that never implemented the endpoint actually returns.
     */
    private static String springDefaultErrorPage() {
        return "{\"timestamp\":\"2026-08-06T10:00:00Z\",\"status\":404,\"error\":\"Not Found\","
                + "\"path\":\"" + CANCEL_PATH + "\"}";
    }

    /**
     * Attach a recorder to this client's own logger so a test can assert on the events it emits.
     *
     * <p>logback-classic is this module's SLF4J provider (slf4j-simple is on the test classpath too
     * but loses provider selection), so the SLF4J logger really is a logback one; asserting the type
     * makes a provider change fail loudly instead of silently skipping the log assertions. The level
     * is pinned so the assertions do not depend on the ambient root level, and restored to inherited
     * afterwards — the module ships no logback configuration, so inherited is the original state.
     */
    private static ListAppender<ILoggingEvent> attachClientLogRecorder() {
        Logger clientLogger = Assertions.assertInstanceOf(Logger.class,
                LoggerFactory.getLogger(DiscoveryApiClient.class),
                "expected logback-classic to be the active SLF4J provider");
        ListAppender<ILoggingEvent> recorder = new ListAppender<>();
        recorder.setContext(clientLogger.getLoggerContext());
        recorder.start();
        clientLogger.setLevel(Level.WARN);
        clientLogger.addAppender(recorder);
        return recorder;
    }

    private static void detachClientLogRecorder(ListAppender<ILoggingEvent> recorder) {
        Logger clientLogger = (Logger) LoggerFactory.getLogger(DiscoveryApiClient.class);
        clientLogger.detachAppender(recorder);
        clientLogger.setLevel(null);
        recorder.stop();
    }

    private static List<ILoggingEvent> warnings(ListAppender<ILoggingEvent> recorder) {
        return recorder.list.stream()
                .filter(event -> event.getLevel() == Level.WARN)
                .toList();
    }
}
