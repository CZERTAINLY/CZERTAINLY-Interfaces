package com.otilm.api.clients;

import java.time.Duration;

/**
 * Tuning for the shared connector {@link org.springframework.web.reactive.function.client.WebClient}.
 *
 * <p>Only the load-bearing knobs are exposed here; connection-pool hygiene (idle eviction,
 * lifetime, background disposal, LIFO leasing) is fixed inside {@link BaseApiClient} because it is
 * not deployment-specific. Deployment code supplies these values from configuration;
 * {@link #defaults()} backs tests and any caller that does not tune.
 *
 * <p><b>Which of these a deployment can actually set, as of this writing.</b> The four timeout/pool
 * components only. Core binds {@code connector.api-client.*} into its
 * {@code ConnectorApiClientProperties} record — {@code connectTimeout}, {@code responseTimeout},
 * {@code maxConnections}, {@code pendingAcquireTimeout}, nothing else — and its
 * {@code ApplicationConfig.webClient} bean passes exactly those to the four-argument constructor
 * below. There is no {@code connector.api-client.max-in-memory-size} property, no
 * {@code CONNECTOR_API_CLIENT_MAX_IN_MEMORY_SIZE} environment variable, and no helm value for it, so
 * <em>{@code maxInMemorySize} is not settable by any deployment surface</em>: every deployment runs
 * on {@link #DEFAULT_MAX_IN_MEMORY} whatever its configuration says. The sentence above about
 * deployment code supplying these values from configuration therefore does not hold for this one
 * component. Exposing it means adding the property, the env var and the helm value together with a
 * fifth argument at that call site.
 *
 * <p><b>The "keep the two in sync" claim on core's side is currently false.</b>
 * {@code ConnectorApiClientProperties}' javadoc states that its {@code application.yml} defaults
 * mirror {@link #defaults()} and that the two must be kept in sync. They cannot mirror each other any
 * more: this record has five components and that one has four, so the mirror only covers the four
 * they share, and {@code maxInMemorySize} has no counterpart to stay in sync with. Read the claim as
 * applying to the four timeout/pool values alone until core's record grows the fifth.
 *
 * <p>{@code maxConnections} is <em>per remote host</em> (Reactor-Netty pools per destination), not a
 * global cap, so it is sized against the per-connector concurrent load rather than a single queue's
 * listener concurrency.
 *
 * <p>{@code maxInMemorySize} bounds how many bytes of a single response {@link BaseApiClient} will
 * buffer while decoding it (Spring's codec {@code maxInMemorySize}) — shared by every client built
 * on the WebClient this tuning produces, so one setting protects all of them against an oversized or
 * malicious connector response (a connector like discovery's {@code results}/drain page is the
 * motivating case, but the bound is not endpoint-specific). A response exceeding it fails the call
 * rather than buffering without limit; {@link BaseApiClient#processRequest} maps that failure to
 * {@link com.otilm.api.exception.ConnectorServerException}.
 *
 * <p>The four-argument constructor below is a source-compatible overload for callers built against
 * the version of this record before {@code maxInMemorySize} existed — it applies
 * {@link #DEFAULT_MAX_IN_MEMORY}. A shared-library addition to a public record should not force a
 * simultaneous edit in every consuming repository; callers who care about the response bound
 * should construct with the canonical five-argument constructor (or {@link #defaults()}) instead.
 */
public record ClientTuning(
        Duration connectTimeout,
        Duration responseTimeout,
        int maxConnections,
        Duration pendingAcquireTimeout,
        int maxInMemorySize) {

    /** 16 MiB: generous for attribute/status/CRL payloads, bounded against an oversized or malicious
     * connector response. The single source of the default in-memory response cap — used by both
     * {@link #defaults()} and the four-argument convenience constructor. */
    private static final int DEFAULT_MAX_IN_MEMORY = 16 * 1024 * 1024;

    public ClientTuning {
        requirePositive(connectTimeout, "connectTimeout");
        requirePositive(responseTimeout, "responseTimeout");
        requirePositive(pendingAcquireTimeout, "pendingAcquireTimeout");
        if (maxConnections <= 0) {
            throw new IllegalArgumentException("maxConnections must be positive, was " + maxConnections);
        }
        if (maxInMemorySize <= 0) {
            throw new IllegalArgumentException("maxInMemorySize must be positive, was " + maxInMemorySize);
        }
    }

    /**
     * Source-compatible overload for callers compiled against this record before
     * {@code maxInMemorySize} was added — applies {@link #DEFAULT_MAX_IN_MEMORY}. Do not remove:
     * a shared-library record gaining a component must not force every consuming repository to
     * change its constructor call in lockstep with the library bump.
     */
    public ClientTuning(Duration connectTimeout, Duration responseTimeout, int maxConnections, Duration pendingAcquireTimeout) {
        this(connectTimeout, responseTimeout, maxConnections, pendingAcquireTimeout, DEFAULT_MAX_IN_MEMORY);
    }

    private static void requirePositive(Duration value, String name) {
        if (value == null || value.isNegative() || value.isZero()) {
            throw new IllegalArgumentException(name + " must be a positive duration, was " + value);
        }
    }

    public static ClientTuning defaults() {
        // 35s response ~ authority connector per-call budget (30s) + margin; 3s connect fails fast on unreachable hosts.
        return new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(35), 20, Duration.ofSeconds(10), DEFAULT_MAX_IN_MEMORY);
    }
}
