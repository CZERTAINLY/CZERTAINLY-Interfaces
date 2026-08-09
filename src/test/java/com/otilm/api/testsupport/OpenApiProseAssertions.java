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

    private static final List<BannedTerm> BANNED_TERMS = BANNED_JARGON.stream().map(BannedTerm::of).toList();

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

    private record BannedTerm(String term, Pattern pattern) {

        private static BannedTerm of(String term) {
            return new BannedTerm(term, Pattern.compile("\\b" + Pattern.quote(term) + "\\b"));
        }
    }
}
