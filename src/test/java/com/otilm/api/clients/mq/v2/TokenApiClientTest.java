package com.otilm.api.clients.mq.v2;

import com.otilm.api.exception.ConnectorException;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.cryptography.v2.token.TokenScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.token.TokenStatusResponseV2Dto;
import com.otilm.api.model.core.connector.ConnectorDto;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static com.otilm.api.clients.mq.v2.RecordingProxyClient.aRecordingProxyClient;
import static org.junit.jupiter.api.Assertions.*;

class TokenApiClientTest {

    private RecordingProxyClient proxy;
    private TokenApiClient client;
    private ConnectorDto connector;

    @BeforeEach
    void setUp() {
        proxy = aRecordingProxyClient();
        client = new TokenApiClient(proxy);
        connector = new ConnectorDto();
    }

    @Test
    void listTokenAttributes_returnsConnectorResponse() throws ConnectorException {
        // given
        proxy.respondWith(new BaseAttribute[0]);

        // when
        var result = client.listTokenAttributes(connector);

        // then
        assertTrue(result.isEmpty());
        proxy.assertCall(connector, "/v2/cryptographyProvider/tokens/attributes",
                "GET", null, BaseAttribute[].class);
    }

    @Test
    void getTokenStatus_returnsConnectorResponse() throws ConnectorException {
        // given
        var request = new TokenScopedRequestV2Dto();
        var expectedResponse = new TokenStatusResponseV2Dto();
        proxy.respondWith(expectedResponse);

        // when
        var result = client.getTokenStatus(connector, request);

        // then
        assertSame(expectedResponse, result);
        proxy.assertCall(connector, "/v2/cryptographyProvider/tokens/status",
                "POST", request, TokenStatusResponseV2Dto.class);
    }

    @Test
    void listTokenProfileAttributes_returnsConnectorResponse() throws ConnectorException {
        // given
        var request = new TokenScopedRequestV2Dto();
        proxy.respondWith(new BaseAttribute[0]);

        // when
        var result = client.listTokenProfileAttributes(connector, request);

        // then
        assertTrue(result.isEmpty());
        proxy.assertCall(connector, "/v2/cryptographyProvider/tokens/tokenProfile/attributes",
                "POST", request, BaseAttribute[].class);
    }

    @Test
    void listTokenProfileKeyUsages_returnsConnectorResponse() throws ConnectorException {
        // given
        var request = new TokenScopedRequestV2Dto();
        var expectedKeyUsages = List.of(KeyUsage.SIGN);
        proxy.respondWith(expectedKeyUsages.toArray(KeyUsage[]::new));

        // when
        var result = client.listTokenProfileKeyUsages(connector, request);

        // then
        assertEquals(expectedKeyUsages, result);
        proxy.assertCall(connector, "/v2/cryptographyProvider/tokens/tokenProfile/keyUsages",
                "POST", request, KeyUsage[].class);
    }

    @Test
    void getTokenStatus_removesSecretBearingCause_fromConnectorException() {
        // given
        var secret = "expanded-pin-1234";
        proxy.failWith(new IllegalStateException(secret));

        // when
        Executable getTokenStatus = () -> client.getTokenStatus(
                connector, new TokenScopedRequestV2Dto());

        // then
        var exception = assertThrows(ConnectorException.class, getTokenStatus);
        assertFalse(exception.getMessage().contains(secret));
        assertNull(exception.getCause());
    }
}
