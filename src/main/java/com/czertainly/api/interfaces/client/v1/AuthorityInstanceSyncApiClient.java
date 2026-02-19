package com.czertainly.api.interfaces.client.v1;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.connector.authority.AuthorityProviderInstanceDto;
import com.czertainly.api.model.connector.authority.AuthorityProviderInstanceRequestDto;
import com.czertainly.api.model.connector.authority.CaCertificatesRequestDto;
import com.czertainly.api.model.connector.authority.CaCertificatesResponseDto;
import com.czertainly.api.model.connector.authority.CertificateRevocationListRequestDto;
import com.czertainly.api.model.connector.authority.CertificateRevocationListResponseDto;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.List;

/**
 * Synchronous API client interface for Authority Provider Instance operations.
 * Implementations include REST-based and MQ-based clients.
 */
public interface AuthorityInstanceSyncApiClient {

    List<AuthorityProviderInstanceDto> listAuthorityInstances(ConnectorDto connector) throws ConnectorException;

    AuthorityProviderInstanceDto getAuthorityInstance(ConnectorDto connector, String uuid) throws ConnectorException;

    AuthorityProviderInstanceDto createAuthorityInstance(ConnectorDto connector, AuthorityProviderInstanceRequestDto requestDto) throws ConnectorException;

    AuthorityProviderInstanceDto updateAuthorityInstance(ConnectorDto connector, String uuid, AuthorityProviderInstanceRequestDto requestDto) throws ConnectorException;

    void removeAuthorityInstance(ConnectorDto connector, String uuid) throws ConnectorException;

    List<BaseAttribute> listRAProfileAttributes(ConnectorDto connector, String uuid) throws ConnectorException;

    Boolean validateRAProfileAttributes(ConnectorDto connector, String uuid, List<RequestAttribute> attributes) throws ValidationException, ConnectorException;

    CertificateRevocationListResponseDto getCrl(ConnectorDto connector, String uuid, CertificateRevocationListRequestDto requestDto) throws ConnectorException;

    CaCertificatesResponseDto getCaCertificates(ConnectorDto connector, String uuid, CaCertificatesRequestDto requestDto) throws ValidationException, ConnectorException;
}
