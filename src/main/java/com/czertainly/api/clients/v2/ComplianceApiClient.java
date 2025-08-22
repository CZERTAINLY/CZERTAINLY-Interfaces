package com.czertainly.api.clients.v2;

import com.czertainly.api.clients.BaseApiClient;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.connector.compliance.ComplianceResponseDto;
import com.czertainly.api.model.connector.compliance.v2.ComplianceGroupResponseDto;
import com.czertainly.api.model.connector.compliance.v2.ComplianceRequestDto;
import com.czertainly.api.model.connector.compliance.v2.ComplianceRuleResponseDto;
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

    private static final String CERTIFICATE_TYPE_QUERY_HEADER = "certificateType";

    public ComplianceApiClient(WebClient webClient, TrustManager[] defaultTrustManagers) {
        this.webClient = webClient;
        this.defaultTrustManagers = defaultTrustManagers;
    }


    public List<ComplianceRuleResponseDto> getComplianceRules(ConnectorDto connector, String kind, List<String> certificateType) throws ConnectorException {
        URI uri;
        UriBuilder uriBuilder = UriComponentsBuilder.fromUriString(connector.getUrl());
        uriBuilder.path(COMPLIANCE_RULE_GET_CONTEXT.replace("{kind}", kind));

        if(certificateType != null && !certificateType.isEmpty()){
            certificateType.stream()
                    .filter(q -> q != null)
                    .forEach(q -> uriBuilder.queryParam(CERTIFICATE_TYPE_QUERY_HEADER, q));
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


    public List<ComplianceGroupResponseDto> getComplianceGroups(ConnectorDto connector, String kind) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                        .uri(connector.getUrl() + COMPLIANCE_GROUP_GET_CONTEXT, kind)
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
