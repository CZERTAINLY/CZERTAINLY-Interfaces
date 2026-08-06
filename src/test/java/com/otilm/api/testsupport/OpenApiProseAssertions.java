package com.otilm.api.testsupport;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Shared OpenAPI-prose guard for connector doc tests: the banned Core-internal jargon list and
 * the word-boundary matcher that checks for it. Doc tests must use this class rather than declare
 * their own list — per-test copies drift, and coverage then depends on which test happens to
 * exercise a given controller.
 */
public final class OpenApiProseAssertions {

    /**
     * Core-internal vocabulary that must never reach the published OpenAPI document — connector
     * authors in Java, Go and Python read only the generated document, never the internal design
     * docs. Matched on word boundaries so "tick" does not false-positive inside "ticket". Extend
     * the list when a new internal term needs banning; never narrow it for one controller's prose.
     */
    public static final List<String> BANNED_JARGON = List.of(
            "tick", "tick engine", "sweeper", "agenda table", "drain tick", "ingestor",
            "advisory lock", "push slice",
            "rung", "dispatch ladder", "expander", "scope chain", "fail closed", "fail-closed",
            "footgun", "s-1", "dependson", "ladder", "envelope assembly");

    private OpenApiProseAssertions() {
    }

    /**
     * Fails if {@code text} contains any {@link #BANNED_JARGON} term, matched case-insensitively
     * on word boundaries. {@code context} identifies where the text came from, for the failure
     * message only.
     */
    public static void assertNoJargon(String context, String text) {
        String lower = text.toLowerCase(Locale.ROOT);
        for (String banned : BANNED_JARGON) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(banned) + "\\b");
            assertFalse(pattern.matcher(lower).find(),
                    "internal jargon \"" + banned + "\" leaked into OpenAPI prose on " + context);
        }
    }
}
