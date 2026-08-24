package com.otilm.api.model.core.discovery;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.otilm.api.model.client.discovery.DiscoveryCertificateResponseDto;
import com.otilm.api.model.common.PaginationResponseDto;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Covers the wire shape of {@code GET /v1/discoveries/{uuid}/messages}, which returns
 * {@code PaginationResponseDto<DiscoveryMessageDto>} — the same generic envelope the item and certificate listings page
 * with, rather than a hand-rolled response DTO.
 *
 * <p>
 * Worth testing despite the envelope being shared, for the reason its sibling {@link DiscoveryItemPageTest} states:
 * {@code T} is erased, so what survives serialization inside the envelope is a property of this pairing rather than of
 * the envelope. Here the value at risk is the severity enum's wire code, which reaches a client through two levels of
 * generic nesting.
 */
class DiscoveryMessagePageTest {

    // findAndAddModules() for the JSR-310 module the seen-at timestamps need.
    private final ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();

    private static DiscoveryMessageDto message(DiscoveryMessageSeverity severity, String code, long occurrences) {
        DiscoveryMessageDto dto = new DiscoveryMessageDto();
        dto.setSeverity(severity);
        dto.setCode(code);
        dto.setMessage("something the platform wrote for a person to read");
        dto.setOccurrences(occurrences);
        dto.setFirstSeenAt(OffsetDateTime.of(2026, 8, 24, 9, 15, 0, 0, ZoneOffset.UTC));
        dto.setLastSeenAt(OffsetDateTime.of(2026, 8, 24, 9, 57, 0, 0, ZoneOffset.UTC));
        return dto;
    }

    private static PaginationResponseDto<DiscoveryMessageDto> pageOf(DiscoveryMessageDto... messages) {
        PaginationResponseDto<DiscoveryMessageDto> page = new PaginationResponseDto<>();
        page.setItems(List.of(messages));
        page.setItemsPerPage(10);
        page.setPageNumber(0);
        page.setTotalPages(3);
        page.setTotalItems(21L);
        return page;
    }

    @Test
    void pagingNumbersSurviveTheRoundTrip() throws Exception {
        PaginationResponseDto<DiscoveryMessageDto> page = pageOf(
                message(DiscoveryMessageSeverity.WARNING, "SKIPPED", 3L));

        PaginationResponseDto<DiscoveryMessageDto> back = mapper
                .readValue(mapper.writeValueAsString(page),
                        new TypeReference<PaginationResponseDto<DiscoveryMessageDto>>() {
                        });

        assertEquals(10, back.getItemsPerPage());
        assertEquals(0, back.getPageNumber());
        assertEquals(3, back.getTotalPages());
        assertEquals(21L, back.getTotalItems());
        assertEquals(DiscoveryMessageSeverity.WARNING, back.getItems().get(0).getSeverity());
        assertEquals(3L, back.getItems().get(0).getOccurrences());
    }

    /**
     * Asserted inside the items array rather than against the whole document: severity travels as a wire code, and a
     * document-wide match would be satisfied by any occurrence of the string anywhere in the envelope.
     */
    @Test
    void severityTravelsAsAWireCodeInsideTheEnvelope() {
        JsonNode items = mapper
                .valueToTree(pageOf(message(DiscoveryMessageSeverity.INFO, "NOTED", 1L),
                        message(DiscoveryMessageSeverity.ERROR, "FAILED", 9L)))
                .get("items");

        assertEquals("info", items.get(0).get("severity").asText());
        assertEquals("error", items.get(1).get("severity").asText());
    }

    /**
     * The listing must not invent its own paging contract — the frontend pages every discovery listing with one helper.
     *
     * <p>
     * Compared as emitted JSON rather than as declared fields, for the reason its sibling states: the generic envelope
     * types these as {@code int}/{@code long} while {@link DiscoveryCertificateResponseDto} uses
     * {@code Integer}/{@code Long}, a difference no caller can observe.
     */
    @Test
    void pagingMembersSerializeUnderTheSameNamesAsTheCertificateListing() {
        DiscoveryCertificateResponseDto certificates = new DiscoveryCertificateResponseDto();
        certificates.setCertificates(List.of());
        certificates.setItemsPerPage(10);
        certificates.setPageNumber(0);
        certificates.setTotalPages(3);
        certificates.setTotalItems(21L);

        assertEquals(pagingMembersOf(certificates),
                pagingMembersOf(pageOf(message(DiscoveryMessageSeverity.WARNING, "SKIPPED", 3L))),
                "the run messages listing must page under the same property names as the certificate listing");
    }

    /** Emitted property names other than the payload array, sorted. */
    private String pagingMembersOf(Object dto) {
        JsonNode root = mapper.valueToTree(dto);
        List<String> names = new ArrayList<>();
        root.fieldNames().forEachRemaining(names::add);
        return names.stream().filter(name -> !root.get(name).isArray()).sorted().collect(Collectors.joining(","));
    }
}
