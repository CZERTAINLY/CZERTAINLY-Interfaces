package com.czertainly.api.interfaces.client;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.key.CreateKeyRequestDto;
import com.czertainly.api.model.connector.cryptography.key.KeyDataResponseDto;
import com.czertainly.api.model.connector.cryptography.key.KeyPairDataResponseDto;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.List;

public interface KeyManagementSyncApiClient {
    List<BaseAttribute> listCreateSecretKeyAttributes(ConnectorDto connector, String uuid) throws ConnectorException;
    void validateCreateSecretKeyAttributes(ConnectorDto connector, String uuid, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException;
    KeyDataResponseDto createSecretKey(ConnectorDto connector, String uuid, CreateKeyRequestDto requestDto) throws ConnectorException;
    List<BaseAttribute> listCreateKeyPairAttributes(ConnectorDto connector, String uuid) throws ConnectorException;
    void validateCreateKeyPairAttributes(ConnectorDto connector, String uuid, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException;
    KeyPairDataResponseDto createKeyPair(ConnectorDto connector, String uuid, CreateKeyRequestDto requestDto) throws ConnectorException;
    List<KeyDataResponseDto> listKeys(ConnectorDto connector, String uuid) throws ConnectorException;
    KeyDataResponseDto getKey(ConnectorDto connector, String uuid, String keyUuid) throws ConnectorException;
    void destroyKey(ConnectorDto connector, String uuid, String keyUuid) throws ConnectorException;
}
