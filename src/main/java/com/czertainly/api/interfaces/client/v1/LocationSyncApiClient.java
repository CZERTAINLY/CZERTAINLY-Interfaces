package com.czertainly.api.interfaces.client.v1;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.connector.entity.GenerateCsrRequestDto;
import com.czertainly.api.model.connector.entity.GenerateCsrResponseDto;
import com.czertainly.api.model.connector.entity.LocationDetailRequestDto;
import com.czertainly.api.model.connector.entity.LocationDetailResponseDto;
import com.czertainly.api.model.connector.entity.PushCertificateRequestDto;
import com.czertainly.api.model.connector.entity.PushCertificateResponseDto;
import com.czertainly.api.model.connector.entity.RemoveCertificateRequestDto;
import com.czertainly.api.model.connector.entity.RemoveCertificateResponseDto;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.List;

/**
 * Synchronous API client interface for Location operations.
 * Implementations include REST-based and MQ-based clients.
 */
public interface LocationSyncApiClient {

    LocationDetailResponseDto getLocationDetail(ConnectorDto connector, String entityUuid, LocationDetailRequestDto requestDto) throws ConnectorException;

    PushCertificateResponseDto pushCertificateToLocation(ConnectorDto connector, String entityUuid, PushCertificateRequestDto requestDto) throws ConnectorException;

    List<BaseAttribute> listPushCertificateAttributes(ConnectorDto connector, String entityUuid) throws ConnectorException;

    void validatePushCertificateAttributes(ConnectorDto connector, String entityUuid, List<RequestAttribute> pushAttributes) throws ConnectorException;

    RemoveCertificateResponseDto removeCertificateFromLocation(ConnectorDto connector, String entityUuid, RemoveCertificateRequestDto requestDto) throws ConnectorException;

    GenerateCsrResponseDto generateCsrLocation(ConnectorDto connector, String entityUuid, GenerateCsrRequestDto requestDto) throws ConnectorException;

    List<BaseAttribute> listGenerateCsrAttributes(ConnectorDto connector, String entityUuid) throws ConnectorException;

    void validateGenerateCsrAttributes(ConnectorDto connector, String entityUuid, List<RequestAttribute> pushAttributes) throws ConnectorException;
}
