package com.czertainly.api;

import com.czertainly.api.exception.ConnectorClientException;
import com.czertainly.api.exception.ConnectorCommunicationException;
import com.czertainly.api.exception.ConnectorServerException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.connector.ConnectorDto;
import com.czertainly.api.model.connector.FunctionGroupCode;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.net.ConnectException;
import java.net.UnknownHostException;

public class ApiClientTest {

    private AttributeApiClient attributeApiClient;

    private WireMockServer mockServer;

    @BeforeEach
    public void setUp() {
        attributeApiClient = new AttributeApiClient(BaseApiClient.prepareWebClient());

        mockServer = new WireMockServer(3665);
        mockServer.start();

        WireMock.configureFor("localhost", mockServer.port());
    }

    @AfterEach
    public void tearDown() {
        mockServer.stop();
    }

    @Test
    public void testGetAttributes_unknownHost() {
        ConnectorDto connector = new ConnectorDto();
        connector.setUrl("wrong-host:1234");

        Throwable cause = Assertions.assertThrows(ConnectorCommunicationException.class, () ->
            // tested method
            attributeApiClient.listAttributeDefinitions(
                    connector,
                    FunctionGroupCode.CREDENTIAL_PROVIDER,
                    "certificate")
        );

        Assertions.assertEquals(UnknownHostException.class, cause.getCause().getClass());
    }

    @Test
    public void testGetAttributes_connectionRefused() {
        ConnectorDto connector = new ConnectorDto();
        connector.setUrl("localhost:1234");

        Throwable cause = Assertions.assertThrows(ConnectorCommunicationException.class, () ->
                // tested method
                attributeApiClient.listAttributeDefinitions(
                        connector,
                        FunctionGroupCode.CREDENTIAL_PROVIDER,
                        "certificate")
        );

        Assertions.assertEquals(ConnectException.class, cause.getCause().getCause().getClass());
    }

    @Test
    public void testGetAttributes_clientNotFoundError() {
        String bodyString = new NotFoundException("Attribute", 1).getMessage();

        mockServer.stubFor(WireMock.get("/v1/credentialProvider/certificate/attributes")
                .willReturn(WireMock
                        .aResponse()
                        .withStatus(404)
                        .withBody(bodyString)));

        ConnectorDto connector = new ConnectorDto();
        connector.setUrl("localhost:3665");

        NotFoundException cause = Assertions.assertThrows(NotFoundException.class, () ->
                // tested method
                attributeApiClient.listAttributeDefinitions(
                        connector,
                        FunctionGroupCode.CREDENTIAL_PROVIDER,
                        "certificate")
        );

        Assertions.assertEquals(bodyString, cause.getMessage());
        Assertions.assertEquals(connector, cause.getConnector());
    }

    @Test
    public void testGetAttributes_clientError() {
        String bodyString = "Bad client request";

        mockServer.stubFor(WireMock.get("/v1/credentialProvider/certificate/attributes")
                .willReturn(WireMock
                        .aResponse()
                        .withStatus(400)
                        .withBody(bodyString)));

        ConnectorDto connector = new ConnectorDto();
        connector.setUrl("localhost:3665");

        ConnectorClientException cause = Assertions.assertThrows(ConnectorClientException.class, () ->
                // tested method
                attributeApiClient.listAttributeDefinitions(
                        connector,
                        FunctionGroupCode.CREDENTIAL_PROVIDER,
                        "certificate")
        );

        Assertions.assertEquals(bodyString, cause.getMessage());
        Assertions.assertEquals(connector, cause.getConnector());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, cause.getHttpStatus());
    }

    @Test
    public void testGetAttributes_serverError() {
        String bodyString = "Internal server error";

        mockServer.stubFor(WireMock.get("/v1/credentialProvider/certificate/attributes")
                .willReturn(WireMock
                        .aResponse()
                        .withStatus(500)
                        .withBody(bodyString)));

        ConnectorDto connector = new ConnectorDto();
        connector.setUrl("localhost:3665");

        ConnectorServerException cause = Assertions.assertThrows(ConnectorServerException.class, () ->
                // tested method
                attributeApiClient.listAttributeDefinitions(
                        connector,
                        FunctionGroupCode.CREDENTIAL_PROVIDER,
                        "certificate")
        );

        Assertions.assertEquals(bodyString, cause.getMessage());
        Assertions.assertEquals(connector, cause.getConnector());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cause.getHttpStatus());
    }
}
