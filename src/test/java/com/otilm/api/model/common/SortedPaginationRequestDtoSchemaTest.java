package com.otilm.api.model.common;

import io.swagger.v3.oas.models.media.Schema;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static com.otilm.api.testsupport.OpenApiSchemaTestSupport.openApi31Schemas;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SortedPaginationRequestDtoSchemaTest {

    @Test
    void sortedPaginationSchema_carriesSortDirectionAlongsideInheritedPagingProperties() {
        Map<String, Schema> schemas = openApi31Schemas(SortedPaginationRequestDto.class);
        Schema<?> request = schemas.get("SortedPaginationRequestDto");
        assertNotNull(request, "the paging properties must be generated from the request object's getters");
        assertEquals(Set.of("itemsPerPage", "pageNumber", "sortDirection"), request.getProperties().keySet());
    }

    @Test
    void sortDirectionProperty_defaultsToTheAscendingCodeRatherThanTheEnumConstant() {
        Map<String, Schema> schemas = openApi31Schemas(SortedPaginationRequestDto.class);
        Schema<?> sortDirection = (Schema<?>) schemas
                .get("SortedPaginationRequestDto")
                .getProperties()
                .get("sortDirection");
        assertEquals("asc", sortDirection.getDefault());
    }
}
