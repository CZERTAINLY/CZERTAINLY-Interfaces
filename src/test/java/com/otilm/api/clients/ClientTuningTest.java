package com.otilm.api.clients;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class ClientTuningTest {

    /**
     * Regression guard for the four-argument, source-compatible constructor. A compile error alone
     * would not catch a wrong default silently taking the 16 MiB one's place.
     */
    @Test
    void fourArgConstructor_usesDefaultMaxInMemorySize() {
        ClientTuning tuning = new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(35), 20, Duration.ofSeconds(10));

        Assertions.assertEquals(16 * 1024 * 1024, tuning.maxInMemorySize());
    }

    /**
     * The canonical constructor's {@code maxInMemorySize} guard; the four-argument overload can never
     * reach it, since it always supplies the positive default. A non-positive cap is not harmless:
     * Spring's codec treats it as the byte limit, so it would fail every response on every connector
     * call sharing the WebClient.
     */
    @Test
    void canonicalConstructor_rejectsNonPositiveMaxInMemorySize() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(35), 20, Duration.ofSeconds(10), 0));

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(35), 20, Duration.ofSeconds(10), -1));
    }
}
