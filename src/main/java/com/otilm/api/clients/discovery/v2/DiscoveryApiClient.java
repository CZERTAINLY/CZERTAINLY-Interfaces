package com.otilm.api.clients.discovery.v2;

import com.otilm.api.clients.ApiClientConnectorInfo;
import com.otilm.api.clients.BaseApiClient;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import javax.net.ssl.TrustManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WebClient (HTTP) implementation of the v2 Discovery API client.
 *
 * <p>This client uses the injected, shared {@code webClient} unmodified. The in-memory
 * response-size bound is a {@link com.otilm.api.clients.ClientTuning#maxInMemorySize()} setting on
 * that shared {@code WebClient}, so every client sharing it shares the same bound; a response
 * exceeding it fails the call, mapped by {@link BaseApiClient#processRequest} to
 * {@link com.otilm.api.exception.ConnectorServerException}. The bound cannot be made per-client:
 * {@link BaseApiClient} caches CERTIFICATE-auth WebClients process-wide keyed only by connector
 * UUID, and one connector can implement several provider interfaces under a single UUID, so a
 * per-client cap would land on another client's connector depending on cache-population order.
 *
 * <p><b>Why the list operations decode arrays rather than streaming element lists.</b> The bound
 * only covers a whole response on the {@code toEntity} path, which joins the response into one
 * buffer before decoding. {@code toEntityList(X.class)} streams the array through Spring's
 * {@code Jackson2Tokenizer}, which resets its byte counter every time a top-level element
 * completes, so the cap degrades into a per-element cap and the {@code collectList} assembling the
 * result is unbounded — a connector returning a million tiny objects would not be bounded at all.
 * The three list operations therefore request an array type ({@code X[].class}) and wrap the
 * result, routing through the codec's bounded {@code decodeToMono} path.
 *
 * <p><b>No per-operation timeout budget over REST.</b> Unlike the MQ client, which sizes each call
 * with {@link com.otilm.api.clients.mq.discovery.v2.DiscoveryMqTimeouts}, every call here —
 * including a {@code results} drain that legitimately runs far longer than a status poll — is capped
 * by the single global {@link com.otilm.api.clients.ClientTuning#responseTimeout()} read guard on
 * the shared {@code WebClient} (35s by default). Raising the drain budget means raising that shared
 * {@code responseTimeout}, which lengthens the read guard for every client built on the same
 * WebClient.
 */
@SuppressWarnings("java:S1075") // contract paths, not configurable URIs
public class DiscoveryApiClient extends BaseApiClient implements DiscoverySyncApiClient {

    private static final Logger logger = LoggerFactory.getLogger(DiscoveryApiClient.class);

    public DiscoveryApiClient(WebClient webClient, TrustManager[] defaultTrustManagers) {
        this.webClient = webClient;
        this.defaultTrustManagers = defaultTrustManagers;
    }

    @Override
    public List<DiscoverySupportedResourceDto> listSupportedResources(ApiClientConnectorInfo connector) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> toMutableList(requireBody(r
                        .uri(connector.getUrl() + DiscoveryPaths.RESOURCES)
                        .retrieve()
                        .toEntity(DiscoverySupportedResourceDto[].class), "listSupportedResources")),
                request,
                connector);
    }

    @Override
    public List<BaseAttribute> listRunAttributes(ApiClientConnectorInfo connector) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> toMutableList(requireBody(r
                        .uri(connector.getUrl() + DiscoveryPaths.ATTRIBUTES)
                        .retrieve()
                        .toEntity(BaseAttribute[].class), "listRunAttributes")),
                request,
                connector);
    }

    /**
     * {@code resource} binds to the URL by its wire code ({@code "certificates"}, {@code "keys"}),
     * never the Java enum name — {@link Resource#getCode()}.
     */
    @Override
    public List<BaseAttribute> listResourceAttributes(ApiClientConnectorInfo connector, Resource resource) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> toMutableList(requireBody(r
                        .uri(connector.getUrl() + DiscoveryPaths.resourceAttributes(resource))
                        .retrieve()
                        .toEntity(BaseAttribute[].class), "listResourceAttributes")),
                request,
                connector);
    }

    @Override
    public DiscoveryInitiateResponseDto initiate(ApiClientConnectorInfo connector, DiscoveryInitiateRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> requireBody(r
                        .uri(connector.getUrl() + DiscoveryPaths.INITIATE)
                        .body(Mono.just(requestDto), DiscoveryInitiateRequestDto.class)
                        .retrieve()
                        .toEntity(DiscoveryInitiateResponseDto.class), "initiate"),
                request,
                connector);
    }

    @Override
    public DiscoveryStatusResponseDto status(ApiClientConnectorInfo connector, DiscoveryRunRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> requireBody(r
                        .uri(connector.getUrl() + DiscoveryPaths.STATUS)
                        .body(Mono.just(requestDto), DiscoveryRunRequestDto.class)
                        .retrieve()
                        .toEntity(DiscoveryStatusResponseDto.class), "status"),
                request,
                connector);
    }

    @Override
    public DiscoveryResultsResponseDto results(ApiClientConnectorInfo connector, DiscoveryDrainRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> requireBody(r
                        .uri(connector.getUrl() + DiscoveryPaths.RESULTS)
                        .body(Mono.just(requestDto), DiscoveryDrainRequestDto.class)
                        .retrieve()
                        .toEntity(DiscoveryResultsResponseDto.class), "results"),
                request,
                connector);
    }

    @Override
    public DiscoveryStopResponseDto stop(ApiClientConnectorInfo connector, DiscoveryRunRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> requireBody(r
                        .uri(connector.getUrl() + DiscoveryPaths.STOP)
                        .body(Mono.just(requestDto), DiscoveryRunRequestDto.class)
                        .retrieve()
                        .toEntity(DiscoveryStopResponseDto.class), "stop"),
                request,
                connector);
    }

    @Override
    public DiscoveryInitiateResponseDto resume(ApiClientConnectorInfo connector, DiscoveryRunRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> requireBody(r
                        .uri(connector.getUrl() + DiscoveryPaths.RESUME)
                        .body(Mono.just(requestDto), DiscoveryRunRequestDto.class)
                        .retrieve()
                        .toEntity(DiscoveryInitiateResponseDto.class), "resume"),
                request,
                connector);
    }

    /**
     * A 404 on cancel means the connector no longer tracks the run — an already-terminal
     * cancellation, i.e. success. It is surfaced through the returned {@code ResponseEntity}'s status
     * rather than as a thrown exception, which is why this method returns {@code ResponseEntity<Void>}
     * instead of {@code void}. The 404 arrives in either of two shapes: a
     * {@link ConnectorProblemException} carrying a not-tracked {@code errorCode} for a conformant
     * {@code application/problem+json} body, matched by error code through
     * {@link ConnectorOperationErrorCodes#isOperationNotTracked} rather than by transport status; or a
     * {@link ConnectorEntityNotFoundException} for a terse non-problem-json 404,
     * {@code BaseApiClient}'s legacy fallback. Every other failure (422 refusal, transport errors)
     * flows through the standard {@link BaseApiClient#processRequest} mapping unchanged.
     *
     * <p>The terse shape is ambiguous: the shared connector contract also documents 404 as "endpoint
     * not found or not implemented" (see {@code AuthProtectedConnectorController}), so a connector
     * that never implemented {@code /discoveries/cancel}, or one reached through a stale base URL, is
     * indistinguishable from a run that really is gone. Swallowing it silently would report a failed
     * abort as a successful one, so it is accepted but logged at WARN naming the connector and path.
     *
     * <p>{@link com.otilm.api.clients.v3.CertificateApiClient} deliberately does the opposite and
     * propagates a 404 from its {@code cancelIssue}/{@code cancelRevoke}/{@code cancelRegister}
     * calls. Cancelling a discovery run is idempotent-terminal — a run the connector no longer tracks
     * is already in the state cancel asked for. A certificate cancellation is not: there the 404 only
     * says the operation handle is unknown, not that the operation stopped, and Core may still have an
     * issued certificate to reconcile, so reporting a successful abort would lose work.
     */
    @Override
    public ResponseEntity<Void> cancel(ApiClientConnectorInfo connector, DiscoveryRunRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);
        String cancelUrl = connector.getUrl() + DiscoveryPaths.CANCEL;

        return processRequest(r -> {
            // Attached to the exchange stage alone, not the assembled chain, so only a not-found
            // raised by this request's own response can be intercepted.
            Mono<ResponseEntity<Void>> exchange = r
                    .uri(cancelUrl)
                    .body(Mono.just(requestDto), DiscoveryRunRequestDto.class)
                    .retrieve()
                    .toBodilessEntity();

            return requireResponse(exchange
                    .onErrorResume(ex -> isRunNotTracked(ex, connector, cancelUrl)
                            ? Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).<Void>build())
                            : Mono.error(ex)), "cancel");
        }, request, connector);
    }

    /**
     * True when a cancel failure should be read as "the connector no longer tracks this run" and
     * turned back into a 404 response.
     *
     * <p>{@link Exceptions#unwrap} runs first because Reactor wraps propagated errors. The response
     * filter happens to emit unwrapped into {@code onErrorResume} today, so matching the raw
     * throwable works by accident; any future wrapping would silently turn a swallowed 404 back into
     * a hard failure.
     *
     * <p>The conformant shape is recognised by error code via
     * {@link ConnectorOperationErrorCodes#isOperationNotTracked}. The terse shape is accepted too but
     * logged — see {@code cancel} for why a bare 404 is ambiguous yet not fatal.
     */
    private static boolean isRunNotTracked(Throwable error, ApiClientConnectorInfo connector, String cancelUrl) {
        Throwable unwrapped = Exceptions.unwrap(error);
        if (unwrapped instanceof ConnectorProblemException cpe) {
            return ConnectorOperationErrorCodes.isOperationNotTracked(cpe.getProblemDetail().getErrorCode());
        }
        if (unwrapped instanceof ConnectorEntityNotFoundException) {
            logger.warn("Connector {} answered cancel at {} with a 404 carrying no not-tracked error code;"
                            + " treating the run as already terminal on weaker evidence.",
                    connector.getName(), cancelUrl);
            return true;
        }
        return false;
    }

    /**
     * Wrap a decoded response array as the mutable list the interface promises: a fresh
     * {@link ArrayList} rather than an {@link Arrays#asList} view, with an absent array read as "no
     * items". Matches the MQ client's helper, so in-place sorting or filtering behaves the same on
     * both transports.
     */
    private static <T> List<T> toMutableList(T[] result) {
        return result == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(result));
    }
}
