package com.otilm.api.testsupport;

import org.junit.jupiter.api.Test;

import static com.otilm.api.testsupport.OpenApiProseAssertions.assertNoJargon;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link OpenApiProseAssertions} is the guard that keeps Core-internal vocabulary out of the published document, so
 * shortening its list must not shorten its reach. Longer phrases were dropped as redundant on the strength of the
 * word-boundary matcher; this holds the matcher to that claim.
 */
class OpenApiProseAssertionsTest {

    @Test
    void aPhraseSubsumedByAShorterEntryIsStillCaught() {
        AssertionError tickEngine = assertThrows(AssertionError.class,
                () -> assertNoJargon("test", "The tick engine advances each run."));
        assertTrue(tickEngine.getMessage().contains("tick"), tickEngine.getMessage());

        AssertionError drainTick = assertThrows(AssertionError.class,
                () -> assertNoJargon("test", "Each drain tick pulls one batch."));
        assertTrue(drainTick.getMessage().contains("tick"), drainTick.getMessage());

        AssertionError dispatchLadder = assertThrows(AssertionError.class,
                () -> assertNoJargon("test", "Walks the dispatch ladder in order."));
        assertTrue(dispatchLadder.getMessage().contains("ladder"), dispatchLadder.getMessage());
    }

    @Test
    void everyRemainingEntryIsStillCaughtOnItsOwn() {
        for (String banned : OpenApiProseAssertions.BANNED_JARGON) {
            assertThrows(AssertionError.class, () -> assertNoJargon("test", "Prose about the " + banned + " here."),
                    "banned term \"" + banned + "\" is no longer caught");
        }
    }

    @Test
    void aBannedTermInsideALongerWordIsNotAFalsePositive() {
        assertDoesNotThrow(() -> assertNoJargon("test", "Open a ticket for the expanded rungless run."),
                "matching must stay word-bounded: ticket, expanded and rungless are all legitimate");
    }

    @Test
    void matchingIsCaseInsensitive() {
        assertThrows(AssertionError.class, () -> assertNoJargon("test", "The SWEEPER runs hourly."));
    }
}
