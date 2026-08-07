package com.otilm.api.clients.mq.discovery.v2;

import com.otilm.api.clients.ApiClientConnectorInfo;
import com.otilm.api.clients.discovery.v2.DiscoveryPaths;
import com.otilm.api.clients.mq.ProxyClient;
import com.otilm.api.exception.ConnectorEntityNotFoundException;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.exception.ConnectorProblemException;
import com.otilm.api.interfaces.client.v2.DiscoverySyncApiClient;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.common.error.ConnectorOperationErrorCodes;
import com.otilm.api.model.connector.discovery.v2.DiscoveryDrainRequestDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryInitiateRequestDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryInitiateResponseDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryResultsResponseDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryRunRequestDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryStatusResponseDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryStopResponseDto;
import com.otilm.api.model.connector.discovery.v2.DiscoverySupportedResourceDto;
import com.otilm.api.model.core.auth.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * MQ-based implementation of the v2 Discovery API client.
 *
 * <p>Path constants mirror {@link com.otilm.api.clients.discovery.v2.DiscoveryApiClient}
 * character-for-character — both transports must address the same connector contract.
 *
 * <p>No {@code stream} method exists here: NDJSON streaming has no MQ representation (a
 * request/response proxy hop cannot carry a chunked stream). Core selects the REST client when it
 * needs streaming; see {@link DiscoverySyncApiClient}.
 *
 * <p>Every call passes an explicit {@code Duration} sized by {@link DiscoveryMqTimeouts} — this
 * client never invokes a {@link ProxyClient} overload that falls back to the proxy's default
 * timeout, since a discovery drain can legitimately take far longer than a status poll or a
 * lifecycle control call.
 *
 * <p>The proxy hands back {@code null} for a relayed response that carried no body, so every
 * operation guards it and fails naming the operation, rather than passing {@code null} on to Core
 * where it would resurface as a {@link NullPointerException} far from its cause. A list route that
 * answers with no body at all is non-conformant, not empty — REST rejects it the same way, so the
 * two transports report an identical failure. Lists that do arrive are mutable, matching the
 * mutable Jackson list REST returns, so a caller sorting in place behaves the same on both.
 */
@SuppressWarnings("java:S1075") // contract paths, not configurable URIs
public class DiscoveryApiClient implements DiscoverySyncApiClient {



    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";

    private final ProxyClient proxyClient;
    private final DiscoveryMqTimeouts timeouts;

    /**
     * Both collaborators are validated eagerly — as {@link DiscoveryMqTimeouts} and
     * {@link com.otilm.api.clients.ClientTuning} validate their own arguments — so misconfigured
     * wiring fails at bean construction instead of on the first connector call.
     */
    public DiscoveryApiClient(ProxyClient proxyClient, DiscoveryMqTimeouts timeouts) {
        this.proxyClient = Objects.requireNonNull(proxyClient, "proxyClient is required");
        this.timeouts = Objects.requireNonNull(timeouts, "timeouts is required");
    }

    /**
     * Convenience constructor using {@link DiscoveryMqTimeouts#defaults()}, so Core can adopt this
     * client before it exposes timeout configuration properties.
     */
    public DiscoveryApiClient(ProxyClient proxyClient) {
        this(proxyClient, DiscoveryMqTimeouts.defaults());
    }

    @Override
    public List<DiscoverySupportedResourceDto> listSupportedResources(ApiClientConnectorInfo connector) throws ConnectorException {
        DiscoverySupportedResourceDto[] result = proxyClient.sendRequest(connector, DiscoveryPaths.RESOURCES, HTTP_METHOD_GET, null,
                DiscoverySupportedResourceDto[].class, timeouts.control());
        return toMutableList(result, "listSupportedResources", connector);
    }

    @Override
    public List<BaseAttribute> listRunAttributes(ApiClientConnectorInfo connector) throws ConnectorException {
        BaseAttribute[] result = proxyClient.sendRequest(connector, DiscoveryPaths.ATTRIBUTES, HTTP_METHOD_GET, null,
                BaseAttribute[].class, timeouts.control());
        return toMutableList(result, "listRunAttributes", connector);
    }

    /**
     * {@code resource} binds to the path by its wire code ({@code "certificates"}, {@code "keys"}),
     * never the Java enum name — {@link Resource#getCode()}.
     */
    @Override
    public List<BaseAttribute> listResourceAttributes(ApiClientConnectorInfo connector, Resource resource) throws ConnectorException {
        String path = DiscoveryPaths.resourceAttributes(resource);
        BaseAttribute[] result = proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null,
                BaseAttribute[].class, timeouts.control());
        return toMutableList(result, "listResourceAttributes", connector);
    }

    @Override
    public DiscoveryInitiateResponseDto initiate(ApiClientConnectorInfo connector, DiscoveryInitiateRequestDto request) throws ConnectorException {
        return requireBody(proxyClient.sendRequest(connector, DiscoveryPaths.INITIATE, HTTP_METHOD_POST, request,
                DiscoveryInitiateResponseDto.class, timeouts.control()), "initiate", connector);
    }

    @Override
    public DiscoveryStatusResponseDto status(ApiClientConnectorInfo connector, DiscoveryRunRequestDto request) throws ConnectorException {
        return requireBody(proxyClient.sendRequest(connector, DiscoveryPaths.STATUS, HTTP_METHOD_POST, request,
                DiscoveryStatusResponseDto.class, timeouts.status()), "status", connector);
    }

    @Override
    public DiscoveryResultsResponseDto results(ApiClientConnectorInfo connector, DiscoveryDrainRequestDto request) throws ConnectorException {
        return requireBody(proxyClient.sendRequest(connector, DiscoveryPaths.RESULTS, HTTP_METHOD_POST, request,
                DiscoveryResultsResponseDto.class, timeouts.drain()), "results", connector);
    }

    @Override
    public DiscoveryStopResponseDto stop(ApiClientConnectorInfo connector, DiscoveryRunRequestDto request) throws ConnectorException {
        return requireBody(proxyClient.sendRequest(connector, DiscoveryPaths.STOP, HTTP_METHOD_POST, request,
                DiscoveryStopResponseDto.class, timeouts.control()), "stop", connector);
    }

    @Override
    public DiscoveryInitiateResponseDto resume(ApiClientConnectorInfo connector, DiscoveryRunRequestDto request) throws ConnectorException {
        return requireBody(proxyClient.sendRequest(connector, DiscoveryPaths.RESUME, HTTP_METHOD_POST, request,
                DiscoveryInitiateResponseDto.class, timeouts.control()), "resume", connector);
    }

    /**
     * Uses {@link ProxyClient#sendRequestForEntity(ApiClientConnectorInfo, String, String, Object, Class, java.time.Duration)}
     * rather than {@code sendRequest} because the caller needs the status, not the (absent) body.
     *
     * <p>A 404 means the connector no longer tracks the run — the terminal state cancel was asking
     * for, so Core reads it as an already-terminal cancellation rather than an error. The proxy
     * classifies that 404 into a thrown {@link ConnectorEntityNotFoundException}, and would raise a
     * {@link ConnectorProblemException} once it relays {@code application/problem+json}; both shapes
     * are caught here (the problem one by error code, via
     * {@link ConnectorOperationErrorCodes#isOperationNotTracked}) and returned as a {@code 404}
     * response, exactly as the REST client does. Otherwise the identical call would succeed on REST
     * and hard-fail on MQ, defeating the sole reason this method returns {@code ResponseEntity<Void>}.
     * Every other failure — the {@code 422} past-the-point-of-no-return refusal, transport errors,
     * ... — propagates untouched.
     *
     * <p>Any successful status the proxy reports is normalized to {@code 204}, cancel's only contract
     * success status. The {@code Duration} overload of {@code sendRequestForEntity} is a
     * {@code default} method that wraps the body in {@code ResponseEntity.ok}, so without this an MQ
     * cancel would answer {@code 200} where the controller declares {@code 204} — and a Core adapter
     * that branches on exact status values would reject it. Normalizing here makes both transports
     * agree regardless of when (or whether) Core overrides that overload, which is why
     * {@link ProxyClient} itself is left alone.
     */
    @Override
    public ResponseEntity<Void> cancel(ApiClientConnectorInfo connector, DiscoveryRunRequestDto request) throws ConnectorException {
        ResponseEntity<Void> response;
        try {
            response = proxyClient.sendRequestForEntity(connector, DiscoveryPaths.CANCEL, HTTP_METHOD_POST, request,
                    Void.class, timeouts.control());
        } catch (ConnectorEntityNotFoundException | ConnectorProblemException e) {
            if (!isRunNotTracked(e)) {
                throw e;
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).<Void>build();
        }
        if (response == null) {
            throw new ConnectorException("No response received from connector for cancel", connector);
        }
        // A non-2xx entity (a proxy that one day relays the 404 instead of throwing it) already
        // carries the status the caller reads, so it passes through unchanged.
        return response.getStatusCode().is2xxSuccessful()
                ? ResponseEntity.noContent().build()
                : response;
    }

    private static boolean isRunNotTracked(ConnectorException ex) {
        if (ex instanceof ConnectorEntityNotFoundException) {
            return true;
        }
        return ex instanceof ConnectorProblemException cpe
                && ConnectorOperationErrorCodes.isOperationNotTracked(cpe.getProblemDetail().getErrorCode());
    }

    /**
     * An absent body is a non-conformant response, not "no items": a list route must return a JSON
     * array, and reading a missing body as empty would make a broken connector indistinguishable from
     * one that genuinely reports nothing — for {@code listSupportedResources} that is a distinction
     * Core acts on. REST rejects it identically via {@code BaseApiClient.requireBody}, and the
     * single-DTO operations here already throw, so all three agree.
     *
     * <p>The list is a fresh {@link ArrayList} rather than an {@link Arrays#asList} view because REST
     * hands back a mutable Jackson list: a caller that sorts or filters in place must behave the same
     * on both transports.
     */
    private static <T> List<T> toMutableList(T[] result, String operation, ApiClientConnectorInfo connector)
            throws ConnectorException {
        return new ArrayList<>(Arrays.asList(requireBody(result, operation, connector)));
    }

    /**
     * The MQ counterpart of {@code BaseApiClient.requireBody}: for an operation whose 2xx response
     * must carry a payload, a bodiless response is a connector contract violation, so it fails here
     * naming the operation instead of returning the proxy's {@code null}. Unlike the REST side's
     * {@link IllegalStateException} this uses the declared {@link ConnectorException} channel and
     * attributes the failure to the connector, so a caller can both catch it and tell which
     * connector produced it.
     */
    private static <T> T requireBody(T body, String operation, ApiClientConnectorInfo connector) throws ConnectorException {
        if (body == null) {
            throw new ConnectorException("Connector returned an empty body for " + operation, connector);
        }
        return body;
    }
}
