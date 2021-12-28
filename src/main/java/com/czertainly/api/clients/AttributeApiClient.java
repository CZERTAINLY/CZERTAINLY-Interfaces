package com.czertainly.api.clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.AttributeCallback;
import com.czertainly.api.model.common.AttributeDefinition;
import com.czertainly.api.model.common.RequestAttributeDto;
import com.czertainly.api.model.core.connector.ConnectorDto;
import com.czertainly.api.model.core.connector.FunctionGroupCode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

public class AttributeApiClient extends BaseApiClient {

    private static final String ATTRIBUTE_BASE_CONTEXT = "/v1/{functionGroup}/{kind}/attributes";
    private static final String ATTRIBUTE_VALIDATION_CONTEXT = ATTRIBUTE_BASE_CONTEXT + "/validate";

    private static final ParameterizedTypeReference<List<RequestAttributeDto>> ATTRIBUTE_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public AttributeApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<AttributeDefinition> listAttributeDefinitions(ConnectorDto connector, FunctionGroupCode functionGroupCode, String kind) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + ATTRIBUTE_BASE_CONTEXT, functionGroupCode.getCode(), kind)
                .retrieve()
                .toEntityList(AttributeDefinition.class)
                .block().getBody(),
                request,
                connector);
    }

    public Boolean validateAttributes(ConnectorDto connector, FunctionGroupCode functionGroupCode, List<RequestAttributeDto> attributes, String functionGroupType) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + ATTRIBUTE_VALIDATION_CONTEXT, functionGroupCode.getCode(), functionGroupType)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Boolean.class)
                .block().getBody(),
                request,
                connector);
    }

    public Object attributeCallback(ConnectorDto connector, AttributeCallback callback) throws ConnectorException {
        HttpMethod method = HttpMethod.valueOf(callback.getCallbackMethod());

        URI uri;
        UriBuilder uriBuilder = UriComponentsBuilder.fromUriString(connector.getUrl());
        uriBuilder.path(callback.getCallbackContext());

        if (callback.getQueryParameters() != null) {
            callback.getQueryParameters().entrySet().stream()
                    .filter(q -> q.getValue() != null)
                    .forEach(q -> uriBuilder.queryParam(q.getKey(), q.getValue()));
        }

        if (callback.getPathVariables() != null) {
            uri = uriBuilder.build(callback.getPathVariables());
        } else {
            uri = uriBuilder.build();
        }

        WebClient.RequestBodySpec request =
                prepareRequest(method, connector.getAuthType(), connector.getAuthAttributes())
                        .uri(uri);

        if (callback.getRequestBody() != null) {
            request.bodyValue(callback.getRequestBody());
        }

        return processRequest(r -> r
                .retrieve()
                .toEntity(Object.class)
                .block().getBody(),
                request,
                connector);
    }
}
