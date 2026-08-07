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
 * <p>Path constants are part of the v2 discovery connector API contract — they describe the
 * routes a v2 discovery connector implementation must expose, not configurable URIs.
 *
 * <p>This client uses the injected, shared {@code webClient} unmodified — it does not mutate or
 * fork it. The in-memory response-size bound that protects {@code results} (whose pages can run to
 * multiple megabytes) is a {@link com.otilm.api.clients.ClientTuning#maxInMemorySize()} setting on
 * that shared {@code WebClient}, not something this class owns: every client sharing the WebClient
 * shares the same bound. A per-client override was considered and rejected — {@link BaseApiClient}
 * caches CERTIFICATE-auth WebClients process-wide keyed only by connector UUID, on the documented
 * assumption that all clients share one base WebClient; a connector can implement more than one
 * provider interface (e.g. both authority and discovery) under a single UUID, so a per-client cap
 * could silently land on — or silently vanish from — a different client's connector depending on
 * cache-population order. A response that exceeds the shared bound fails the call; see
 * {@link BaseApiClient#processRequest} for how that failure is mapped to
 * {@link com.otilm.api.exception.ConnectorServerException}.
 *
 * <p><b>Why the list operations decode arrays rather than streaming element lists.</b> That bound
 * only covers a whole response on the {@code toEntity} path, which joins the response into one
 * buffer before decoding it. {@code toEntityList(X.class)} instead streams the array through
 * Spring's {@code Jackson2Tokenizer}, and the tokenizer resets its byte counter every time a
 * top-level element completes, so the cap degrades into a per-element cap and the {@code collectList}
 * that assembles the result is unbounded — a connector returning a million tiny attribute objects
 * would not be bounded at all. The three list operations therefore request an array type
 * ({@code X[].class}) and wrap the result, which routes through the codec's bounded
 * {@code decodeToMono} path and holds the documented "single response" meaning of the cap. The
 * wrapper returns a mutable {@link ArrayList}, matching the MQ client, so a caller that sorts or
 * filters the result in place behaves the same on both transports.
 *
 * <p><b>No per-operation timeout budget over REST.</b> The MQ client sizes every call with
 * {@link com.otilm.api.clients.mq.discovery.v2.DiscoveryMqTimeouts} — a short control/status budget,
 * a deliberately long drain budget. This client has no per-request timeout at all: every call,
 * including a {@code results} drain that legitimately runs far longer than a status poll, is capped
 * by the single global {@link com.otilm.api.clients.ClientTuning#responseTimeout()} read guard on the
 * shared {@code WebClient} (35s by default). Raising the effective drain budget therefore means
 * raising that shared {@code responseTimeout}, which lengthens the read guard for every connector
 * client built on the same WebClient, not just discovery. Documenting the asymmetry is the chosen
 * resolution — a per-request override is deliberately not implemented, for the same shared-WebClient
 * reason the response-size bound above is not per-client.
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
     * A 404 on cancel means the connector no longer tracks the run — Core treats that as an
     * already-terminal cancellation, i.e. success, not a failure. That is surfaced through the
     * returned {@code ResponseEntity}'s status rather than as a thrown exception (the reason this
     * method returns {@code ResponseEntity<Void>} instead of {@code void}), so a mapped "not
     * tracked" failure is caught here and turned back into a normal response. That mapping can
     * arrive as either of two shapes depending on how the connector answers the 404: a
     * {@link ConnectorProblemException} carrying a not-tracked {@code errorCode} for a conformant
     * {@code application/problem+json} body (checked by error code through
     * {@link ConnectorOperationErrorCodes#isOperationNotTracked}, not by transport status, since the
     * error code is the one thing both this and every other lifecycle call's 404 agree on), or a
     * {@link ConnectorEntityNotFoundException} for a terse/non-problem-json 404 —
     * {@code BaseApiClient}'s legacy fallback for exactly that case. Every other failure (422
     * refusal, transport errors, ...) still flows through the standard
     * {@link BaseApiClient#processRequest} mapping unchanged.
     *
     * <p>The terse shape is genuinely ambiguous, which is why the lenient fallback is kept but logged.
     * The shared connector contract also documents 404 as "endpoint not found or not implemented"
     * (see {@code AuthProtectedConnectorController}), and Spring's own unmapped-route error page is a
     * bare 404 too, so a connector that never implemented {@code /discoveries/cancel} — or one reached
     * through a stale base URL — is indistinguishable at this point from a run that really is gone.
     * Swallowing it silently would report a failed abort to Core as a successful one, so every
     * swallowed 404 that did not carry a conformant not-tracked error code is logged at WARN naming
     * the connector and the path.
     *
     * <p><b>Divergence from the v3 certificate family, deliberate.</b>
     * {@link com.otilm.api.clients.v3.CertificateApiClient} propagates a 404 from its
     * {@code cancelIssue}/{@code cancelRevoke}/{@code cancelRegister} calls as a
     * {@link ConnectorProblemException} instead of swallowing it. Core consumes both clients, so two
     * opposite cancel contracts in one library need justifying rather than merely tolerating:
     * cancelling a discovery run is idempotent-terminal — a run the connector no longer tracks is
     * already in the state cancel asked for, so there is nothing left to stop and nothing for Core to
     * reconcile. A certificate issue/revoke/register cancellation is not: there the 404 only says the
     * operation handle is unknown, which does not mean the operation stopped, and Core may still have
     * an issued certificate to reconcile. Reporting that as a successful abort would lose work, so
     * the certificate client must keep propagating it.
     */
    @Override
    public ResponseEntity<Void> cancel(ApiClientConnectorInfo connector, DiscoveryRunRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);
        String cancelUrl = connector.getUrl() + DiscoveryPaths.CANCEL;

        return processRequest(r -> {
            // The 404 swallow is attached to the exchange stage alone rather than to the assembled
            // chain, so only a not-found raised by this request's own response can be intercepted.
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
     * <p>{@link Exceptions#unwrap} runs first because Reactor wraps propagated errors and
     * {@link BaseApiClient#processRequest} unwraps for exactly that reason. The filter that maps
     * connector responses happens to emit unwrapped into {@code onErrorResume} today, so matching the
     * raw throwable works by accident; any future wrapping would silently turn a swallowed 404 back
     * into a hard failure.
     *
     * <p>The conformant shape is recognised by error code via
     * {@link ConnectorOperationErrorCodes#isOperationNotTracked}, the single definition of that rule.
     * The terse shape is accepted too, but logged — see this class's {@code cancel} javadoc for why a
     * bare 404 is ambiguous and why it is nonetheless not fatal.
     */
    private static boolean isRunNotTracked(Throwable error, ApiClientConnectorInfo connector, String cancelUrl) {
        Throwable unwrapped = Exceptions.unwrap(error);
        if (unwrapped instanceof ConnectorProblemException cpe) {
            return ConnectorOperationErrorCodes.isOperationNotTracked(cpe.getProblemDetail().getErrorCode());
        }
        if (unwrapped instanceof ConnectorEntityNotFoundException) {
            logger.warn("Connector {} answered cancel at {} with a 404 that carries no not-tracked error code;"
                            + " treating the run as already terminal. An unimplemented cancel endpoint or a stale"
                            + " connector URL produces the same 404, so confirm the endpoint exists before trusting"
                            + " this cancellation.",
                    connector.getName(), cancelUrl);
            return true;
        }
        return false;
    }

    /**
     * Wrap a decoded response array as the mutable list the interface promises.
     *
     * <p>Identical to the MQ client's helper on purpose: a fresh {@link ArrayList} rather than an
     * {@link Arrays#asList} view, and an absent array read as "no items", so a caller that sorts or
     * filters the result in place cannot break on one transport only.
     */
    private static <T> List<T> toMutableList(T[] result) {
        return result == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(result));
    }
}
