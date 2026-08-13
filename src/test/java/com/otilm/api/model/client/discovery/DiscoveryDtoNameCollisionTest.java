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
 * The Core-side and connector-facing discovery models must not share a simple type name.
 *
 * <p>
 * Core maps one onto the other — {@code DiscoveryServiceImpl} turns a client create request into a connector request —
 * so a class that needs both cannot single-type-import them, and Java offers no aliasing. A shared name is therefore
 * not a style question: it makes the pair unusable together, and it surfaces only once a consumer tries, in another
 * repository, after this one has published.
 *
 * <p>
 * Two Core-side packages are guarded, because both are imported alongside the connector types: the client model, and
 * {@code model/core/discovery}, where {@code DiscoveryItemDto} lives — one connector-side name away from making
 * {@code DiscoveryServiceImpl} unable to import its own item type.
 */
class DiscoveryDtoNameCollisionTest {

    private static final Path CLIENT_MODEL = Path.of("src/main/java/com/otilm/api/model/client/discovery");
    private static final Path CORE_MODEL = Path.of("src/main/java/com/otilm/api/model/core/discovery");
    private static final Path CONNECTOR_MODEL = Path.of("src/main/java/com/otilm/api/model/connector/discovery");

    private static Set<String> typeNamesIn(Path packageDir) throws IOException {
        // Recursive on purpose. A nested package is a different import but not a different simple name, and a class
        // that needs one type from each side still cannot single-type-import two types called the same thing —
        // which is the whole failure this guards. The discovery v2 connector types all live under `v2`, so
        // scanning the top level only would have left the guard blind to the package the feature actually grows in.
        try (Stream<Path> entries = Files.walk(packageDir)) {
            return entries
                    .filter(p -> p.getFileName().toString().endsWith(".java"))
                    .map(p -> p.getFileName().toString().replace(".java", ""))
                    .collect(Collectors.toCollection(TreeSet::new));
        }
    }

    /**
     * Guards the guard: if any of the packages moves, {@link #typeNamesIn} would return nothing and the collision check
     * below would pass by vacuously comparing empty sets.
     */
    @Test
    void allModelPackagesAreWhereThisTestExpects() throws IOException {
        assertTrue(Files.isDirectory(CLIENT_MODEL),
                CLIENT_MODEL + " is missing; this test cannot see the client model");
        assertTrue(Files.isDirectory(CORE_MODEL), CORE_MODEL + " is missing; this test cannot see the core model");
        assertTrue(Files.isDirectory(CONNECTOR_MODEL),
                CONNECTOR_MODEL + " is missing; this test cannot see the connector model");
        assertFalse(typeNamesIn(CLIENT_MODEL).isEmpty(), "found no client discovery types");
        assertFalse(typeNamesIn(CORE_MODEL).isEmpty(), "found no core discovery types");
        assertFalse(typeNamesIn(CONNECTOR_MODEL).isEmpty(), "found no connector discovery types");
    }

    @Test
    void noCoreSideDiscoveryTypeSharesItsNameWithAConnectorDiscoveryType() throws IOException {
        Set<String> shared = new TreeSet<>(typeNamesIn(CLIENT_MODEL));
        shared.addAll(typeNamesIn(CORE_MODEL));
        shared.retainAll(typeNamesIn(CONNECTOR_MODEL));

        assertEquals(Set.of(), shared,
                "these names exist both on the Core side (model.client.discovery or "
                        + "model.core.discovery) and in com.otilm.api.model.connector.discovery, so no class can "
                        + "import both: " + shared);
    }
}
