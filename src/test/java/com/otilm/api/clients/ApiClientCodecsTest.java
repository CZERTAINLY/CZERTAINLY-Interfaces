package com.otilm.api.clients;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.codec.Decoder;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.DecoderHttpMessageReader;
import org.springframework.http.codec.EncoderHttpMessageWriter;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

/**
 * Pins the codec contract every outbound client depends on. The check cannot fail on the Spring Framework 6 line, where
 * Jackson 2 is what {@code defaultCodecs()} picks anyway; it exists to fail on Framework 7, where the default silently
 * becomes Jackson 3 and our DTO serializer annotations stop being seen.
 */
class ApiClientCodecsTest {

    @Test
    void pinsTheJsonDecoderToJackson2() {
        ClientCodecConfigurer codecs = ClientCodecConfigurer.create();

        ApiClientCodecs.pinToJackson2(codecs);

        boolean pinned = codecs
                .getReaders()
                .stream()
                .filter(DecoderHttpMessageReader.class::isInstance)
                .map(reader -> ((DecoderHttpMessageReader<?>) reader).getDecoder())
                .anyMatch(Jackson2JsonDecoder.class::isInstance);

        Assertions.assertTrue(pinned, "no Jackson 2 JSON decoder is registered: " + decoderTypes(codecs));
    }

    @Test
    void pinsTheJsonEncoderToJackson2() {
        ClientCodecConfigurer codecs = ClientCodecConfigurer.create();

        ApiClientCodecs.pinToJackson2(codecs);

        boolean pinned = codecs
                .getWriters()
                .stream()
                .filter(EncoderHttpMessageWriter.class::isInstance)
                .map(writer -> ((EncoderHttpMessageWriter<?>) writer).getEncoder())
                .anyMatch(Jackson2JsonEncoder.class::isInstance);

        Assertions.assertTrue(pinned, "no Jackson 2 JSON encoder is registered");
    }

    private static String decoderTypes(final ClientCodecConfigurer codecs) {
        return codecs
                .getReaders()
                .stream()
                .filter(DecoderHttpMessageReader.class::isInstance)
                .map(reader -> ((DecoderHttpMessageReader<?>) reader).getDecoder())
                .map(Decoder::getClass)
                .map(Class::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("none");
    }
}
