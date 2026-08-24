package com.otilm.api.clients;

import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

/**
 * The JSON codecs every outbound {@link org.springframework.web.reactive.function.client.WebClient} in the platform is
 * built with.
 *
 * <p>
 * {@code defaultCodecs()} picks its JSON codec from what is on the classpath, and from Spring Framework 7 onwards it
 * prefers Jackson 3 whenever {@code tools.jackson} is present. Jackson 3 cannot see
 * {@code com.fasterxml.jackson.databind.annotation}, which our DTOs use for their custom serializers, so a client that
 * takes the default silently deserializes those types wrongly. Which codec wins is decided by the <em>consumer's</em>
 * classpath, not by this artifact, so the choice is made explicitly here rather than left to resolution.
 *
 * <p>
 * This is also the single place the platform moves to Jackson 3 when the DTO annotation surface is migrated.
 */
public final class ApiClientCodecs {

    private ApiClientCodecs() {
    }

    /**
     * Binds the client to the Jackson 2 JSON codecs.
     *
     * @param codecs the configurer handed to {@code WebClient.Builder.codecs} or {@code ExchangeStrategies.builder}
     */
    public static void pinToJackson2(final ClientCodecConfigurer codecs) {
        codecs.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder());
        codecs.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder());
    }
}
