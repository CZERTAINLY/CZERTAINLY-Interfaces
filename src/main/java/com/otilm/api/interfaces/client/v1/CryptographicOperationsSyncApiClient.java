package com.otilm.api.interfaces.client.v1;

import com.otilm.api.clients.ApiClientConnectorInfo;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.cryptography.operations.CipherDataRequestDto;
import com.otilm.api.model.connector.cryptography.operations.DecryptDataResponseDto;
import com.otilm.api.model.connector.cryptography.operations.EncryptDataResponseDto;
import com.otilm.api.model.connector.cryptography.operations.RandomDataRequestDto;
import com.otilm.api.model.connector.cryptography.operations.RandomDataResponseDto;
import com.otilm.api.model.connector.cryptography.operations.SignDataRequestDto;
import com.otilm.api.model.connector.cryptography.operations.SignDataResponseDto;
import com.otilm.api.model.connector.cryptography.operations.VerifyDataRequestDto;
import com.otilm.api.model.connector.cryptography.operations.VerifyDataResponseDto;
import java.util.List;

public interface CryptographicOperationsSyncApiClient {
    EncryptDataResponseDto encryptData(ApiClientConnectorInfo connector, String uuid, String keyUuid,
            CipherDataRequestDto requestDto) throws ConnectorException;

    DecryptDataResponseDto decryptData(ApiClientConnectorInfo connector, String uuid, String keyUuid,
            CipherDataRequestDto requestDto) throws ConnectorException;

    SignDataResponseDto signData(ApiClientConnectorInfo connector, String uuid, String keyUuid,
            SignDataRequestDto requestDto) throws ConnectorException;

    VerifyDataResponseDto verifyData(ApiClientConnectorInfo connector, String uuid, String keyUuid,
            VerifyDataRequestDto requestDto) throws ConnectorException;

    List<BaseAttribute> listRandomAttributes(ApiClientConnectorInfo connector, String uuid) throws ConnectorException;

    void validateRandomAttributes(ApiClientConnectorInfo connector, String uuid, List<RequestAttribute> attributes)
            throws ValidationException, ConnectorException;

    RandomDataResponseDto randomData(ApiClientConnectorInfo connector, String uuid, RandomDataRequestDto requestDto)
            throws ConnectorException;
}
