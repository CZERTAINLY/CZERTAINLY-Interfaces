package com.otilm.api.model.core.secret;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PassphraseTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String VALUE = "correct horse battery staple";

    @Test
    void readsAJsonStringIntoCharacters() throws Exception {
        // given
        String json = "\"" + VALUE + "\"";

        // when
        Passphrase passphrase = MAPPER.readValue(json, Passphrase.class);

        // then
        assertArrayEquals(VALUE.toCharArray(), passphrase.characters());
        assertEquals(VALUE.codePointCount(0, VALUE.length()), passphrase.codePointLength());
    }

    @Test
    void readsAnAbsentValueAsNull() throws Exception {
        // given
        String json = "{\"passphrase\": null}";

        // when
        Holder holder = MAPPER.readValue(json, Holder.class);

        // then
        assertNull(holder.passphrase);
    }

    @Test
    void isNeverWrittenBack() throws Exception {
        // given
        Holder holder = new Holder();
        holder.passphrase = new Passphrase(VALUE.toCharArray());

        // when
        String json = MAPPER.writeValueAsString(holder);

        // then
        assertFalse(json.contains(VALUE), () -> "passphrase leaked into " + json);
        assertFalse(json.contains("blank") || json.contains("normalized"),
                () -> "the passphrase checks must not be published as properties, got " + json);
    }

    /**
     * A passphrase in the wrong place must not be echoed back. Jackson's own message for a type mismatch quotes the
     * offending value, which for this field would put a secret into an error response.
     */
    @Test
    void refusesAValueOfAnotherTypeWithoutQuotingIt() {
        // given
        String json = "{\"passphrase\": " + "[\"" + VALUE + "\"]}";

        // when
        Exception failure = assertThrows(Exception.class, () -> MAPPER.readValue(json, Holder.class));

        // then
        assertFalse(rendered(failure).contains(VALUE), () -> "passphrase leaked into " + rendered(failure));
        assertTrue(rendered(failure).contains("passphrase must be a string"));
    }

    @Test
    void refusesANumberWithoutQuotingIt() {
        // given
        String json = "{\"passphrase\": 1234567890123}";

        // when
        Exception failure = assertThrows(Exception.class, () -> MAPPER.readValue(json, Holder.class));

        // then
        assertFalse(rendered(failure).contains("1234567890123"),
                () -> "the supplied value leaked into " + rendered(failure));
    }

    private static String rendered(Throwable failure) {
        StringBuilder rendered = new StringBuilder();
        for (Throwable t = failure; t != null; t = t.getCause()) {
            rendered.append(t.getClass().getName()).append(':').append(t.getMessage()).append('\n');
        }
        return rendered.toString();
    }

    @Test
    void toStringRedactsTheValue() {
        // given
        Passphrase passphrase = new Passphrase(VALUE.toCharArray());

        // when
        String rendered = passphrase.toString();

        // then
        assertFalse(rendered.contains(VALUE), () -> "passphrase leaked into " + rendered);
        assertTrue(rendered.contains("Passphrase"));
    }

    @Test
    void charactersAreCopiedInAndOut() {
        // given
        char[] source = VALUE.toCharArray();
        Passphrase passphrase = new Passphrase(source);

        // when
        source[0] = 'X';
        char[] read = passphrase.characters();
        read[1] = 'Y';

        // then
        assertArrayEquals(VALUE.toCharArray(), passphrase.characters());
        assertNotSame(read, passphrase.characters());
    }

    @Test
    void clearingOverwritesTheCharacters() {
        // given
        Passphrase passphrase = new Passphrase(VALUE.toCharArray());

        // when
        passphrase.clear();

        // then
        assertEquals(0, passphrase.codePointLength());
        assertArrayEquals(new char[0], passphrase.characters());
    }

    @Test
    void rejectsAbsentCharacters() {
        // given
        // when
        NullPointerException failure = assertThrows(NullPointerException.class, () -> new Passphrase(null));

        // then
        assertEquals("characters are required", failure.getMessage());
    }

    @Test
    void equalityComparesTheCharactersInConstantTime() {
        // given
        Passphrase first = new Passphrase(VALUE.toCharArray());
        Passphrase same = new Passphrase(VALUE.toCharArray());
        Passphrase other = new Passphrase("another value".toCharArray());

        // when
        // then
        assertEquals(first, same);
        assertEquals(first.hashCode(), same.hashCode());
        assertNotEquals(first, other);
    }

    @Test
    void countsCodePointsRatherThanCharacters() {
        // given
        String sixEmoji = "😀😃😄😁😆😅";

        // when
        Passphrase passphrase = new Passphrase(sixEmoji.toCharArray());

        // then
        assertEquals(12, sixEmoji.length(), "the fixture must occupy twelve characters to be worth testing");
        assertEquals(6, passphrase.codePointLength());
    }

    /**
     * The published {@code minLength} counts the value as sent, and normalizing first would copy the passphrase into a
     * value {@link Passphrase#clear()} cannot overwrite. A decomposed value therefore counts every code point it
     * carries; where the count matters, {@link Passphrase#isNormalized()} is what refuses such a value.
     */
    @Test
    void countsTheValueAsSentRatherThanNormalized() {
        // given
        String decomposed = "e\u0301e\u0301e\u0301e\u0301e\u0301e\u0301";

        // when
        Passphrase passphrase = new Passphrase(decomposed.toCharArray());

        // then
        assertEquals(12, decomposed.length(), "the fixture must occupy twelve characters to be worth testing");
        assertEquals(12, passphrase.codePointLength());
    }

    @Test
    void tellsNormalizationFormCFromAnyOtherForm() {
        // given
        Passphrase composed = new Passphrase("caf\u00e9 au lait".toCharArray());
        Passphrase decomposed = new Passphrase("cafe\u0301 au lait".toCharArray());

        // when
        // then
        assertTrue(composed.isNormalized());
        assertFalse(decomposed.isNormalized());
    }

    @Test
    void isEmptyWhenItCarriesOnlyWhitespace() {
        // given
        // when
        // then
        assertTrue(new Passphrase("   ".toCharArray()).isBlank());
        assertTrue(new Passphrase(new char[0]).isBlank());
        assertFalse(new Passphrase(VALUE.toCharArray()).isBlank());
    }

    @Test
    void doesNotRetainTheStringItWasReadFrom() throws Exception {
        // given
        String json = "\"" + VALUE + "\"";

        // when
        Passphrase passphrase = MAPPER.readValue(json, Passphrase.class);

        // then
        assertFalse(Arrays.toString(passphrase.characters()).isEmpty());
        assertFalse(passphrase.toString().contains(VALUE));
    }

    private static final class Holder {

        private Passphrase passphrase;

        @SuppressWarnings("unused")
        public Passphrase getPassphrase() {
            return passphrase;
        }

        @SuppressWarnings("unused")
        public void setPassphrase(Passphrase passphrase) {
            this.passphrase = passphrase;
        }
    }
}
