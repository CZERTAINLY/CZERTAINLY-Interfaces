package com.otilm.api.model.core.listview;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.search.FilterFieldSource;
import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListViewRequestDtoTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final Validator VALIDATOR = VALIDATORS.validator();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void acceptsAViewNamingAResourceAndAtLeastOneColumn() {
        assertTrue(VALIDATOR.validate(request("Expiry watch", Resource.CERTIFICATE, column("commonName"))).isEmpty());
    }

    @Test
    void rejectsAViewWithoutAName() {
        // given — views are addressed by name in the selector, so a blank one is unusable
        var violations = VALIDATOR.validate(request("   ", Resource.CERTIFICATE, column("commonName")));

        // then
        assertEquals(Set.of("name"), paths(violations));
    }

    @Test
    void rejectsANameLongerThanTheStoredColumn() {
        // given
        var violations = VALIDATOR.validate(request("x".repeat(256), Resource.CERTIFICATE, column("commonName")));

        // then
        assertEquals(Set.of("name"), paths(violations));
    }

    @Test
    void acceptsANameAtTheStoredColumnLength() {
        assertTrue(VALIDATOR.validate(request("x".repeat(255), Resource.CERTIFICATE, column("commonName"))).isEmpty());
    }

    @Test
    void rejectsAViewWithoutAResource() {
        // given — columns are resolved against one resource's field catalogue, so the resource is not optional
        var violations = VALIDATOR.validate(request("Expiry watch", null, column("commonName")));

        // then
        assertEquals(Set.of("resource"), paths(violations));
    }

    @Test
    void rejectsAViewWithNoColumns() {
        // given — a view that selects nothing would render an empty table
        var dto = request("Expiry watch", Resource.CERTIFICATE);

        // when
        var violations = VALIDATOR.validate(dto);

        // then
        assertEquals(Set.of("columns"), paths(violations));
    }

    @Test
    void validatesThroughIntoEachColumn() {
        // given — a column naming no field cannot be resolved against the catalogue
        var violations = VALIDATOR
                .validate(request("Expiry watch", Resource.CERTIFICATE,
                        new ListViewColumnDto(FilterFieldSource.PROPERTY, null, null)));

        // then
        assertEquals(Set.of("columns[0].fieldIdentifier"), paths(violations));
    }

    @Test
    void carriesTheResourceOnTopOfTheUpdatableFields() throws Exception {
        // given — create is the update shape plus the resource, which is fixed for the view's whole life
        var dto = request("Expiry watch", Resource.CERTIFICATE, column("commonName"));

        // when
        var back = mapper.readValue(mapper.writeValueAsString(dto), ListViewRequestDto.class);

        // then
        assertEquals(Resource.CERTIFICATE, back.getResource());
        assertEquals("Expiry watch", back.getName());
        assertEquals("commonName", back.getColumns().get(0).getFieldIdentifier());
    }

    private static ListViewRequestDto request(String name, Resource resource, ListViewColumnDto... columns) {
        var dto = new ListViewRequestDto();
        dto.setName(name);
        dto.setResource(resource);
        dto.setColumns(List.of(columns));
        return dto;
    }

    private static ListViewColumnDto column(String fieldIdentifier) {
        return new ListViewColumnDto(FilterFieldSource.PROPERTY, fieldIdentifier, null);
    }

    private static Set<String> paths(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }
}
