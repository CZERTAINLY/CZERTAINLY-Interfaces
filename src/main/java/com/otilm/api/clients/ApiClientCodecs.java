package com.otilm.api.clients;

import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

/**
 * The JSON codecs every outbound {@code WebClient} in the platform is built with.
 */
public final class ApiClientCodecs {

    private ApiClientCodecs() {
    }

    /**
     * Binds the client to the Jackson 2 JSON codecs, which our DTO serializer annotations require.
     *
     * <p>
     * {@code defaultCodecs()} resolves JSON from the classpath and prefers Jackson 3 from Spring Framework 7 onwards,
     * so the consumer's classpath would otherwise decide the wire format.
     *
     * @param codecs the configurer handed to {@code WebClient.Builder.codecs} or {@code ExchangeStrategies.builder}
     */
    public static void pinToJackson2(final ClientCodecConfigurer codecs) {
        codecs.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder());
        codecs.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder());
    }
}
