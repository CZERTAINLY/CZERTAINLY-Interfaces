package com.czertainly.api.clients.v2;

import com.czertainly.api.clients.BaseApiClient;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.connector.compliance.ComplianceResponseDto;
import com.czertainly.api.model.connector.compliance.v2.*;
import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.net.ssl.TrustManager;
import java.net.URI;
import java.util.List;
import java.util.UUID;

public class ComplianceApiClient extends BaseApiClient {

    private static final String COMPLIANCE_BASE_CONTEXT = "/v2/complianceProvider/{kind}";
    private static final String COMPLIANCE_RULE_GET_CONTEXT = COMPLIANCE_BASE_CONTEXT + "/rules";
    private static final String COMPLIANCE_GROUP_GET_CONTEXT = COMPLIANCE_BASE_CONTEXT + "/groups";
    private static final String COMPLIANCE_GROUP_RULE_CONTEXT = COMPLIANCE_BASE_CONTEXT + "/groups/{uuid}";
    private static final String COMPLIANCE_CONTEXT = COMPLIANCE_BASE_CONTEXT + "/compliance";

    private static final String RESOURCE_QUERY_HEADER = "resource";
    private static final String RESOURCE_TYPE_QUERY_HEADER = "type";
    private static final String RESOURCE_FORMAT_QUERY_HEADER = "format";

    public ComplianceApiClient(WebClient webClient, TrustManager[] defaultTrustManagers) {
        this.webClient = webClient;
        this.defaultTrustManagers = defaultTrustManagers;
    }

    public List<ComplianceRuleResponseDto> getComplianceRules(ConnectorDto connector, String kind, Resource resource, String type, String format) throws ConnectorException {
        URI uri;
        UriBuilder uriBuilder = UriComponentsBuilder.fromUriString(connector.getUrl());
        uriBuilder.path(COMPLIANCE_RULE_GET_CONTEXT.replace("{kind}", kind));

        if (resource != null) {
            uriBuilder.queryParam(RESOURCE_QUERY_HEADER, resource);
        }
        if (type != null) {
            uriBuilder.queryParam(RESOURCE_TYPE_QUERY_HEADER, type);
        }
        if (format != null) {
            uriBuilder.queryParam(RESOURCE_FORMAT_QUERY_HEADER, format);
        }
        uri = uriBuilder.build();
        WebClient.RequestBodySpec request = prepareRequest(HttpMethod.GET, connector, true).uri(uri);

        return processRequest(r -> r
                        .retrieve()
                        .toEntityList(ComplianceRuleResponseDto.class)
                        .block().getBody(),
                request,
                connector);
    }

    public ComplianceRulesBatchResponseDto getComplianceRulesBatch(ConnectorDto connector, String kind, ComplianceRulesBatchRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                        .uri(connector.getUrl() + COMPLIANCE_RULE_GET_CONTEXT, kind)
                        .body(Mono.just(requestDto), ComplianceRulesBatchRequestDto.class)
                        .retrieve()
                        .toEntity(ComplianceRulesBatchResponseDto.class)
                        .block().getBody(),
                request,
                connector);
    }

    public List<ComplianceGroupResponseDto> getComplianceGroups(ConnectorDto connector, String kind, Resource resource) throws ConnectorException {
        URI uri;
        UriBuilder uriBuilder = UriComponentsBuilder.fromUriString(connector.getUrl());
        uriBuilder.path(COMPLIANCE_RULE_GET_CONTEXT.replace("{kind}", kind));

        if (resource != null) {
            uriBuilder.queryParam(RESOURCE_QUERY_HEADER, resource);
        }
        uri = uriBuilder.build();
        WebClient.RequestBodySpec request = prepareRequest(HttpMethod.GET, connector, true).uri(uri);
        return processRequest(r -> r
                        .retrieve()
                        .toEntityList(ComplianceGroupResponseDto.class)
                        .block().getBody(),
                request,
                connector);
    }


    public List<ComplianceRuleResponseDto> getComplianceGroupRules(ConnectorDto connector, String kind, UUID uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                        .uri(connector.getUrl() + COMPLIANCE_GROUP_RULE_CONTEXT, kind, uuid)
                        .retrieve()
                        .toEntityList(ComplianceRuleResponseDto.class)
                        .block().getBody(),
                request,
                connector);
    }

    public ComplianceResponseDto checkCompliance(ConnectorDto connector, String kind, ComplianceRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                        .uri(connector.getUrl() + COMPLIANCE_CONTEXT, kind)
                        .body(Mono.just(requestDto), ComplianceRequestDto.class)
                        .retrieve()
                        .toEntity(ComplianceResponseDto.class)
                        .block().getBody(),
                request,
                connector);
    }
}
