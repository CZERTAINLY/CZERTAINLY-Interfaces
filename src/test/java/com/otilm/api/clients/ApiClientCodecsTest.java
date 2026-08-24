package com.otilm.api.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.codec.Decoder;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.DecoderHttpMessageReader;
import org.springframework.http.codec.EncoderHttpMessageWriter;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

/**
 * Tests for Jackson 2 codec contract, which Spring Framework 7 would otherwise resolve to Jackson 3.
 */
class ApiClientCodecsTest {

    @Test
    void overridesAJsonDecoderThatWasAlreadyConfigured() {
        ClientCodecConfigurer codecs = ClientCodecConfigurer.create();
        ObjectMapper foreign = new ObjectMapper();
        codecs.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(foreign));

        ApiClientCodecs.configureJsonCodecs(codecs);

        Assertions
                .assertSame(ApiClientCodecs.objectMapper(), jsonDecoder(codecs.getReaders()).getObjectMapper(),
                        "the seam must install its own Jackson 2 decoder");
    }

    @Test
    void overridesAJsonEncoderThatWasAlreadyConfigured() {
        ClientCodecConfigurer codecs = ClientCodecConfigurer.create();
        ObjectMapper foreign = new ObjectMapper();
        codecs.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(foreign));

        ApiClientCodecs.configureJsonCodecs(codecs);

        Assertions
                .assertSame(ApiClientCodecs.objectMapper(), jsonEncoder(codecs.getWriters()).getObjectMapper(),
                        "the seam must install its own Jackson 2 encoder");
    }

    @Test
    void buildsTheCodecsOnACallerSuppliedMapper() {
        ClientCodecConfigurer codecs = ClientCodecConfigurer.create();
        ObjectMapper caller = new ObjectMapper();

        ApiClientCodecs.configureJsonCodecs(codecs, caller);

        Assertions.assertSame(caller, jsonDecoder(codecs.getReaders()).getObjectMapper(), "decoder mapper");
        Assertions.assertSame(caller, jsonEncoder(codecs.getWriters()).getObjectMapper(), "encoder mapper");
    }

    @Test
    void connectorClientsPinJacksonAndCarryTheTunedReadCap() {
        int readCap = 7 * 1024 * 1024;
        ClientTuning tuning = new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(35), 20,
                Duration.ofSeconds(10), readCap);

        Jackson2JsonDecoder decoder = jsonDecoder(BaseApiClient.connectorExchangeStrategies(tuning).messageReaders());

        Assertions
                .assertSame(ApiClientCodecs.objectMapper(), decoder.getObjectMapper(),
                        "connector clients must decode through the pinned Jackson 2 codec");
        Assertions
                .assertEquals(readCap, decoder.getMaxInMemorySize(),
                        "the tuned read cap must reach the pinned decoder");
    }

    @Test
    void platformClientsPinJacksonAndShareTheConnectorReadCap() {
        Jackson2JsonDecoder decoder = jsonDecoder(PlatformBaseApiClient.exchangeStrategies().messageReaders());

        Assertions
                .assertSame(ApiClientCodecs.objectMapper(), decoder.getObjectMapper(),
                        "platform clients must decode through the pinned Jackson 2 codec");
        Assertions
                .assertEquals(ClientTuning.DEFAULT_MAX_IN_MEMORY, decoder.getMaxInMemorySize(),
                        "platform clients share the connector read cap");
    }

    private static Jackson2JsonDecoder jsonDecoder(final List<HttpMessageReader<?>> readers) {
        return readers
                .stream()
                .filter(DecoderHttpMessageReader.class::isInstance)
                .map(reader -> ((DecoderHttpMessageReader<?>) reader).getDecoder())
                .filter(Jackson2JsonDecoder.class::isInstance)
                .map(Jackson2JsonDecoder.class::cast)
                .findFirst()
                .orElseThrow(
                        () -> new AssertionError("no Jackson 2 JSON decoder is registered: " + decoderTypes(readers)));
    }

    private static Jackson2JsonEncoder jsonEncoder(final List<HttpMessageWriter<?>> writers) {
        return writers
                .stream()
                .filter(EncoderHttpMessageWriter.class::isInstance)
                .map(writer -> ((EncoderHttpMessageWriter<?>) writer).getEncoder())
                .filter(Jackson2JsonEncoder.class::isInstance)
                .map(Jackson2JsonEncoder.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("no Jackson 2 JSON encoder is registered"));
    }

    private static String decoderTypes(final List<HttpMessageReader<?>> readers) {
        return readers
                .stream()
                .filter(DecoderHttpMessageReader.class::isInstance)
                .map(reader -> ((DecoderHttpMessageReader<?>) reader).getDecoder())
                .map(Decoder::getClass)
                .map(Class::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("none");
    }
}
