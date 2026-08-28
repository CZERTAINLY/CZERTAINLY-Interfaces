package com.otilm.api.model.core.certificate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import com.otilm.api.model.common.attribute.v3.content.StringAttributeContentV3;
import com.otilm.api.model.core.search.FilterFieldSource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class CertificateDtoAttributeValuesTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void omitsAttributeValuesWhenNoColumnsWereProjected() throws Exception {
        // given — a listing entry as it is built when the request carried no columns
        var dto = new CertificateDto();
        dto.setUuid("d8b0b0d6-2a3e-11ee-be56-0242ac120002");
        dto.setCommonName("web01");

        // when
        var json = mapper.writeValueAsString(dto);

        // then — the property must be absent, not null, so the response stays what it was
        assertFalse(json.contains("attributeValues"));
        assertNull(dto.getAttributeValues());
    }

    @Test
    void roundTripsValuesKeyedBySourceThenIdentifier() throws Exception {
        // given — the same identifier under two sources, which is why the source is part of the key
        var dto = new CertificateDto();
        dto
                .setAttributeValues(Map
                        .of(FilterFieldSource.CUSTOM, Map.of("department", List.of(content("PKI Ops"))),
                                FilterFieldSource.META, Map.of("department", List.of(content("scanned")))));

        // when
        var back = mapper.readValue(mapper.writeValueAsString(dto), CertificateDto.class);

        // then
        assertEquals("PKI Ops",
                back.getAttributeValues().get(FilterFieldSource.CUSTOM).get("department").get(0).getData());
        assertEquals("scanned",
                back.getAttributeValues().get(FilterFieldSource.META).get("department").get(0).getData());
    }

    @Test
    void keysTheSourceByItsWireCode() throws Exception {
        // given
        var dto = new CertificateDto();
        dto.setAttributeValues(Map.of(FilterFieldSource.CUSTOM, Map.of("department", List.of(content("PKI Ops")))));

        // when
        Map<String, Object> properties = mapper.readValue(mapper.writeValueAsString(dto), new TypeReference<>() {
        });

        // then — "custom", not "CUSTOM", so the frontend can key off the catalogue's fieldSource verbatim
        assertEquals(Set.of("custom"), ((Map<?, ?>) properties.get("attributeValues")).keySet());
    }

    @Test
    void carriesEveryValueOfAMultiValuedAttribute() throws Exception {
        // given — a multi-valued attribute must not be flattened to its first value
        var dto = new CertificateDto();
        dto
                .setAttributeValues(
                        Map.of(FilterFieldSource.CUSTOM, Map.of("tags", List.of(content("prod"), content("edge")))));

        // when
        var back = mapper.readValue(mapper.writeValueAsString(dto), CertificateDto.class);

        // then
        assertEquals(List.of("prod", "edge"),
                back
                        .getAttributeValues()
                        .get(FilterFieldSource.CUSTOM)
                        .get("tags")
                        .stream()
                        .map(BaseAttributeContentV3::getData)
                        .toList());
    }

    @Test
    void carriesNoEntryForAFieldTheCertificateHasNoValueFor() throws Exception {
        // given — a projected column the object has nothing under
        var dto = new CertificateDto();
        dto.setAttributeValues(Map.of(FilterFieldSource.CUSTOM, Map.of("department", List.of(content("PKI Ops")))));

        // when
        var back = mapper.readValue(mapper.writeValueAsString(dto), CertificateDto.class);

        // then — the cell is rendered as empty from the absence, not from an empty list
        assertFalse(back.getAttributeValues().get(FilterFieldSource.CUSTOM).containsKey("owner"));
        assertNull(back.getAttributeValues().get(FilterFieldSource.DATA));
    }

    private static StringAttributeContentV3 content(String value) {
        var item = new StringAttributeContentV3();
        item.setData(value);
        item.setContentType(AttributeContentType.STRING);
        return item;
    }
}
