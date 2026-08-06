package com.otilm.api.testsupport;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Shared OpenAPI-prose guard for connector doc tests: the banned Core-internal jargon list and
 * the word-boundary matcher that checks for it.
 *
 * <p>Before this was extracted, the same list and matcher existed as three separate,
 * near-identical copies across the discovery and attributes doc tests, and one of the three used
 * a plain substring match with its own, narrower word list — so whether a given internal term was
 * actually caught depended on which doc test happened to exercise the controller. Every doc test
 * that asserts published OpenAPI prose is free of Core-internal design vocabulary should use this
 * class instead of declaring its own copy.
 */
public final class OpenApiProseAssertions {

    /**
     * Core-internal vocabulary that must never reach the published OpenAPI document — connector
     * authors in Java, Go and Python only ever read the generated document, not the internal
     * design or planning docs. Matched on word boundaries so, for example, "tick" does not
     * false-positive inside "ticket" or "sticky". This is the union of every term any connector
     * doc test has banned to date; extend it when a new internal term needs banning, and don't
     * narrow it for the sake of one controller's prose.
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
