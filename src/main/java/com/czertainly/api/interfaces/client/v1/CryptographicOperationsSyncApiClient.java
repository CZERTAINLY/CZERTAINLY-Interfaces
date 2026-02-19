package com.czertainly.api.interfaces.client.v1;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.operations.*;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.List;

public interface CryptographicOperationsSyncApiClient {
    EncryptDataResponseDto encryptData(ConnectorDto connector, String uuid, String keyUuid, CipherDataRequestDto requestDto) throws ConnectorException;
    DecryptDataResponseDto decryptData(ConnectorDto connector, String uuid, String keyUuid, CipherDataRequestDto requestDto) throws ConnectorException;
    SignDataResponseDto signData(ConnectorDto connector, String uuid, String keyUuid, SignDataRequestDto requestDto) throws ConnectorException;
    VerifyDataResponseDto verifyData(ConnectorDto connector, String uuid, String keyUuid, VerifyDataRequestDto requestDto) throws ConnectorException;
    List<BaseAttribute> listRandomAttributes(ConnectorDto connector, String uuid) throws ConnectorException;
    void validateRandomAttributes(ConnectorDto connector, String uuid, List<RequestAttribute> attributes) throws ValidationException, ConnectorException;
    RandomDataResponseDto randomData(ConnectorDto connector, String uuid, RandomDataRequestDto requestDto) throws ConnectorException;
}
