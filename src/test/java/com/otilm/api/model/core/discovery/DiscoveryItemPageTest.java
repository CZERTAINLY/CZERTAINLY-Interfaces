package com.otilm.api.model.core.discovery;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.otilm.api.model.client.discovery.DiscoveryCertificateResponseDto;
import com.otilm.api.model.common.PaginationResponseDto;
import com.otilm.api.model.common.attribute.v3.MetadataAttributeV3;
import com.otilm.api.model.connector.discovery.v2.DiscoveredCertificateDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveredKeyDto;
import com.otilm.api.model.core.auth.Resource;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    /** {@link #certificateItem()} plus the processing state Core stamps on an item after attempting ingestion. */
    private static DiscoveryItemDto processedItem() {
        DiscoveryItemDto item = certificateItem();
        item.setDiscoveredAt(OffsetDateTime.of(2026, 8, 4, 10, 30, 0, 0, ZoneOffset.UTC));
        item.setProcessedError("key algorithm not supported");
        MetadataAttributeV3 where = new MetadataAttributeV3();
        where.setName("discoverySource");
        item.setMeta(List.of(where));
        item.setNewlyDiscovered(true);
        item.setProcessed(true);
        item.setInventoryUuid(null);
        return item;
    }

    private static PaginationResponseDto<DiscoveryItemDto> pageOf(DiscoveryItemDto... items) {
        PaginationResponseDto<DiscoveryItemDto> page = new PaginationResponseDto<>();
        page.setItems(List.of(items));
        page.setItemsPerPage(10);
        page.setPageNumber(0);
        page.setTotalPages(3);
        page.setTotalItems(21L);
        return page;
    }

    private PaginationResponseDto<DiscoveryItemDto> roundTrip(PaginationResponseDto<DiscoveryItemDto> page)
            throws Exception {
        return mapper
                .readValue(mapper.writeValueAsString(page),
                        new TypeReference<PaginationResponseDto<DiscoveryItemDto>>() {
                        });
    }

    @Test
    void pagingNumbersSurviveTheRoundTrip() throws Exception {
        PaginationResponseDto<DiscoveryItemDto> back = roundTrip(pageOf(processedItem()));

        assertEquals(10, back.getItemsPerPage());
        assertEquals(0, back.getPageNumber());
        assertEquals(3, back.getTotalPages());
        assertEquals(21L, back.getTotalItems());
    }

    /**
     * The part the shared envelope does not give us for free: {@code T} is erased, so the payload is resolved by its
     * own discriminator, not by the page.
     */
    @Test
    void payloadSubtypeResolvesInsideTheErasedGeneric() throws Exception {
        PaginationResponseDto<DiscoveryItemDto> back = roundTrip(pageOf(processedItem()));

        assertEquals(1, back.getItems().size());
        DiscoveryItemDto item = back.getItems().get(0);
        assertEquals(Resource.CERTIFICATE, item.getResource());
        assertEquals("MIIBOgIBAAJBAK", ((DiscoveredCertificateDto) item.getPayload()).getCertificateData());
    }

    /**
     * Core's own processing state, the reason this is not the connector's {@code DiscoveredItemDto}: without it a
     * failed key ingestion would be indistinguishable from a successful one in this listing.
     */
    @Test
    void coreProcessingStateSurvivesTheRoundTrip() throws Exception {
        DiscoveryItemDto item = roundTrip(pageOf(processedItem())).getItems().get(0);

        assertEquals("6f1b8c1e-0000-4000-8000-000000000001", item.getUuid());
        assertEquals(1L, item.getSequence());
        assertEquals("10.0.0.7:443", item.getUniqueRef());
        assertEquals("key algorithm not supported", item.getProcessedError());
        assertNull(item.getInventoryUuid(), "an item whose processing failed never produced an inventory object");
        assertEquals("discoverySource", item.getMeta().get(0).getName(),
                "the provider-reported location context must survive the round trip");
        assertTrue(item.isNewlyDiscovered());
        assertTrue(item.isProcessed());
    }

    /**
     * Pinned as literal wire names: {@code newlyDiscovered} and {@code processed} are Lombok-backed booleans, so a
     * rename would fail as a compile error elsewhere in this file rather than as an assertion, and a round-trip test
     * renames both ends at once.
     */
    @Test
    void processingStateFieldsArePinnedToTheirWireNames() {
        JsonNode emitted = mapper.valueToTree(pageOf(processedItem())).get("items").get(0);

        assertTrue(emitted.has("newlyDiscovered"), emitted.toString());
        assertTrue(emitted.has("processed"), emitted.toString());
        // NON_NULL at class level: the schema promises absence, not null, for what never happened.
        assertFalse(emitted.has("inventoryUuid"), emitted.toString());
        assertTrue(emitted.has("meta"), emitted.toString());
    }

    /**
     * A payload-less item is trivially buildable — no-args constructor plus setters is exactly the state a Core mapper
     * passes through before populating one — so the derived getter's null branch is a real code path: no payload means
     * no resource, and with class-level {@code NON_NULL} the serialized item carries no {@code resource} property even
     * though the schema marks it required for every item Core actually publishes.
     */
    @Test
    void payloadlessItemHasNoResource() {
        DiscoveryItemDto empty = new DiscoveryItemDto();

        assertNull(empty.getResource());
        assertFalse(mapper.valueToTree(empty).has("resource"),
                "the derived resource must stay out of the emitted JSON when there is no payload to derive it from");
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
    void pagingMembersSerializeUnderTheSameNamesAsTheCertificateListing() {
        DiscoveryCertificateResponseDto certificates = new DiscoveryCertificateResponseDto();
        certificates.setCertificates(List.of());
        certificates.setItemsPerPage(10);
        certificates.setPageNumber(0);
        certificates.setTotalPages(3);
        certificates.setTotalItems(21L);

        assertEquals(pagingMembersOf(certificates), pagingMembersOf(pageOf(certificateItem())),
                "the items listing must page under the same property names as the certificate listing");
    }

    /** Emitted property names other than the payload array, sorted. */
    private String pagingMembersOf(Object dto) {
        JsonNode root = mapper.valueToTree(dto);
        List<String> names = new ArrayList<>();
        root.fieldNames().forEachRemaining(names::add);
        return names.stream().filter(name -> !root.get(name).isArray()).sorted().collect(Collectors.joining(","));
    }

    /**
     * Asserted inside the payload subtree, not against the whole document: a document-wide match for
     * {@code "resource":"certificates"} is satisfied by the item-level derived {@code resource}, a different field, and
     * so constrains nothing about the payload discriminator.
     */
    @Test
    void itemPayloadCarriesItsOwnResourceWireCode() {
        DiscoveryItemDto keyItem = certificateItem();
        DiscoveredKeyDto keyPayload = new DiscoveredKeyDto();
        keyPayload.setFingerprint("2b:9c:...");
        keyItem.setPayload(keyPayload);
        // No item-level resource to set: the accessor derives it from the payload, so the two disagreeing
        // is unrepresentable — swapping the payload above IS what makes this a key item.

        JsonNode items = mapper.valueToTree(pageOf(certificateItem(), keyItem)).get("items");
        assertEquals("certificates", items.get(0).get("payload").get("resource").asText());
        assertEquals("keys", items.get(1).get("payload").get("resource").asText());
    }
}
