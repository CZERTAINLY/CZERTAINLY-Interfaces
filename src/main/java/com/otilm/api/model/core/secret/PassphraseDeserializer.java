package com.otilm.api.model.core.secret;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Arrays;

/**
 * Reads a JSON string into a {@link Passphrase} without going through {@link String}.
 *
 * <p>
 * A value of another JSON type is refused with a fixed message rather than one that quotes what was sent, so a mistyped
 * request cannot echo a secret back through an error response.
 * </p>
 */
public class PassphraseDeserializer extends JsonDeserializer<Passphrase> {

    private static final String WRONG_TYPE = "passphrase must be a string";

    @Override
    public Passphrase deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (parser.currentToken() != JsonToken.VALUE_STRING) {
            throw new PassphraseFormatException(WRONG_TYPE);
        }

        char[] characters = new char[parser.getTextLength()];
        System.arraycopy(parser.getTextCharacters(), parser.getTextOffset(), characters, 0, parser.getTextLength());
        Passphrase passphrase = new Passphrase(characters);
        Arrays.fill(characters, '\0');
        return passphrase;
    }

    @Override
    public Passphrase getNullValue(DeserializationContext context) {
        return null;
    }
}
