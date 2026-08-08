package com.otilm.api.clients.mq.discovery.v2;

import java.time.Duration;
import java.util.Objects;

/**
 * Per-operation MQ timeouts for {@link DiscoveryApiClient}, sized independently because a drain can
 * legitimately run far longer than a status poll or a lifecycle control call.
 *
 * <ul>
 *   <li>{@link #status()} — the {@code status} poll.</li>
 *   <li>{@link #drain()} — the {@code results} drain; raised where connectors return large batches.</li>
 *   <li>{@link #control()} — {@code initiate}, {@code stop}, {@code resume}, {@code cancel} and the
 *       three metadata reads.</li>
 * </ul>
 *
 * <p>Components must be positive, rejected at construction rather than surfacing as an immediate
 * timeout on the first connector call — the rule {@link com.otilm.api.clients.ClientTuning} applies to
 * its own durations.
 */
public record DiscoveryMqTimeouts(Duration status, Duration drain, Duration control) {

    public DiscoveryMqTimeouts {
        requirePositive(status, "status");
        requirePositive(drain, "drain");
        requirePositive(control, "control");
    }

    /**
     * A missing component stays a {@link NullPointerException} — a wiring bug, not a value out of
     * range; a zero or negative one is an {@link IllegalArgumentException} carrying the message shape
     * {@link com.otilm.api.clients.ClientTuning} uses.
     */
    private static void requirePositive(Duration value, String name) {
        Objects.requireNonNull(value, name + " is required");
        if (value.isNegative() || value.isZero()) {
            throw new IllegalArgumentException(name + " must be a positive duration, was " + value);
        }
    }

    /** 30 seconds for every component. */
    public static DiscoveryMqTimeouts defaults() {
        Duration thirtySeconds = Duration.ofSeconds(30);
        return new DiscoveryMqTimeouts(thirtySeconds, thirtySeconds, thirtySeconds);
    }
}
