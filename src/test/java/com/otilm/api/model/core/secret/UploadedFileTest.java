package com.otilm.api.model.core.secret;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UploadedFileTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final byte[] CONTENT = "-----BEGIN PRIVATE KEY-----".getBytes(StandardCharsets.US_ASCII);

    private static final String ENCODED = Base64.getEncoder().encodeToString(CONTENT);

    @Test
    void readsABase64StringIntoBytes() throws Exception {
        // given
        String json = "\"" + ENCODED + "\"";

        // when
        UploadedFile file = MAPPER.readValue(json, UploadedFile.class);

        // then
        assertArrayEquals(CONTENT, file.content());
        assertEquals(CONTENT.length, file.length());
    }

    @Test
    void readsAnAbsentValueAsNull() throws Exception {
        // given
        String json = "{\"file\": null}";

        // when
        Holder holder = MAPPER.readValue(json, Holder.class);

        // then
        assertNull(holder.file);
    }

    @Test
    void isNeverWrittenBack() throws Exception {
        // given
        Holder holder = new Holder();
        holder.file = new UploadedFile(CONTENT);

        // when
        String json = MAPPER.writeValueAsString(holder);

        // then
        assertFalse(json.contains(ENCODED), () -> "file content leaked into " + json);
    }

    /**
     * A file in the wrong place must not be echoed back: Jackson's own message for a type mismatch quotes the offending
     * value, which for this field could put key material into an error response.
     */
    @Test
    void refusesAValueOfAnotherTypeWithoutQuotingIt() {
        // given
        String json = "{\"file\": [\"" + ENCODED + "\"]}";

        // when
        Exception failure = assertThrows(Exception.class, () -> MAPPER.readValue(json, Holder.class));

        // then
        assertFalse(rendered(failure).contains(ENCODED), () -> "file content leaked into " + rendered(failure));
        assertTrue(rendered(failure).contains("file must be a base64-encoded string"));
    }

    @Test
    void refusesContentThatIsNotBase64WithoutQuotingIt() {
        // given
        String json = "{\"file\": \"not base64 at all!\"}";

        // when
        Exception failure = assertThrows(Exception.class, () -> MAPPER.readValue(json, Holder.class));

        // then
        assertFalse(rendered(failure).contains("not base64 at all"),
                () -> "the value leaked into " + rendered(failure));
        assertTrue(rendered(failure).contains("file must be a base64-encoded string"));
    }

    @Test
    void toStringCarriesNoContent() {
        // given
        UploadedFile file = new UploadedFile(CONTENT);

        // when
        String rendered = file.toString();

        // then
        assertFalse(rendered.contains(ENCODED), () -> "file content leaked into " + rendered);
        assertFalse(rendered.contains("PRIVATE KEY"), () -> "file content leaked into " + rendered);
        assertTrue(rendered.contains("UploadedFile"));
    }

    @Test
    void returnsACopyAndCanBeCleared() {
        // given
        UploadedFile file = new UploadedFile(CONTENT);
        byte[] first = file.content();

        // when
        first[0] = 0;
        byte[] second = file.content();
        file.clear();

        // then
        assertArrayEquals(CONTENT, second, "a caller overwriting its copy must not reach the held content");
        assertEquals(0, file.length());
        assertEquals(0, file.content().length);
    }

    private static String rendered(Throwable failure) {
        StringBuilder rendered = new StringBuilder();
        for (Throwable t = failure; t != null; t = t.getCause()) {
            rendered.append(t.getClass().getName()).append(':').append(t.getMessage()).append('\n');
        }
        return rendered.toString();
    }

    static class Holder {
        public UploadedFile file;
    }
}
