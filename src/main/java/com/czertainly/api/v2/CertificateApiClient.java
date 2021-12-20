package com.czertainly.api.v2;

import com.czertainly.api.BaseApiClient;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.connector.ConnectorDto;
import com.czertainly.api.v2.model.ca.CertRevocationDto;
import com.czertainly.api.v2.model.ca.CertificateDataResponseDto;
import com.czertainly.api.v2.model.ca.CertificateRenewRequestDto;
import com.czertainly.api.v2.model.ca.CertificateSignRequestDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class CertificateApiClient extends BaseApiClient {

    private static final String CERTIFICATE_BASE_CONTEXT = "/v2/caConnector/authorities/{uuid}/certificates";
    private static final String CERTIFICATE_IDENTIFIED_CONTEXT = CERTIFICATE_BASE_CONTEXT + "/{certificateId}";

    private static final String CERTIFICATE_ISSUE_CONTEXT = CERTIFICATE_BASE_CONTEXT + "/issue";
    private static final String CERTIFICATE_ISSUE_ATTRIBUTES_CONTEXT = CERTIFICATE_ISSUE_CONTEXT + "/attributes";
    private static final String CERTIFICATE_ISSUE_ATTRIBUTES_VALIDATE_CONTEXT = CERTIFICATE_ISSUE_ATTRIBUTES_CONTEXT + "/validate";

    private static final String CERTIFICATE_RENEW_CONTEXT = CERTIFICATE_IDENTIFIED_CONTEXT + "/renew";

    private static final String CERTIFICATE_REVOKE_ATTRIBUTES_CONTEXT = CERTIFICATE_BASE_CONTEXT + "/revoke/attributes";
    private static final String CERTIFICATE_REVOKE_ATTRIBUTES_VALIDATE_CONTEXT = CERTIFICATE_REVOKE_ATTRIBUTES_CONTEXT + "/validate";

    private static final String CERTIFICATE_REVOKE_CONTEXT = CERTIFICATE_IDENTIFIED_CONTEXT + "/revoke";

    private static final ParameterizedTypeReference<List<AttributeDefinition>> ATTRIBUTE_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public CertificateApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<AttributeDefinition> listIssueCertificateAttributes(ConnectorDto connector, String authorityUuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + CERTIFICATE_ISSUE_ATTRIBUTES_CONTEXT, authorityUuid)
                .retrieve()
                .toEntityList(AttributeDefinition.class)
                .block().getBody(),
                request,
                connector);
    }

    public Boolean validateIssueCertificateAttributes(ConnectorDto connector, String authorityUuid, List<AttributeDefinition> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + CERTIFICATE_ISSUE_ATTRIBUTES_VALIDATE_CONTEXT, authorityUuid)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Boolean.class)
                .block().getBody(),
                request,
                connector);
    }

    public CertificateDataResponseDto issueCertificate(ConnectorDto connector, String authorityUuid, CertificateSignRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + CERTIFICATE_ISSUE_CONTEXT, authorityUuid)
                .body(Mono.just(requestDto), CertificateSignRequestDto.class)
                .retrieve()
                .toEntity(CertificateDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public CertificateDataResponseDto renewCertificate(ConnectorDto connector, String authorityUuid, String certificateId, CertificateRenewRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + CERTIFICATE_RENEW_CONTEXT, authorityUuid, certificateId)
                .body(Mono.just(requestDto), CertificateRenewRequestDto.class)
                .retrieve()
                .toEntity(CertificateDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public List<AttributeDefinition> listRevokeCertificateAttributes(ConnectorDto connector, String authorityUuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + CERTIFICATE_REVOKE_ATTRIBUTES_CONTEXT, authorityUuid)
                .retrieve()
                .toEntityList(AttributeDefinition.class)
                .block().getBody(),
                request,
                connector);
    }

    public Boolean validateRevokeCertificateAttributes(ConnectorDto connector, String authorityUuid, List<AttributeDefinition> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + CERTIFICATE_REVOKE_ATTRIBUTES_VALIDATE_CONTEXT, authorityUuid)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Boolean.class)
                .block().getBody(),
                request,
                connector);
    }

    public void revokeCertificate(ConnectorDto connector, String authorityUuid, String certificateId, CertRevocationDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        processRequest(r -> r
                .uri(connector.getUrl() + CERTIFICATE_REVOKE_CONTEXT, authorityUuid, certificateId)
                .body(Mono.just(requestDto), CertRevocationDto.class)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }
}
