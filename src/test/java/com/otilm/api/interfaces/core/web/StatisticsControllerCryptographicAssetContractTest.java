package com.otilm.api.interfaces.core.web;

import com.otilm.api.model.client.dashboard.CryptographicAssetStatisticsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;

import static com.otilm.api.testsupport.OpenApiProseAssertions.assertNoJargon;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pins the cryptographic asset statistics operation against its annotation values. Only the new member is asserted; the
 * pre-existing statistics endpoints are left alone so an unrelated addition does not fail this test.
 */
class StatisticsControllerCryptographicAssetContractTest {

    private static Method statisticsMethod() {
        return Arrays
                .stream(StatisticsController.class.getDeclaredMethods())
                .filter(m -> m.getName().equals("getCryptographicAssetStatistics"))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "StatisticsController declares no getCryptographicAssetStatistics method"));
    }

    @Test
    void theStatisticsOperationIsAGetUnderTheStatisticsPath() {
        Method method = statisticsMethod();
        GetMapping mapping = method.getAnnotation(GetMapping.class);
        assertNotNull(mapping, "asset statistics must be a GET");
        assertArrayEquals(new String[]{"/cryptoAssets"}, mapping.path());
        assertArrayEquals(new String[]{"application/json"}, mapping.produces());
        assertEquals(CryptographicAssetStatisticsDto.class, method.getReturnType());
        assertEquals(0, method.getParameterCount());
    }

    @Test
    void theOperationIdMatchesTheSiblingConvention() {
        Operation operation = statisticsMethod().getAnnotation(Operation.class);
        assertNotNull(operation, "missing @Operation");
        assertEquals("getCryptographicAssetStatistics", operation.operationId());
    }

    @Test
    void theOperationProseIsDocumentedAndJargonFree() {
        Method method = statisticsMethod();
        Operation operation = method.getAnnotation(Operation.class);
        assertNotNull(operation, "missing @Operation");
        assertFalse(operation.summary().isBlank(), "blank @Operation summary");
        assertFalse(operation.description().isBlank(), "blank @Operation description");
        assertNoJargon("getCryptographicAssetStatistics summary", operation.summary());
        assertNoJargon("getCryptographicAssetStatistics description", operation.description());

        ApiResponses responses = method.getAnnotation(ApiResponses.class);
        assertNotNull(responses, "missing @ApiResponses");
        for (ApiResponse response : responses.value()) {
            assertFalse(response.description().isBlank(), "blank response description");
            assertNoJargon("getCryptographicAssetStatistics " + response.responseCode(), response.description());
        }
    }
}
