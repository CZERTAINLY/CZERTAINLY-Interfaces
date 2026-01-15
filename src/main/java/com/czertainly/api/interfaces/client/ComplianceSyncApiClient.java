package com.czertainly.api.interfaces.client;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.connector.compliance.ComplianceGroupsResponseDto;
import com.czertainly.api.model.connector.compliance.ComplianceRequestDto;
import com.czertainly.api.model.connector.compliance.ComplianceResponseDto;
import com.czertainly.api.model.connector.compliance.ComplianceRulesResponseDto;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.List;

/**
 * Sync interface for v1 Compliance API client operations.
 * This interface is implemented by both REST and MQ clients.
 */
public interface ComplianceSyncApiClient {

    List<ComplianceRulesResponseDto> getComplianceRules(ConnectorDto connector, String kind, List<String> certificateType) throws ConnectorException;

    List<ComplianceGroupsResponseDto> getComplianceGroups(ConnectorDto connector, String kind) throws ConnectorException;

    List<ComplianceRulesResponseDto> getComplianceGroupRules(ConnectorDto connector, String kind, String uuid) throws ConnectorException;

    ComplianceResponseDto checkCompliance(ConnectorDto connector, String kind, ComplianceRequestDto requestDto) throws ConnectorException;
}
