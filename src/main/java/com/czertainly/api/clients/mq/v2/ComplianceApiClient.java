package com.czertainly.api.clients.mq.v2;

import com.czertainly.api.clients.mq.ProxyClient;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.interfaces.client.v2.ComplianceSyncApiClient;
import com.czertainly.api.model.connector.compliance.v2.*;
import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * MQ-based implementation of v2 Compliance API client.
 */
public class ComplianceApiClient implements ComplianceSyncApiClient {

    private static final String BASE_PATH = "/v2/complianceProvider";
    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";

    private final ProxyClient proxyClient;

    public ComplianceApiClient(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }

    @Override
    public List<ComplianceRuleResponseDto> getComplianceRules(ConnectorDto connector, String kind, Resource resource, String type, String format) throws ConnectorException {
        StringBuilder pathBuilder = new StringBuilder(BASE_PATH).append("/").append(kind).append("/rules");
        boolean hasQuery = false;
        if (resource != null) {
            pathBuilder.append("?resource=").append(resource);
            hasQuery = true;
        }
        if (type != null) {
            pathBuilder.append(hasQuery ? "&" : "?").append("type=").append(type);
            hasQuery = true;
        }
        if (format != null) {
            pathBuilder.append(hasQuery ? "&" : "?").append("format=").append(format);
        }
        ComplianceRuleResponseDto[] result = proxyClient.sendRequest(connector, pathBuilder.toString(), HTTP_METHOD_GET, null, ComplianceRuleResponseDto[].class);
        return Arrays.asList(result);
    }

    @Override
    public ComplianceRuleResponseDto getComplianceRule(ConnectorDto connector, String kind, UUID ruleUuid) throws ConnectorException {
        String path = BASE_PATH + "/" + kind + "/rules/" + ruleUuid;
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, ComplianceRuleResponseDto.class);
    }

    @Override
    public ComplianceRulesBatchResponseDto getComplianceRulesBatch(ConnectorDto connector, String kind, ComplianceRulesBatchRequestDto requestDto) throws ConnectorException {
        String path = BASE_PATH + "/" + kind + "/rules";
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, requestDto, ComplianceRulesBatchResponseDto.class);
    }

    @Override
    public List<ComplianceGroupResponseDto> getComplianceGroups(ConnectorDto connector, String kind, Resource resource) throws ConnectorException {
        StringBuilder pathBuilder = new StringBuilder(BASE_PATH).append("/").append(kind).append("/groups");
        if (resource != null) {
            pathBuilder.append("?resource=").append(resource);
        }
        ComplianceGroupResponseDto[] result = proxyClient.sendRequest(connector, pathBuilder.toString(), HTTP_METHOD_GET, null, ComplianceGroupResponseDto[].class);
        return Arrays.asList(result);
    }

    @Override
    public ComplianceGroupResponseDto getComplianceGroup(ConnectorDto connector, String kind, UUID groupUuid) throws ConnectorException {
        String path = BASE_PATH + "/" + kind + "/groups/" + groupUuid;
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, ComplianceGroupResponseDto.class);
    }

    @Override
    public List<ComplianceRuleResponseDto> getComplianceGroupRules(ConnectorDto connector, String kind, UUID groupUuid) throws ConnectorException {
        String path = BASE_PATH + "/" + kind + "/groups/" + groupUuid + "/rules";
        ComplianceRuleResponseDto[] result = proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, ComplianceRuleResponseDto[].class);
        return Arrays.asList(result);
    }

    @Override
    public ComplianceResponseDto checkCompliance(ConnectorDto connector, String kind, ComplianceRequestDto requestDto) throws ConnectorException {
        String path = BASE_PATH + "/" + kind + "/compliance";
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, requestDto, ComplianceResponseDto.class);
    }

    // Async variants
    public CompletableFuture<ComplianceResponseDto> checkComplianceAsync(ConnectorDto connector, String kind, ComplianceRequestDto requestDto) {
        String path = BASE_PATH + "/" + kind + "/compliance";
        return proxyClient.sendRequestAsync(connector, path, HTTP_METHOD_POST, requestDto, ComplianceResponseDto.class);
    }

    public CompletableFuture<ComplianceRulesBatchResponseDto> getComplianceRulesBatchAsync(ConnectorDto connector, String kind, ComplianceRulesBatchRequestDto requestDto) {
        String path = BASE_PATH + "/" + kind + "/rules";
        return proxyClient.sendRequestAsync(connector, path, HTTP_METHOD_POST, requestDto, ComplianceRulesBatchResponseDto.class);
    }
}
