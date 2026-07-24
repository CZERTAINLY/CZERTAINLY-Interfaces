package com.otilm.api.clients.testutils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.otilm.api.model.core.connector.ConnectorDto;
import com.otilm.api.model.core.connector.ConnectorStatus;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public final class ConnectorWireMockServer implements AutoCloseable {

    private final WireMockServer server;

    private ConnectorWireMockServer() {
        server = new WireMockServer(options().dynamicPort());
        server.start();
    }

    public static ConnectorWireMockServer aConnectorWireMockServer() {
        return new ConnectorWireMockServer();
    }

    public ConnectorDto connectedConnector() {
        var connector = new ConnectorDto();
        connector.setUrl("http://localhost:" + server.port());
        connector.setStatus(ConnectorStatus.CONNECTED);
        return connector;
    }

    public void stubGetJson(String path, String responseBody) {
        server.stubFor(WireMock.get(path).willReturn(WireMock.okJson(responseBody)));
    }

    public void stubPostJson(String path, String responseBody) {
        server.stubFor(WireMock.post(path).willReturn(WireMock.okJson(responseBody)));
    }

    public void stubPostJson(String path, int status, String contentType, String responseBody) {
        server.stubFor(WireMock.post(path)
                .willReturn(WireMock.aResponse()
                        .withStatus(status)
                        .withHeader("Content-Type", contentType)
                        .withBody(responseBody)));
    }

    public void verifyPostBody(String path, String jsonPath, String expectedValue) {
        server.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo(path))
                .withRequestBody(WireMock.matchingJsonPath(jsonPath, WireMock.equalTo(expectedValue))));
    }

    @Override
    public void close() {
        server.stop();
    }
}
