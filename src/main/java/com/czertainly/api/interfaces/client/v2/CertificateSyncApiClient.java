package com.czertainly.api.interfaces.client.v2;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.connector.v2.*;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.List;

/**
 * Sync interface for v2 Certificate API client operations.
 * This interface is implemented by both REST and MQ clients.
 */
public interface CertificateSyncApiClient {

    List<BaseAttribute> listIssueCertificateAttributes(ConnectorDto connector, String authorityUuid) throws ConnectorException;

    Boolean validateIssueCertificateAttributes(ConnectorDto connector, String authorityUuid, List<RequestAttribute> attributes) throws ValidationException, ConnectorException;

    CertificateDataResponseDto issueCertificate(ConnectorDto connector, String authorityUuid, CertificateSignRequestDto requestDto) throws ConnectorException;

    CertificateDataResponseDto renewCertificate(ConnectorDto connector, String authorityUuid, CertificateRenewRequestDto requestDto) throws ConnectorException;

    List<BaseAttribute> listRevokeCertificateAttributes(ConnectorDto connector, String authorityUuid) throws ConnectorException;

    Boolean validateRevokeCertificateAttributes(ConnectorDto connector, String authorityUuid, List<RequestAttribute> attributes) throws ValidationException, ConnectorException;

    void revokeCertificate(ConnectorDto connector, String authorityUuid, CertRevocationDto requestDto) throws ConnectorException;

    CertificateIdentificationResponseDto identifyCertificate(ConnectorDto connector, String authorityUuid, CertificateIdentificationRequestDto requestDto) throws ValidationException, ConnectorException;
}
