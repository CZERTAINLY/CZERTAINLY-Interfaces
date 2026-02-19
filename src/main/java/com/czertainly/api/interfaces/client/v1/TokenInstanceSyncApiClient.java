package com.czertainly.api.interfaces.client.v1;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.token.TokenInstanceDto;
import com.czertainly.api.model.connector.cryptography.token.TokenInstanceRequestDto;
import com.czertainly.api.model.connector.cryptography.token.TokenInstanceStatusDto;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.List;

public interface TokenInstanceSyncApiClient {
    List<TokenInstanceDto> listTokenInstances(ConnectorDto connector) throws ConnectorException;
    TokenInstanceDto getTokenInstance(ConnectorDto connector, String uuid) throws ConnectorException;
    TokenInstanceDto createTokenInstance(ConnectorDto connector, TokenInstanceRequestDto requestDto) throws ConnectorException;
    TokenInstanceDto updateTokenInstance(ConnectorDto connector, String uuid, TokenInstanceRequestDto requestDto) throws ConnectorException;
    void removeTokenInstance(ConnectorDto connector, String uuid) throws ConnectorException;
    TokenInstanceStatusDto getTokenInstanceStatus(ConnectorDto connector, String uuid) throws ConnectorException;
    List<BaseAttribute> listTokenProfileAttributes(ConnectorDto connector, String uuid) throws ConnectorException;
    void validateTokenProfileAttributes(ConnectorDto connector, String uuid, List<RequestAttribute> attributes) throws ValidationException, ConnectorException;
    List<BaseAttribute> listTokenInstanceActivationAttributes(ConnectorDto connector, String uuid) throws ConnectorException;
    void validateTokenInstanceActivationAttributes(ConnectorDto connector, String uuid, List<RequestAttribute> attributes) throws ValidationException, ConnectorException;
    void activateTokenInstance(ConnectorDto connector, String uuid, List<RequestAttribute> attributes) throws ConnectorException;
    void deactivateTokenInstance(ConnectorDto connector, String uuid) throws ConnectorException;
}
