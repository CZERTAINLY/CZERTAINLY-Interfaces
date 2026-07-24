package com.otilm.api.clients.mq.v2;

import com.otilm.api.clients.mq.ProxyClient;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.model.client.connector.v2.ConnectorInterface;
import com.otilm.api.model.client.connector.v2.attribute.AttributeCallbackRequestDto;
import com.otilm.api.model.client.connector.v2.attribute.AttributeCallbackResponseDto;
import com.otilm.api.model.client.connector.v2.attribute.AttributeDefinitionsDto;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.common.attribute.v3.InfoAttributeV3;
import com.otilm.api.model.core.connector.ConnectorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.otilm.api.clients.mq.v2.RecordingProxyClient.aRecordingProxyClient;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Delegation tests for the MQ-based Attributes v2 client. Verifies each method calls
 * {@link ProxyClient#sendRequest} (or its async variant) with the right path / HTTP method / body /
 * response type and returns the proxy's value.
 *
 * <p>No mocking framework (Mockito etc.) is on this project's test classpath — only JUnit Jupiter and
 * WireMock — so the proxy is a hand-written recording fake.</p>
 *
 * <p><b>Out of scope — AC5 / MQ error parity is a Core concern.</b> The {@link ProxyClient}
 * implementation maps connector errors by raw HTTP status code and never reconstructs a
 * {@code ConnectorProblemException} from the connector's {@code errorCode}, so a connector 404
 * ({@code ATTRIBUTE_DEFINITION_NOT_FOUND}) over MQ loses its error code. That parity fix is tracked as a
 * Core gate (core #1622/#1621), not #726, so this suite does NOT assert error mapping over MQ. Note too that
 * {@code ProxyClient} has no query-string notion — the exploded {@code uuids} string rides in the path
 * (verified below); whether it survives the bus is a separate Core/MQ gate.</p>
 */
class AttributesApiClientMqTest {

    private static final String ATTRIBUTES_PATH = "/v2/attributes";
    private static final String CALLBACK_PATH = ATTRIBUTES_PATH + "/callback";
    private static final String GET = "GET";
    private static final String POST = "POST";

    private RecordingProxyClient proxyClient;
    private AttributesApiClient client;
    private ConnectorDto connector;

    @BeforeEach
    void setUp() {
        proxyClient = aRecordingProxyClient();
        client = new AttributesApiClient(proxyClient);
        connector = new ConnectorDto();
    }

    @Test
    void listDefinitions_returnsFullRegistry_whenUuidsAreNull() throws ConnectorException {
        // given
        var expectedResponse = new AttributeDefinitionsDto();
        proxyClient.respondWith(expectedResponse);

        // when
        var result = client.listDefinitions(connector, null);

        // then
        assertSame(expectedResponse, result);
        proxyClient.assertCall(connector, ATTRIBUTES_PATH, GET, null, AttributeDefinitionsDto.class);
    }

    @Test
    void listDefinitions_usesExplodedUuidQuery_whenUuidsAreProvided() throws ConnectorException {
        // given
        var firstUuid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        var secondUuid = UUID.fromString("22222222-2222-2222-2222-222222222222");
        var expectedPath = ATTRIBUTES_PATH + "?uuids=" + firstUuid + "&uuids=" + secondUuid;
        var expectedResponse = new AttributeDefinitionsDto();
        proxyClient.respondWith(expectedResponse);

        // when
        var result = client.listDefinitions(connector, List.of(firstUuid, secondUuid));

        // then
        assertSame(expectedResponse, result);
        proxyClient.assertCall(connector, expectedPath, GET, null, AttributeDefinitionsDto.class);
    }

    @Test
    void getDefinition_usesDefinitionUuidPath() throws ConnectorException {
        // given
        var definitionUuid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        var expectedPath = ATTRIBUTES_PATH + "/" + definitionUuid;
        BaseAttribute expectedResponse = new InfoAttributeV3();
        proxyClient.respondWith(expectedResponse);

        // when
        var result = client.getDefinition(connector, definitionUuid);

        // then
        assertSame(expectedResponse, result);
        proxyClient.assertCall(connector, expectedPath, GET, null, BaseAttribute.class);
    }

    @Test
    void callback_sendsRequestBody() throws ConnectorException {
        // given
        var request = callbackRequest();
        var expectedResponse = new AttributeCallbackResponseDto();
        proxyClient.respondWith(expectedResponse);

        // when
        var result = client.callback(connector, request);

        // then
        assertSame(expectedResponse, result);
        proxyClient.assertCall(connector, CALLBACK_PATH, POST, request, AttributeCallbackResponseDto.class);
    }

    @Test
    void getDefinitionAsync_usesDefinitionUuidPath() {
        // given
        var definitionUuid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        var expectedPath = ATTRIBUTES_PATH + "/" + definitionUuid;
        BaseAttribute expectedResponse = new InfoAttributeV3();
        proxyClient.respondAsyncWith(expectedResponse);

        // when
        var result = client.getDefinitionAsync(connector, definitionUuid);

        // then
        assertSame(expectedResponse, result.join());
        proxyClient.assertCall(connector, expectedPath, GET, null, BaseAttribute.class);
    }

    @Test
    void listDefinitionsAsync_usesExplodedUuidQuery_whenUuidsAreProvided() {
        // given
        var firstUuid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        var secondUuid = UUID.fromString("22222222-2222-2222-2222-222222222222");
        var expectedPath = ATTRIBUTES_PATH + "?uuids=" + firstUuid + "&uuids=" + secondUuid;
        var expectedResponse = new AttributeDefinitionsDto();
        proxyClient.respondAsyncWith(expectedResponse);

        // when
        var result = client.listDefinitionsAsync(connector, List.of(firstUuid, secondUuid));

        // then
        assertSame(expectedResponse, result.join());
        proxyClient.assertCall(connector, expectedPath, GET, null, AttributeDefinitionsDto.class);
    }

    @Test
    void callbackAsync_sendsRequestBody() {
        // given
        var request = callbackRequest();
        var expectedResponse = new AttributeCallbackResponseDto();
        proxyClient.respondAsyncWith(expectedResponse);

        // when
        var result = client.callbackAsync(connector, request);

        // then
        assertSame(expectedResponse, result.join());
        proxyClient.assertCall(connector, CALLBACK_PATH, POST, request, AttributeCallbackResponseDto.class);
    }

    private static AttributeCallbackRequestDto callbackRequest() {
        var request = new AttributeCallbackRequestDto();
        request.setConnectorInterface(ConnectorInterface.AUTHORITY);
        request.setInterfaceVersion("v3");
        request.setAttributeUuid(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        request.setAttributeName("someAttr");
        request.setContextAttributes(List.of());
        request.setCurrentAttributes(List.of());
        return request;
    }
}
