package com.otilm.api.model.core.cryptography.key;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.common.attribute.v3.content.StringAttributeContentV3;
import com.otilm.api.model.common.enums.cryptography.KeyAlgorithm;
import com.otilm.api.model.common.enums.cryptography.KeyFormat;
import com.otilm.api.model.common.enums.cryptography.KeyType;
import com.otilm.api.model.core.compliance.ComplianceStatus;
import com.otilm.api.model.core.search.FilterFieldSource;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class KeyItemDtoAttributeValuesTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void omitsAttributeValuesWhenNoColumnsWereProjected() throws Exception {
        // given — a key listing entry as it is built when the request carried no columns
        var dto = new KeyItemDto();
        dto.setName("signing-key");

        // when
        var json = mapper.writeValueAsString(dto);

        // then
        assertFalse(json.contains("attributeValues"));
        assertNull(dto.getAttributeValues());
    }

    @Test
    @SuppressWarnings("deprecation")
    void keepsThePreProjectionAllArgumentsSignature() {
        // given — the legacy positional signature stays resolvable, so a consumer compiled against an earlier
        // release keeps binary compatibility
        var dto = new KeyItemDto("signing key", OffsetDateTime.parse("2026-01-01T00:00:00Z"),
                "3c8bd0f6-2e1a-4a5f-9a3f-1f0f6a2b1c11", null, null, null, null, "admin",
                "9b8a7c6d-5e4f-4a3b-8c2d-1e0f9a8b7c66", List.of(), 0, null, KeyType.PRIVATE_KEY, KeyAlgorithm.RSA,
                KeyFormat.PRKI, 4096, List.of(KeyUsage.SIGN), true, KeyState.ACTIVE, ComplianceStatus.OK);

        // then — the field the new signature carries is simply left unset
        assertNull(dto.getAttributeValues());
        assertEquals("signing key", dto.getDescription());
    }

    @Test
    void roundTripsValuesKeyedBySourceThenIdentifier() throws Exception {
        // given
        var dto = new KeyItemDto();
        dto
                .setAttributeValues(Map
                        .of(FilterFieldSource.CUSTOM,
                                Map.of("purpose", List.of(new StringAttributeContentV3("signing")))));

        // when
        var back = mapper.readValue(mapper.writeValueAsString(dto), KeyItemDto.class);

        // then
        assertEquals("signing",
                back.getAttributeValues().get(FilterFieldSource.CUSTOM).get("purpose").get(0).getData());
    }
}
