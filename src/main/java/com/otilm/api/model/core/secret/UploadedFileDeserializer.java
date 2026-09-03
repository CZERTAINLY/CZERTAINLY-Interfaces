package com.otilm.api.model.core.secret;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Arrays;

/**
 * Reads a base64 JSON string into an {@link UploadedFile}, decoding the token directly to bytes.
 *
 * <p>
 * A value of another JSON type, or one that is not base64, is refused with a fixed message rather than one that quotes
 * what was sent, so a mistyped request cannot echo key material back through an error response.
 * </p>
 */
public class UploadedFileDeserializer extends JsonDeserializer<UploadedFile> {

    private static final String WRONG_FORMAT = "file must be a base64-encoded string";

    @Override
    public UploadedFile deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (parser.currentToken() != JsonToken.VALUE_STRING) {
            throw new UploadedFileFormatException(WRONG_FORMAT);
        }
        byte[] content;
        try {
            content = parser.getBinaryValue();
        } catch (JsonProcessingException | IllegalArgumentException e) {
            throw new UploadedFileFormatException(WRONG_FORMAT);
        }
        UploadedFile file = new UploadedFile(content);
        Arrays.fill(content, (byte) 0);
        return file;
    }

    @Override
    public UploadedFile getNullValue(DeserializationContext context) {
        return null;
    }
}
