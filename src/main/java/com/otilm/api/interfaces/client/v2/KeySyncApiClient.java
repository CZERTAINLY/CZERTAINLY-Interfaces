package com.otilm.api.interfaces.client.v2;

import com.otilm.api.clients.ApiClientConnectorInfo;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyAttributesRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.DestroyKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyDestructionStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationResponseV2Dto;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * Sync contract shared by REST and MQ clients for stateless cryptography v2 key creation.
 */
public interface KeySyncApiClient {

    List<BaseAttribute> listCreateKeyAttributes(ApiClientConnectorInfo connector,
            CreateKeyAttributesRequestV2Dto request) throws ConnectorException;

    ResponseEntity<KeyCreationResponseV2Dto> createKey(ApiClientConnectorInfo connector, CreateKeyRequestV2Dto request)
            throws ConnectorException;

    KeyCreationStatusResponseV2Dto getCreateKeyStatus(ApiClientConnectorInfo connector,
            KeyOperationRequestV2Dto request) throws ConnectorException;

    ResponseEntity<Void> cancelCreateKey(ApiClientConnectorInfo connector, KeyOperationRequestV2Dto request)
            throws ConnectorException;

    ResponseEntity<KeyOperationResponseV2Dto> destroyKey(ApiClientConnectorInfo connector,
            DestroyKeyRequestV2Dto request) throws ConnectorException;

    KeyDestructionStatusResponseV2Dto getDestroyKeyStatus(ApiClientConnectorInfo connector,
            KeyOperationRequestV2Dto request) throws ConnectorException;

    ResponseEntity<Void> cancelDestroyKey(ApiClientConnectorInfo connector, KeyOperationRequestV2Dto request)
            throws ConnectorException;
}
