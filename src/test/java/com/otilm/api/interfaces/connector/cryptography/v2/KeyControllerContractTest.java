package com.otilm.api.interfaces.connector.cryptography.v2;

import com.otilm.api.model.common.error.ProblemDetailExtended;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyControllerContractTest {

    @Test
    void createKey_declaresProblemDetailResponse_forConflict() throws NoSuchMethodException {
        // given
        String conflictStatusCode = "409";
        Method createKey = KeyController.class.getMethod("createKey", CreateKeyRequestV2Dto.class);

        // when
        ApiResponse conflictResponse = Arrays
                .stream(createKey.getAnnotation(ApiResponses.class).value())
                .filter(response -> conflictStatusCode.equals(response.responseCode()))
                .findFirst()
                .orElseThrow();

        // then
        assertEquals(1, conflictResponse.content().length);
        assertEquals(MediaType.APPLICATION_PROBLEM_JSON_VALUE, conflictResponse.content()[0].mediaType());
        assertEquals(ProblemDetailExtended.class, conflictResponse.content()[0].schema().implementation());
    }
}
