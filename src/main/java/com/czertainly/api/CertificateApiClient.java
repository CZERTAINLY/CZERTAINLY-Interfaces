package com.czertainly.api;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.ca.CertRevocationDto;
import com.czertainly.api.model.ca.CertificateSignRequestDto;
import com.czertainly.api.model.ca.CertificateSignResponseDto;
import com.czertainly.api.model.connector.ConnectorDto;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class CertificateApiClient extends BaseApiClient {

    private static final String CERTIFICATE_BASE_CONTEXT = "/v1/authorityProvider/authorities/{uuid}/endEntityProfiles/{endEntityProfileName}/certificates";
    private static final String CERTIFICATE_ISSUE_CONTEXT = CERTIFICATE_BASE_CONTEXT + "/issue";
    private static final String CERTIFICATE_REVOKE_CONTEXT = CERTIFICATE_BASE_CONTEXT + "/revoke";

    public CertificateApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public CertificateSignResponseDto issueCertificate(ConnectorDto connector, String authorityUuid, String endEntityProfileName, CertificateSignRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + CERTIFICATE_ISSUE_CONTEXT, authorityUuid, endEntityProfileName)
                .body(Mono.just(requestDto), CertificateSignRequestDto.class)
                .retrieve()
                .toEntity(CertificateSignResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public void revokeCertificate(ConnectorDto connector, String authorityUuid, String endEntityProfileName, CertRevocationDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        processRequest(r -> r
                .uri(connector.getUrl() + CERTIFICATE_REVOKE_CONTEXT, authorityUuid, endEntityProfileName)
                .body(Mono.just(requestDto), CertRevocationDto.class)
                .retrieve()
                .bodyToMono(Void.class),
                request,
                connector);
    }
}
