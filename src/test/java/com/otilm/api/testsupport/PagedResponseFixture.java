package com.otilm.api.testsupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.common.PaginationResponseDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Shared fixtures for the paged-listing tests. Every listing pages with the same generic envelope, so each of them
 * asserts the same paging contract — and the point of those assertions is that the listings agree with one another,
 * which only holds while they are built from one place.
 */
public final class PagedResponseFixture {

    private PagedResponseFixture() {
    }

    /** A page carrying {@code items}, with paging numbers fixed so two listings can be compared member by member. */
    @SafeVarargs
    public static <T> PaginationResponseDto<T> pageOf(T... items) {
        PaginationResponseDto<T> page = new PaginationResponseDto<>();
        page.setItems(List.of(items));
        page.setItemsPerPage(10);
        page.setPageNumber(0);
        page.setTotalPages(3);
        page.setTotalItems(21L);
        return page;
    }

    /**
     * The envelope's own members, sorted and joined — the array of items dropped, since that is the part each listing
     * is expected to differ on.
     */
    public static String pagingMembersOf(ObjectMapper mapper, Object dto) {
        JsonNode root = mapper.valueToTree(dto);
        List<String> names = new ArrayList<>();
        root.fieldNames().forEachRemaining(names::add);
        return names.stream().filter(name -> !root.get(name).isArray()).sorted().collect(Collectors.joining(","));
    }
}
