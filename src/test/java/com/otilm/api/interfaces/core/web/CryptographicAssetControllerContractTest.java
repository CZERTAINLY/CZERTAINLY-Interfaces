package com.otilm.api.interfaces.core.web;

import com.otilm.api.exception.NotFoundException;
import com.otilm.api.model.client.certificate.SearchRequestDto;
import com.otilm.api.model.common.PaginationResponseDto;
import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.cryptoasset.CryptographicAssetDetailDto;
import com.otilm.api.model.core.cryptoasset.CryptographicAssetDto;
import com.otilm.api.model.core.search.SearchFieldDataByGroupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the cryptographic asset inventory endpoints against their annotation values rather than prose. The frontend's
 * generated client calls these paths and Core implements them, so a change to either has to fail a build.
 */
class CryptographicAssetControllerContractTest {

    private static Method method(String name) {
        return Arrays
                .stream(CryptographicAssetController.class.getDeclaredMethods())
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError("CryptographicAssetController declares no method named " + name));
    }

    @Test
    void theControllerLivesUnderTheCryptoAssetsPath() {
        RequestMapping mapping = CryptographicAssetController.class.getAnnotation(RequestMapping.class);
        assertNotNull(mapping, "missing @RequestMapping");
        assertArrayEquals(new String[]{"/v1/cryptoAssets"}, mapping.value());

        Tag tag = CryptographicAssetController.class.getAnnotation(Tag.class);
        assertNotNull(tag, "missing @Tag");
        assertEquals("Cryptographic Asset Inventory", tag.name());
    }

    @Test
    void theListIsAPostOfTheCanonicalSearchRequest() {
        Method list = method("listCryptographicAssets");
        PostMapping mapping = list.getAnnotation(PostMapping.class);
        assertNotNull(mapping, "list must be a POST");
        assertEquals(0, mapping.path().length, "list lives at the controller root");
        assertArrayEquals(new String[]{MediaType.APPLICATION_JSON_VALUE}, mapping.consumes());
        assertArrayEquals(new String[]{MediaType.APPLICATION_JSON_VALUE}, mapping.produces());

        assertEquals(1, list.getParameterCount());
        assertEquals(SearchRequestDto.class, list.getParameters()[0].getType());
        assertNotNull(list.getParameters()[0].getAnnotation(RequestBody.class), "search request must be the body");
        assertNotNull(list.getParameters()[0].getAnnotation(Valid.class),
                "search request must be validated — sort and column members carry constraints");

        assertEquals(PaginationResponseDto.class, list.getReturnType());
        ParameterizedType returnType = (ParameterizedType) list.getGenericReturnType();
        assertEquals(CryptographicAssetDto.class, returnType.getActualTypeArguments()[0],
                "list rows must be CryptographicAssetDto");
    }

    /**
     * The default sort order is contract, not prose decoration: consumers page the inventory and Core implements the
     * ordering, so the sentence documenting it has to stay until the ordering itself changes. A client-supplied sort
     * (the canonical request's sort member) reorders the result set; this pins what happens when none is supplied.
     */
    @Test
    void theListDocumentsTheDefaultSortOrder() {
        Operation operation = method("listCryptographicAssets").getAnnotation(Operation.class);
        assertNotNull(operation, "missing @Operation on the list");
        assertTrue(operation.description().contains("ordered by name ascending, then UUID ascending"),
                "the list description must document the default sort order (name ASC, uuid ASC)");
    }

    @Test
    void theDetailIsAGetByUuid() {
        Method detail = method("getCryptographicAsset");
        GetMapping mapping = detail.getAnnotation(GetMapping.class);
        assertNotNull(mapping, "detail must be a GET");
        assertArrayEquals(new String[]{"/{uuid}"}, mapping.path());
        assertEquals(CryptographicAssetDetailDto.class, detail.getReturnType());
        assertEquals(UUID.class, detail.getParameters()[0].getType());
        assertNotNull(detail.getParameters()[0].getAnnotation(PathVariable.class), "uuid must be a path variable");
        assertTrue(Arrays.asList(detail.getExceptionTypes()).contains(NotFoundException.class),
                "detail must declare NotFoundException");
    }

    @Test
    void theSearchableFieldsSiblingMatchesTheHousePattern() {
        Method search = method("getSearchableFieldInformation");
        GetMapping mapping = search.getAnnotation(GetMapping.class);
        assertNotNull(mapping, "searchable fields must be a GET");
        assertArrayEquals(new String[]{"/search"}, mapping.path());

        Operation operation = search.getAnnotation(Operation.class);
        assertNotNull(operation, "missing @Operation");
        assertEquals("getCryptographicAssetSearchableFields", operation.operationId());

        assertEquals(List.class, search.getReturnType());
        ParameterizedType returnType = (ParameterizedType) search.getGenericReturnType();
        assertEquals(SearchFieldDataByGroupDto.class, returnType.getActualTypeArguments()[0]);
    }

    @Test
    void theResourceIsRegisteredAsCryptoAssets() {
        assertEquals("cryptoAssets", Resource.CRYPTO_ASSET.getCode());
        assertEquals(Resource.CRYPTO_ASSET, Resource.findByCode("cryptoAssets"));
    }
}
