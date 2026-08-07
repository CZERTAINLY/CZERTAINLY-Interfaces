package com.otilm.api.clients;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class ClientTuningTest {

    /**
     * Regression guard for the four-argument, source-compatible constructor kept for callers built
     * against this record before {@code maxInMemorySize} existed (e.g. Core's
     * {@code ApplicationConfig}). If this overload is ever "tidied away" or its delegation stops
     * applying the 16 MiB default, this fails — a compile error alone would not catch a wrong
     * default silently taking its place.
     */
    @Test
    void fourArgConstructor_usesDefaultMaxInMemorySize() {
        ClientTuning tuning = new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(35), 20, Duration.ofSeconds(10));

        Assertions.assertEquals(16 * 1024 * 1024, tuning.maxInMemorySize());
    }

    /**
     * The canonical constructor's {@code maxInMemorySize} guard, which nothing else covered — the
     * four-argument overload can never reach it, since it always supplies the positive default. A
     * non-positive cap is not a harmless value to wave through: Spring's codec treats it as the byte
     * limit, so it would fail every response rather than none, and the failure would surface as an
     * oversized-response error on every single connector call sharing the WebClient. Without this
     * case the guard could be deleted outright with the suite still green.
     */
    @Test
    void canonicalConstructor_rejectsNonPositiveMaxInMemorySize() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(35), 20, Duration.ofSeconds(10), 0));

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(35), 20, Duration.ofSeconds(10), -1));
    }
}
