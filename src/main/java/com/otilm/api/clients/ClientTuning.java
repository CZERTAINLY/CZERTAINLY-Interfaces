package com.otilm.api.clients;

import java.time.Duration;

/**
 * Tuning for the shared connector {@link org.springframework.web.reactive.function.client.WebClient}.
 *
 * <p>
 * Only the load-bearing knobs are exposed here; connection-pool hygiene (idle eviction, lifetime, background disposal,
 * LIFO leasing) is fixed inside {@link BaseApiClient} because it is not deployment-specific. Deployment code supplies
 * these values from configuration; {@link #defaults()} backs tests and any caller that does not tune.
 *
 * <p>
 * {@code maxInMemorySize} is not yet settable by any deployment surface, tracked in core#1961.
 *
 * <p>
 * {@code maxConnections} is <em>per remote host</em> (Reactor-Netty pools per destination), not a global cap, so it is
 * sized against the per-connector concurrent load rather than a single queue's listener concurrency.
 *
 * <p>
 * {@code maxInMemorySize} bounds how many bytes of a single response {@link BaseApiClient} will buffer while decoding
 * it (Spring's codec {@code maxInMemorySize}). It is shared by every client built on the WebClient this tuning
 * produces, so one setting bounds all of them. A response exceeding it fails the call rather than buffering without
 * limit; {@link BaseApiClient#processRequest} maps that failure to
 * {@link com.otilm.api.exception.ConnectorServerException}.
 */
public record ClientTuning(Duration connectTimeout, Duration responseTimeout, int maxConnections,
        Duration pendingAcquireTimeout, int maxInMemorySize) {

    /**
     * 16 MiB: generous for attribute/status/CRL payloads, bounded against an oversized or malicious connector response.
     */
    static final int DEFAULT_MAX_IN_MEMORY = 16 * 1024 * 1024;

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
     * Source-compatible overload for callers compiled against this record before {@code maxInMemorySize} was added;
     * applies {@link #DEFAULT_MAX_IN_MEMORY}.
     */
    public ClientTuning(Duration connectTimeout, Duration responseTimeout, int maxConnections,
            Duration pendingAcquireTimeout) {
        this(connectTimeout, responseTimeout, maxConnections, pendingAcquireTimeout, DEFAULT_MAX_IN_MEMORY);
    }

    private static void requirePositive(Duration value, String name) {
        if (value == null || value.isNegative() || value.isZero()) {
            throw new IllegalArgumentException(name + " must be a positive duration, was " + value);
        }
    }

    public static ClientTuning defaults() {
        // 35s response ~ authority connector per-call budget (30s) + margin; 3s connect fails fast on unreachable
        // hosts.
        return new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(35), 20, Duration.ofSeconds(10),
                DEFAULT_MAX_IN_MEMORY);
    }
}
