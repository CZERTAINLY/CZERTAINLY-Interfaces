package com.otilm.api.model.core.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchFieldDataDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void roundTripsAFieldThatIsBothDisplayableAndSortable() throws Exception {
        // given — a property field the picker may offer as a column and the listing may order by
        var dto = new SearchFieldDataDto();
        dto.setFieldIdentifier("commonName");
        dto.setFieldLabel("Common Name");
        dto.setType(FilterFieldType.STRING);
        dto.setConditions(List.of(FilterConditionOperator.EQUALS));
        dto.setDisplayable(true);
        dto.setSortable(true);

        // when
        var back = mapper.readValue(mapper.writeValueAsString(dto), SearchFieldDataDto.class);

        // then
        assertTrue(back.isDisplayable());
        assertTrue(back.isSortable());
    }

    @Test
    void roundTripsAnAttributeFieldThatIsDisplayableButNotSortable() throws Exception {
        // given — attribute-sourced fields are displayable but not sortable
        var dto = new SearchFieldDataDto();
        dto.setFieldIdentifier("department");
        dto.setFieldLabel("Department");
        dto.setType(FilterFieldType.STRING);
        dto.setAttributeContentType(AttributeContentType.STRING);
        dto.setDisplayable(true);
        dto.setSortable(false);

        // when
        var back = mapper.readValue(mapper.writeValueAsString(dto), SearchFieldDataDto.class);

        // then
        assertTrue(back.isDisplayable());
        assertFalse(back.isSortable());
        assertEquals(AttributeContentType.STRING, back.getAttributeContentType());
    }

    @Test
    void roundTripsAFieldWithheldFromThePicker() throws Exception {
        // given — secret content is filterable but must never be offered as a column
        var dto = new SearchFieldDataDto();
        dto.setFieldIdentifier("secretValue");
        dto.setDisplayable(false);
        dto.setSortable(false);

        // when
        var back = mapper.readValue(mapper.writeValueAsString(dto), SearchFieldDataDto.class);

        // then
        assertFalse(back.isDisplayable());
        assertFalse(back.isSortable());
    }

    @Test
    void leavesTheNewFlagsUnsetOnACatalogueEntryThatPredatesThem() throws Exception {
        // given — the wire form a core version without column support returns
        var json = "{\"fieldIdentifier\":\"commonName\",\"fieldLabel\":\"Common Name\",\"type\":\"string\"}";

        // when
        var back = mapper.readValue(json, SearchFieldDataDto.class);

        // then — absent stays absent rather than defaulting to a claim either way
        assertNull(back.isDisplayable());
        assertNull(back.isSortable());
        assertNull(back.isMultiValue());
    }
}
