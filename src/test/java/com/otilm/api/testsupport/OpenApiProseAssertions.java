package com.otilm.api.testsupport;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Shared OpenAPI-prose guard for connector doc tests: the banned Core-internal jargon list and the word-boundary
 * matcher that checks for it. Doc tests must use this class rather than declare their own list — per-test copies drift,
 * and coverage then depends on which test happens to exercise a given controller.
 */
public final class OpenApiProseAssertions {

    /**
     * Core-internal vocabulary that must never reach the published OpenAPI document — connector authors in Java, Go and
     * Python read only the generated document, never the internal design docs. Matched on word boundaries so "tick"
     * does not false-positive inside "ticket". Extend the list when a new internal term needs banning; never narrow it
     * for one controller's prose.
     *
     * <p>
     * A single word covers every phrase built around it, so only the shortest form is listed: "tick" already rejects
     * "tick engine" and "drain tick", and "ladder" already rejects "dispatch ladder". Adding the longer phrase back
     * would ban nothing new while implying this matcher is phrase-aware, which it is not.
     */
    public static final List<String> BANNED_JARGON = List
            .of("tick", "sweeper", "agenda table", "ingestor", "advisory lock", "push slice", "rung", "expander",
                    "scope chain", "fail closed", "fail-closed", "footgun", "s-1", "dependson", "ladder",
                    "envelope assembly");

    /**
     * Vocabulary of the language the platform is written in, which describes nothing to an author generating a client
     * in another language. Matched on word boundaries like {@link #BANNED_JARGON}; JSON vocabulary such as "null" and
     * "object" is not listed because it means the same thing everywhere.
     */
    public static final List<String> BANNED_LANGUAGE_TERMS = List
            .of("java", "jvm", "lombok", "jackson", "bean validation", "tostring", "hashcode", "multipartfile",
                    "responseentity", "serializable", "getter", "setter", "char[]", "byte[]", "classpath");

    private static final List<BannedTerm> BANNED_TERMS = BANNED_JARGON.stream().map(BannedTerm::of).toList();

    private static final List<BannedTerm> LANGUAGE_TERMS = BANNED_LANGUAGE_TERMS.stream().map(BannedTerm::of).toList();

    private static final Pattern JAVADOC_MARKUP = Pattern
            .compile("\\{@\\w+|^\\s*@(param|return|throws)\\b", Pattern.MULTILINE);

    /**
     * A reader of the document sees properties in whatever order a generator chose, so a reference to the field "below"
     * or "above" points at nothing. Name the property instead.
     */
    private static final Pattern SOURCE_ORDER_REFERENCE = Pattern
            .compile("\\bthe (field|property) (below|above)\\b|\\b(field|property) (below|above)\\b");

    /**
     * Every path in the document is versioned and absolute. A partial one looks like a path and resolves to nothing.
     */
    private static final Pattern UNVERSIONED_PATH = Pattern.compile("`(?:POST|GET|PATCH|PUT|DELETE) (/(?!v\\d)[^`]*)`");

    private OpenApiProseAssertions() {
    }

    /**
     * Fails if {@code text} contains any {@link #BANNED_JARGON} term, matched case-insensitively on word boundaries.
     * {@code context} identifies where the text came from, for the failure message only.
     */
    public static void assertNoJargon(String context, String text) {
        String lower = text.toLowerCase(Locale.ROOT);
        for (BannedTerm banned : BANNED_TERMS) {
            assertFalse(banned.pattern().matcher(lower).find(),
                    "internal jargon \"" + banned.term() + "\" leaked into OpenAPI prose on " + context);
        }
    }

    /**
     * Fails if {@code text} names the implementation language or one of its types, or carries documentation markup that
     * only renders inside source. {@code context} identifies where the text came from, for the failure message only.
     */
    public static void assertLanguageNeutral(String context, String text) {
        assertFalse(JAVADOC_MARKUP.matcher(text).find(),
                "source documentation markup leaked into OpenAPI prose on " + context + ": " + text);

        assertFalse(SOURCE_ORDER_REFERENCE.matcher(text.toLowerCase(Locale.ROOT)).find(),
                "OpenAPI prose on " + context + " points at a neighbouring field by position, which a reader of the "
                        + "document cannot resolve. Name the property: " + text);

        java.util.regex.Matcher path = UNVERSIONED_PATH.matcher(text);
        assertFalse(path.find(), () -> "OpenAPI prose on " + context + " names a path that is not the absolute one the "
                + "document publishes" + (path.groupCount() >= 1 ? ": " + path.group(1) : ""));

        String lower = text.toLowerCase(Locale.ROOT);
        for (BannedTerm banned : LANGUAGE_TERMS) {
            assertFalse(banned.pattern().matcher(lower).find(), "implementation-language term \"" + banned.term()
                    + "\" leaked into OpenAPI prose on " + context + ", which is read by authors in other languages");
        }
    }

    private record BannedTerm(String term, Pattern pattern) {

        /**
         * A word boundary belongs only where the term itself ends in a word character. Asking for one after a term such
         * as {@code char[]} demands a word character right after the bracket, which prose never has, and the term then
         * matches nothing at all.
         */
        private static BannedTerm of(String term) {
            return new BannedTerm(term, Pattern
                    .compile(
                            boundary(term.charAt(0)) + Pattern.quote(term) + boundary(term.charAt(term.length() - 1))));
        }

        private static String boundary(char edge) {
            return Character.isLetterOrDigit(edge) || edge == '_' ? "\\b" : "";
        }
    }
}
