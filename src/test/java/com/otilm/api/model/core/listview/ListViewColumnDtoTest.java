package com.otilm.api.model.core.listview;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.core.search.FilterFieldSource;
import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListViewColumnDtoTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final Validator VALIDATOR = VALIDATORS.validator();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void omitsTheLabelWhenTheColumnFollowsTheCatalogue() throws Exception {
        // given — no override, so a field that is relabelled later follows along
        var dto = new ListViewColumnDto(FilterFieldSource.PROPERTY, "commonName", null);

        // when
        var json = mapper.writeValueAsString(dto);

        // then
        assertFalse(json.contains("label"));
        assertNull(mapper.readValue(json, ListViewColumnDto.class).getLabel());
    }

    @Test
    void roundTripsAPinnedHeading() throws Exception {
        // given — the operator renamed the heading for this view only
        var dto = new ListViewColumnDto(FilterFieldSource.CUSTOM, "department", "Owning team");

        // when
        var back = mapper.readValue(mapper.writeValueAsString(dto), ListViewColumnDto.class);

        // then
        assertEquals("Owning team", back.getLabel());
        assertEquals(FilterFieldSource.CUSTOM, back.getFieldSource());
        assertEquals("department", back.getFieldIdentifier());
    }

    @Test
    void acceptsAHeadingAtTheStoredColumnLength() {
        assertTrue(VALIDATOR
                .validate(new ListViewColumnDto(FilterFieldSource.CUSTOM, "department", "x".repeat(255)))
                .isEmpty());
    }

    @Test
    void rejectsAHeadingLongerThanTheStoredColumn() {
        // given
        var violations = VALIDATOR
                .validate(new ListViewColumnDto(FilterFieldSource.CUSTOM, "department", "x".repeat(256)));

        // then
        assertEquals(1, violations.size());
        assertEquals("label", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void rejectsABlankHeading() {
        // given — a whitespace-only heading would pin a blank column title instead of falling back to the catalogue
        var violations = VALIDATOR.validate(new ListViewColumnDto(FilterFieldSource.CUSTOM, "department", "   "));

        // then
        assertEquals(1, violations.size());
        assertEquals("label", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void keepsTheHeadingOutOfColumnIdentity() {
        // given — a view stores identifiers; two entries for the same field are the same column however it is headed
        var plain = new ListViewColumnDto(FilterFieldSource.CUSTOM, "department", null);
        var renamed = new ListViewColumnDto(FilterFieldSource.CUSTOM, "department", "Owning team");

        // then — equality intentionally includes the label, so an edit that only renames is still a change to persist
        assertFalse(plain.equals(renamed));
        assertEquals(plain.getFieldIdentifier(), renamed.getFieldIdentifier());
        assertEquals(plain.getFieldSource(), renamed.getFieldSource());
    }
}
