package com.otilm.api.model.client.certificate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.core.search.FilterConditionOperator;
import com.otilm.api.model.core.search.FilterFieldSource;
import com.otilm.api.model.core.search.SortDirection;
import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchRequestDtoTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final Validator VALIDATOR = VALIDATORS.validator();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void omitsSortAndColumnsWhenUnset() throws Exception {
        // given — a request built the way every caller predating this change builds one
        var dto = new SearchRequestDto();
        dto.setFilters(List.of(filter()));

        // when
        var json = mapper.writeValueAsString(dto);
        Map<String, Object> properties = mapper.readValue(json, new TypeReference<>() {
        });

        // then — the serialized request must stay what it was, so existing API clients see no change
        assertEquals(Set.of("filters", "itemsPerPage", "pageNumber"), properties.keySet());
    }

    @Test
    void readsBackARequestThatCarriesNeitherSortNorColumns() throws Exception {
        // given — the wire form of a pre-existing client's request
        var json = "{\"filters\":[],\"itemsPerPage\":25,\"pageNumber\":3}";

        // when
        var back = mapper.readValue(json, SearchRequestDto.class);

        // then
        assertNull(back.getSort());
        assertNull(back.getColumns());
        assertEquals(25, back.getItemsPerPage());
        assertEquals(3, back.getPageNumber());
    }

    @Test
    void roundTripsSortAndColumns() throws Exception {
        // given
        var dto = new SearchRequestDto();
        dto.setSort(new SearchSortRequestDto(FilterFieldSource.PROPERTY, "notAfter", SortDirection.DESC));
        dto
                .setColumns(List
                        .of(new SearchColumnRequestDto(FilterFieldSource.PROPERTY, "commonName"),
                                new SearchColumnRequestDto(FilterFieldSource.CUSTOM, "department")));

        // when
        var back = mapper.readValue(mapper.writeValueAsString(dto), SearchRequestDto.class);

        // then
        assertEquals(FilterFieldSource.PROPERTY, back.getSort().getFieldSource());
        assertEquals("notAfter", back.getSort().getFieldIdentifier());
        assertEquals(SortDirection.DESC, back.getSort().getDirection());
        assertEquals(2, back.getColumns().size());
        assertEquals(FilterFieldSource.CUSTOM, back.getColumns().get(1).getFieldSource());
        assertEquals("department", back.getColumns().get(1).getFieldIdentifier());
    }

    @Test
    void ordersColumnsAsGiven() throws Exception {
        // given — column order is display order, so it must survive the wire
        var dto = new SearchRequestDto();
        dto
                .setColumns(List
                        .of(new SearchColumnRequestDto(FilterFieldSource.META, "b"),
                                new SearchColumnRequestDto(FilterFieldSource.META, "a")));

        // when
        var back = mapper.readValue(mapper.writeValueAsString(dto), SearchRequestDto.class);

        // then
        assertEquals(List.of("b", "a"),
                back.getColumns().stream().map(SearchColumnRequestDto::getFieldIdentifier).toList());
    }

    @Test
    void acceptsARequestWithAFullyPopulatedSortAndColumn() {
        // given
        var dto = new SearchRequestDto();
        dto.setSort(new SearchSortRequestDto(FilterFieldSource.PROPERTY, "notAfter", SortDirection.ASC));
        dto.setColumns(List.of(new SearchColumnRequestDto(FilterFieldSource.DATA, "extraField")));

        // then
        assertTrue(VALIDATOR.validate(dto).isEmpty());
    }

    @Test
    void rejectsASortWithoutAFieldIdentifier() {
        // given — a sort naming a source but no field cannot be turned into an ORDER BY term
        var dto = new SearchRequestDto();
        dto.setSort(new SearchSortRequestDto(FilterFieldSource.PROPERTY, "  ", SortDirection.ASC));

        // when
        var violations = VALIDATOR.validate(dto);

        // then
        assertEquals(1, violations.size());
        assertEquals("sort.fieldIdentifier", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void rejectsASortWithoutADirection() {
        // given
        var dto = new SearchRequestDto();
        dto.setSort(new SearchSortRequestDto(FilterFieldSource.PROPERTY, "notAfter", null));

        // when
        var violations = VALIDATOR.validate(dto);

        // then
        assertEquals(1, violations.size());
        assertEquals("sort.direction", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void rejectsAColumnWithoutASource() {
        // given — a field identifier is unique only within its source, so the source cannot be inferred
        var dto = new SearchRequestDto();
        dto.setColumns(List.of(new SearchColumnRequestDto(null, "department")));

        // when
        var violations = VALIDATOR.validate(dto);

        // then
        assertEquals(1, violations.size());
        assertEquals("columns[0].fieldSource", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void doesNotValidateColumnsOrSortWhenAbsent() {
        // given — the compatibility path: neither field set, so neither is validated
        var dto = new SearchRequestDto();

        // then
        assertTrue(VALIDATOR.validate(dto).isEmpty());
    }

    @Test
    void treatsSortAndColumnReferencesAsValueObjects() {
        // given — core compares stored view columns against requested ones, so equality must be by value
        var one = new SearchColumnRequestDto(FilterFieldSource.CUSTOM, "department");
        var same = new SearchColumnRequestDto(FilterFieldSource.CUSTOM, "department");
        var otherSource = new SearchColumnRequestDto(FilterFieldSource.META, "department");

        // then
        assertEquals(one, same);
        assertEquals(one.hashCode(), same.hashCode());
        assertFalse(one.equals(otherSource));
        assertEquals(new SearchSortRequestDto(FilterFieldSource.PROPERTY, "notAfter", SortDirection.ASC),
                new SearchSortRequestDto(FilterFieldSource.PROPERTY, "notAfter", SortDirection.ASC));
    }

    private static SearchFilterRequestDto filter() {
        return new SearchFilterRequestDto(FilterFieldSource.PROPERTY, "commonName", FilterConditionOperator.EQUALS,
                "web01");
    }
}
