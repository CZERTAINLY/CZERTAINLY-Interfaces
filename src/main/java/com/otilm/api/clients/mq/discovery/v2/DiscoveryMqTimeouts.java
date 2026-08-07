package com.otilm.api.clients.mq.discovery.v2;

import java.time.Duration;
import java.util.Objects;

/**
 * Per-operation MQ timeouts for {@link DiscoveryApiClient}. A discovery drain can legitimately
 * take far longer than a status poll or a lifecycle control call, so each is sized independently
 * rather than sharing one proxy-wide default.
 *
 * <ul>
 *   <li>{@link #status()} — the {@code status} poll.</li>
 *   <li>{@link #drain()} — the {@code results} drain; deployments raise this when their connectors
 *       return large batches.</li>
 *   <li>{@link #control()} — {@code initiate}, {@code stop}, {@code resume}, {@code cancel}, and the
 *       three metadata list operations.</li>
 * </ul>
 *
 * <p>Every component must be a positive duration. A zero or negative timeout is a configuration
 * error that would otherwise surface as an immediate, puzzling timeout on the first connector call,
 * so it is rejected at construction — the same rule
 * {@link com.otilm.api.clients.ClientTuning} applies to its own durations.
 */
public record DiscoveryMqTimeouts(Duration status, Duration drain, Duration control) {

    public DiscoveryMqTimeouts {
        requirePositive(status, "status");
        requirePositive(drain, "drain");
        requirePositive(control, "control");
    }

    /**
     * A missing component stays a {@link NullPointerException} (it is a wiring bug, not a value out
     * of range); a zero or negative one is an {@link IllegalArgumentException} carrying the message
     * shape {@link com.otilm.api.clients.ClientTuning} uses, so both validators read alike.
     */
    private static void requirePositive(Duration value, String name) {
        Objects.requireNonNull(value, name + " is required");
        if (value.isNegative() || value.isZero()) {
            throw new IllegalArgumentException(name + " must be a positive duration, was " + value);
        }
    }

    /**
     * 30 seconds for every component; deployments raise {@link #drain()} when their connectors
     * return large batches.
     */
    public static DiscoveryMqTimeouts defaults() {
        Duration thirtySeconds = Duration.ofSeconds(30);
        return new DiscoveryMqTimeouts(thirtySeconds, thirtySeconds, thirtySeconds);
    }
}
