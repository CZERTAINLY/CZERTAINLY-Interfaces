package com.czertainly.api.clients.secret;

import com.czertainly.api.clients.ApiClientConnectorInfo;
import com.czertainly.api.clients.BaseApiClient;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.TrustManager;
import java.util.List;

public class VaultApiClient extends BaseApiClient {

    private static final String VAULT_BASE_PATH = "/v1/secretProvider/vaults";

    public VaultApiClient(WebClient webClient, TrustManager[] defaultTrustManagers) {
        super(webClient, defaultTrustManagers);
    }

    public void checkVaultConnection(ApiClientConnectorInfo connector, List<RequestAttribute> attributes) throws ConnectorException {
        processRequest(
                attrs -> prepareRequest(HttpMethod.POST, connector, true)
                        .uri(connector.getUrl() + VAULT_BASE_PATH)
                        .bodyValue(attrs)
                        .retrieve()
                        .toBodilessEntity()
                        .block(),
                attributes,
                connector
        );
    }

    public List<BaseAttribute> listVaultAttributes(ApiClientConnectorInfo connector) throws ConnectorException {
        return processRequest(
                c -> prepareRequest(HttpMethod.GET, c, true)
                        .uri(c.getUrl() + VAULT_BASE_PATH + "/attributes")
                        .retrieve()
                        .bodyToFlux(BaseAttribute.class)
                        .collectList()
                        .block(),
                connector,
                connector
        );
    }
}
