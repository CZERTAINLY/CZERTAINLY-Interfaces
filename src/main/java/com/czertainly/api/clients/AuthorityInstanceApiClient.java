package com.czertainly.api.clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.authority.AuthorityProviderInstanceDto;
import com.czertainly.api.model.connector.authority.AuthorityProviderInstanceRequestDto;
import com.czertainly.api.model.connector.authority.CertificateRevocationListRequestDto;
import com.czertainly.api.model.connector.authority.CertificateRevocationListResponseDto;
import com.czertainly.api.model.connector.authority.CaCertificatesRequestDto;
import com.czertainly.api.model.connector.authority.CaCertificatesResponseDto;
import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.net.ssl.TrustManager;
import java.util.List;
import java.util.Objects;

public class AuthorityInstanceApiClient extends BaseApiClient {

    private static final String AUTHORITY_INSTANCE_BASE_CONTEXT = "/v1/authorityProvider/authorities";
    private static final String AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT = AUTHORITY_INSTANCE_BASE_CONTEXT + "/{uuid}";
    private static final String AUTHORITY_INSTANCE_RA_ATTRS_CONTEXT = AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT + "/raProfile/attributes";
    private static final String AUTHORITY_INSTANCE_RA_ATTRS_VALIDATE_CONTEXT = AUTHORITY_INSTANCE_RA_ATTRS_CONTEXT + "/validate";
    private static final String AUTHORITY_INSTANCE_CRL_CONTEXT = AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT + "/crl";
    private static final String AUTHORITY_INSTANCE_CERT_CONTEXT = AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT + "/caCertificates";

    private static final ParameterizedTypeReference<List<RequestAttributeDto>> ATTRIBUTE_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public AuthorityInstanceApiClient(WebClient webClient, TrustManager[] defaultTrustManagers) {
        this.webClient = webClient;
        this.defaultTrustManagers = defaultTrustManagers;
    }

    public List<AuthorityProviderInstanceDto> listAuthorityInstances(ConnectorDto connector) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_BASE_CONTEXT)
                .retrieve()
                .toEntityList(AuthorityProviderInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public AuthorityProviderInstanceDto getAuthorityInstance(ConnectorDto connector, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT, uuid)
                .retrieve()
                .toEntity(AuthorityProviderInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public AuthorityProviderInstanceDto createAuthorityInstance(ConnectorDto connector, AuthorityProviderInstanceRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_BASE_CONTEXT)
                .body(Mono.just(requestDto), AuthorityProviderInstanceRequestDto.class)
                .retrieve()
                .toEntity(AuthorityProviderInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }


    public AuthorityProviderInstanceDto updateAuthorityInstance(ConnectorDto connector, String uuid, AuthorityProviderInstanceRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT, uuid)
                .body(Mono.just(requestDto), AuthorityProviderInstanceRequestDto.class)
                .retrieve()
                .toEntity(AuthorityProviderInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public void removeAuthorityInstance(ConnectorDto connector, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.DELETE, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT, uuid)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }


    public List<BaseAttribute> listRAProfileAttributes(ConnectorDto connector, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_RA_ATTRS_CONTEXT, uuid)
                .retrieve()
                .toEntityList(BaseAttribute.class)
                .block().getBody(),
                request,
                connector);
    }

    public Boolean validateRAProfileAttributes(ConnectorDto connector, String uuid, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_RA_ATTRS_VALIDATE_CONTEXT, uuid)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Boolean.class)
                .block().getBody(),
                request,
                connector);
    }

    public CertificateRevocationListResponseDto getCrl(ConnectorDto connector, String uuid, CertificateRevocationListRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> Objects.requireNonNull(r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_CRL_CONTEXT, uuid)
                .body(Mono.just(requestDto), CertificateRevocationListRequestDto.class)
                .retrieve()
                .toEntity(CertificateRevocationListResponseDto.class)
                .block()).getBody(),
                request,
                connector);
    }

    public CaCertificatesResponseDto getCaCertificates(ConnectorDto connector, String uuid, CaCertificatesRequestDto requestDto) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> Objects.requireNonNull(r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_CERT_CONTEXT, uuid)
                .body(Mono.just(requestDto), CaCertificatesRequestDto.class)
                .retrieve()
                .toEntity(CaCertificatesResponseDto.class)
                .block()).getBody(),
                request,
                connector);
    }
}