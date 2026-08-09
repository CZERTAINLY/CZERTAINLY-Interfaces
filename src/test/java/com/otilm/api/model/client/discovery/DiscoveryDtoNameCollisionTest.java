package com.otilm.api.model.client.discovery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The client-facing and connector-facing discovery models must not share a simple type name.
 *
 * <p>
 * Core maps one onto the other — {@code DiscoveryServiceImpl} turns a client create request into a connector request —
 * so a class that needs both cannot single-type-import them, and Java offers no aliasing. A shared name is therefore
 * not a style question: it makes the pair unusable together, and it surfaces only once a consumer tries, in another
 * repository, after this one has published.
 *
 * <p>
 * This is a real regression, not a hypothetical. Renaming the client create request to {@code DiscoveryRequestDto}
 * collided with the long-standing connector-side {@code DiscoveryRequestDto} and broke Core's compile at exactly that
 * mapping method. The list projection is named {@code DiscoveryListDto} instead.
 */
class DiscoveryDtoNameCollisionTest {

    private static final Path CLIENT_MODEL = Path.of("src/main/java/com/otilm/api/model/client/discovery");
    private static final Path CONNECTOR_MODEL = Path.of("src/main/java/com/otilm/api/model/connector/discovery");

    private static Set<String> typeNamesIn(Path packageDir) throws IOException {
        // Recursive on purpose. A nested package is a different import but not a different simple name, and a class
        // that needs one type from each side still cannot single-type-import two types called the same thing —
        // which is the whole failure this guards. All twenty discovery v2 connector types live under `v2`, so
        // scanning the top level only would have left the guard blind to the package the feature actually grows in.
        try (Stream<Path> entries = Files.walk(packageDir)) {
            return entries
                    .filter(p -> p.getFileName().toString().endsWith(".java"))
                    .map(p -> p.getFileName().toString().replace(".java", ""))
                    .collect(Collectors.toCollection(TreeSet::new));
        }
    }

    /**
     * Guards the guard: if either package moves, {@link #typeNamesIn} would return nothing and the collision check
     * below would pass by vacuously comparing two empty sets.
     */
    @Test
    void bothModelPackagesAreWhereThisTestExpects() throws IOException {
        assertTrue(Files.isDirectory(CLIENT_MODEL),
                CLIENT_MODEL + " is missing; this test cannot see the client model");
        assertTrue(Files.isDirectory(CONNECTOR_MODEL),
                CONNECTOR_MODEL + " is missing; this test cannot see the connector model");
        assertFalse(typeNamesIn(CLIENT_MODEL).isEmpty(), "found no client discovery types");
        assertFalse(typeNamesIn(CONNECTOR_MODEL).isEmpty(), "found no connector discovery types");
    }

    @Test
    void noClientDiscoveryTypeSharesItsNameWithAConnectorDiscoveryType() throws IOException {
        Set<String> shared = new TreeSet<>(typeNamesIn(CLIENT_MODEL));
        shared.retainAll(typeNamesIn(CONNECTOR_MODEL));

        assertEquals(Set.of(), shared, "these names exist in both com.otilm.api.model.client.discovery and "
                + "com.otilm.api.model.connector.discovery, so no class can import both: " + shared);
    }
}
