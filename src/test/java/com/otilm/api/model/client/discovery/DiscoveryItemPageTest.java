package com.otilm.api.model.client.discovery;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.otilm.api.model.common.PaginationResponseDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveredCertificateDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveredKeyDto;
import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.discovery.DiscoveryItemDto;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Covers the wire shape of {@code GET /v1/discoveries/{uuid}/items}, which returns
 * {@code PaginationResponseDto<DiscoveryItemDto>} — the same generic envelope eleven other core-web controllers already
 * page with, rather than a hand-rolled response DTO.
 *
 * <p>
 * Worth testing despite the envelope being shared: nothing else in the platform puts a polymorphic payload inside it,
 * and the generic erases {@code T}, so the item's payload discriminator surviving serialization is a property of this
 * pairing rather than of the envelope.
 */
class DiscoveryItemPageTest {

    // findAndAddModules() for the JSR-310 module discoveredAt needs.
    private final ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();

    private static DiscoveryItemDto certificateItem() {
        DiscoveredCertificateDto payload = new DiscoveredCertificateDto();
        payload.setCertificateData("MIIBOgIBAAJBAK");
        DiscoveryItemDto item = new DiscoveryItemDto();
        item.setUuid("6f1b8c1e-0000-4000-8000-000000000001");
        item.setSequence(1L);
        item.setUniqueRef("10.0.0.7:443");
        item.setPayload(payload);
        return item;
    }

    @Test
    void roundTripsPageOfItems() throws Exception {
        DiscoveryItemDto item = certificateItem();
        item.setDiscoveredAt(OffsetDateTime.of(2026, 8, 4, 10, 30, 0, 0, ZoneOffset.UTC));
        item.setProcessedError("key algorithm not supported");
        item.setNewlyDiscovered(true);
        item.setProcessed(true);
        item.setInventoryUuid(null);

        PaginationResponseDto<DiscoveryItemDto> page = new PaginationResponseDto<>();
        page.setItems(List.of(item));
        page.setItemsPerPage(10);
        page.setPageNumber(0);
        page.setTotalPages(3);
        page.setTotalItems(21L);

        String json = mapper.writeValueAsString(page);
        PaginationResponseDto<DiscoveryItemDto> back = mapper
                .readValue(json, new TypeReference<PaginationResponseDto<DiscoveryItemDto>>() {
                });

        assertEquals(10, back.getItemsPerPage());
        assertEquals(0, back.getPageNumber());
        assertEquals(3, back.getTotalPages());
        assertEquals(21L, back.getTotalItems());

        // The item survives with its payload subtype intact. This is the part the shared envelope does not
        // give us for free: T is erased, so the payload is resolved by its own discriminator, not by the page.
        assertEquals(1, back.getItems().size());
        assertEquals(1L, back.getItems().get(0).getSequence());
        assertEquals("10.0.0.7:443", back.getItems().get(0).getUniqueRef());
        assertEquals(Resource.CERTIFICATE, back.getItems().get(0).getResource());
        assertEquals("MIIBOgIBAAJBAK",
                ((DiscoveredCertificateDto) back.getItems().get(0).getPayload()).getCertificateData());

        // Core's own processing state, the reason this is not the connector's DiscoveredItemDto: without it a
        // failed key ingestion would be indistinguishable from a successful one in this listing.
        assertEquals("6f1b8c1e-0000-4000-8000-000000000001", back.getItems().get(0).getUuid());
        assertEquals("key algorithm not supported", back.getItems().get(0).getProcessedError());
        assertNull(back.getItems().get(0).getInventoryUuid(),
                "an item whose processing failed never produced an inventory object");
        assertTrue(back.getItems().get(0).isNewlyDiscovered());
        assertTrue(back.getItems().get(0).isProcessed());

        // Pinned as literal wire names too: both are Lombok-backed booleans, so a rename would fail as a
        // compile error in this file rather than as an assertion, and a round-trip renames both ends at once.
        JsonNode emitted = mapper.valueToTree(page).get("items").get(0);
        assertTrue(emitted.has("newlyDiscovered"), emitted.toString());
        assertTrue(emitted.has("processed"), emitted.toString());
    }

    /**
     * This listing and the certificate listing must publish their paging members under the same names, so a caller
     * moving between them does not meet a second spelling of the same four numbers.
     *
     * <p>
     * Compared as emitted JSON rather than as declared fields on purpose: the generic envelope types these as
     * {@code int}/{@code long} while {@link DiscoveryCertificateResponseDto} uses {@code Integer}/{@code Long}, so a
     * field-type comparison would fail on a difference no caller can observe. Both are {@code REQUIRED} and always
     * populated, so the boxing never surfaces as a present-versus-null distinction either.
     */
    @Test
    void pagingMembersSerializeUnderTheSameNamesAsTheCertificateListing() throws Exception {
        PaginationResponseDto<DiscoveryItemDto> page = new PaginationResponseDto<>();
        page.setItems(List.of(certificateItem()));
        page.setItemsPerPage(10);
        page.setPageNumber(0);
        page.setTotalPages(3);
        page.setTotalItems(21L);

        DiscoveryCertificateResponseDto certificates = new DiscoveryCertificateResponseDto();
        certificates.setCertificates(List.of());
        certificates.setItemsPerPage(10);
        certificates.setPageNumber(0);
        certificates.setTotalPages(3);
        certificates.setTotalItems(21L);

        assertEquals(pagingMembersOf(certificates), pagingMembersOf(page),
                "the items listing must page under the same property names as the certificate listing");
    }

    /** Emitted property names other than the payload array, sorted. */
    private String pagingMembersOf(Object dto) throws Exception {
        JsonNode root = mapper.valueToTree(dto);
        return StreamSupport
                .stream(((Iterable<String>) root::fieldNames).spliterator(), false)
                .filter(name -> !root.get(name).isArray())
                .collect(Collectors.toCollection(TreeSet::new))
                .stream()
                .collect(Collectors.joining(","));
    }

    /**
     * Asserted inside the payload subtree, not against the whole document. A substring check for
     * {@code "resource":"certificates"} anywhere in the JSON is satisfied by {@link DiscoveryItemDto#getResource()}, a
     * different field — verified: it passes with {@code payload} set to null, so it proves nothing about the
     * discriminator it is named for.
     */
    @Test
    void itemPayloadCarriesItsOwnResourceWireCode() throws Exception {
        DiscoveryItemDto keyItem = certificateItem();
        DiscoveredKeyDto keyPayload = new DiscoveredKeyDto();
        keyPayload.setFingerprint("2b:9c:...");
        keyItem.setPayload(keyPayload);
        // No item-level resource to set: the accessor derives it from the payload, so the two disagreeing
        // is unrepresentable — swapping the payload above IS what makes this a key item.

        PaginationResponseDto<DiscoveryItemDto> page = new PaginationResponseDto<>();
        page.setItems(List.of(certificateItem(), keyItem));

        JsonNode items = mapper.valueToTree(page).get("items");
        assertEquals("certificates", items.get(0).get("payload").get("resource").asText());
        assertEquals("keys", items.get(1).get("payload").get("resource").asText());
    }
}
