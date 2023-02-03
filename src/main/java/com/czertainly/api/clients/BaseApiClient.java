package com.czertainly.api.clients;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContent;
import com.czertainly.api.model.common.attribute.v2.content.FileAttributeContent;
import com.czertainly.api.model.core.connector.ConnectorDto;
import com.czertainly.api.model.core.connector.ConnectorStatus;
import com.czertainly.config.TrustedCertificatesConfig;
import com.czertainly.core.util.AttributeDefinitionUtils;
import com.czertainly.core.util.KeyStoreUtils;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.*;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public abstract class BaseApiClient {
    private static final Logger logger = LoggerFactory.getLogger(BaseApiClient.class);

    @Autowired
    private TrustedCertificatesConfig trustedCertificatesConfig;

    // Basic auth attribute names
    public static final String ATTRIBUTE_USERNAME = "username";
    public static final String ATTRIBUTE_PASSWORD = "password";

    // Certificate attribute names
    public static final String ATTRIBUTE_KEYSTORE_TYPE = "keyStoreType";
    public static final String ATTRIBUTE_KEYSTORE = "keyStore";
    public static final String ATTRIBUTE_KEYSTORE_PASSWORD = "keyStorePassword";
    public static final String ATTRIBUTE_TRUSTSTORE_TYPE = "trustStoreType";
    public static final String ATTRIBUTE_TRUSTSTORE = "trustStore";
    public static final String ATTRIBUTE_TRUSTSTORE_PASSWORD = "trustStorePassword";

    // API key attribute names
    public static final String ATTRIBUTE_API_KEY_HEADER = "apiKeyHeader";
    public static final String ATTRIBUTE_API_KEY = "apiKey";

    protected WebClient webClient;

    public WebClient.RequestBodyUriSpec prepareRequest(HttpMethod method, ConnectorDto connector, Boolean validateConnectorStatus) {
        if(validateConnectorStatus){
            validateConnectorStatus(connector.getStatus());
        }
        WebClient.RequestBodySpec request;

        // for backward compatibility
        if (connector.getAuthType() == null) {
            request = webClient.method(method);
            return (WebClient.RequestBodyUriSpec) request;
        }

        List<ResponseAttributeDto> authAttributes = connector.getAuthAttributes();

        switch (connector.getAuthType()) {
            case NONE:
                request = webClient.method(method);
                break;
            case BASIC:
                BaseAttributeContent<String> username = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_USERNAME, authAttributes, false);
                BaseAttributeContent<String> password = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_PASSWORD, authAttributes, false);

                request = webClient
                        .method(method)
                        .headers(h -> h.setBasicAuth(username.getData(), password.getData()));
                break;
            case CERTIFICATE:
                SslContext sslContext = createSslContext(authAttributes);
                HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
                webClient.mutate().clientConnector(new ReactorClientHttpConnector(httpClient)).build();

                request = webClient.method(method);
                break;
            case API_KEY:
                BaseAttributeContent<String> apiKeyHeader = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_API_KEY_HEADER, authAttributes, false);
                BaseAttributeContent<String> apiKey = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_API_KEY, authAttributes, false);

                request = webClient
                        .method(method)
                        .headers(h -> h.set(apiKeyHeader.getData(), apiKey.getData()));
                break;
            case JWT:
                throw new UnsupportedOperationException("JWT is unimplemented");
            default:
                throw new IllegalArgumentException("Unknown auth type " + connector.getAuthType());
        }

        return (WebClient.RequestBodyUriSpec) request;
    }

    public void validateConnectorStatus(ConnectorStatus connectorStatus) throws ValidationException {
        if(connectorStatus.equals(ConnectorStatus.WAITING_FOR_APPROVAL)){
            throw new ValidationException(ValidationError.create("Connector has invalid status: Waiting For Approval"));
        }
    }

    private SslContext createSslContext(List<ResponseAttributeDto> attributes) {
        try {
            SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();

            KeyManager km = null;
            FileAttributeContent keyStoreData = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_KEYSTORE, attributes, false);
            if (keyStoreData != null && !keyStoreData.getData().getContent().isEmpty()) {
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()); //"SunX509"

                BaseAttributeContent<String> keyStoreType = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_KEYSTORE_TYPE, attributes, false);
                BaseAttributeContent<String> keyStorePassword = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_KEYSTORE_PASSWORD, attributes, false);
                byte[] keyStoreBytes = Base64.getDecoder().decode(keyStoreData.getData().getContent());

                kmf.init(KeyStoreUtils.bytes2KeyStore(keyStoreBytes, keyStorePassword.getData(), keyStoreType.getData()), keyStorePassword.getData().toCharArray());
                km = kmf.getKeyManagers()[0];
            }

            sslContextBuilder.keyManager(km);

            TrustManager tm;
            FileAttributeContent trustStoreData = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_TRUSTSTORE, attributes, false);
            if (trustStoreData != null && !trustStoreData.getData().getContent().isEmpty()) {
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); //"SunX509"

                BaseAttributeContent<String> trustStoreType = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_TRUSTSTORE_TYPE, attributes, false);
                BaseAttributeContent<String> trustStorePassword = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_TRUSTSTORE_PASSWORD, attributes, false);
                byte[] trustStoreBytes = Base64.getDecoder().decode(trustStoreData.getData().getContent());

                tmf.init(KeyStoreUtils.bytes2KeyStore(trustStoreBytes, trustStorePassword.getData(), trustStoreType.getData()));
                tm = tmf.getTrustManagers()[0];
            } else {
                // load default TrustManager
                tm = trustedCertificatesConfig.getDefaultTrustManagers()[0];
            }

            sslContextBuilder.trustManager(tm);

            return sslContextBuilder.protocols("TLSv1.2").build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize SslContext.", e);
        }
    }

    private static final ParameterizedTypeReference<List<String>> ERROR_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public static WebClient prepareWebClient() {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .filter(ExchangeFilterFunction.ofResponseProcessor(BaseApiClient::handleHttpExceptions))
                .exchangeStrategies(strategies)
                .build();
    }

    private static Mono<ClientResponse> handleHttpExceptions(ClientResponse clientResponse) {
        if (HttpStatus.UNPROCESSABLE_ENTITY.equals(clientResponse.statusCode())) {
            return clientResponse.bodyToMono(ERROR_LIST_TYPE_REF).flatMap(body ->
                    Mono.error(new ValidationException(body.stream()
                                    .map(ValidationError::create)
                                    .collect(Collectors.toList())
                            )
                    )
            );
        }
        if (HttpStatus.NOT_FOUND.equals(clientResponse.statusCode())) {
            return clientResponse.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new NotFoundException(body)));
        }
        if (clientResponse.statusCode().is4xxClientError()) {
            return clientResponse.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new ConnectorClientException(body, HttpStatus.valueOf(clientResponse.statusCode().value()))));
        }
        if (clientResponse.statusCode().is5xxServerError()) {
            return clientResponse.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new ConnectorServerException(body, HttpStatus.valueOf(clientResponse.statusCode().value()))));
        }
        return Mono.just(clientResponse);
    }

    public static <T, R> R processRequest(Function<T, R> func, T request, ConnectorDto connector) throws ConnectorException {
        try {
            return func.apply(request);
        } catch (Exception e) {
            Throwable unwrapped = Exceptions.unwrap(e);
            logger.error(unwrapped.getMessage(), unwrapped);

            if (unwrapped instanceof IOException || unwrapped instanceof WebClientRequestException) {
                throw new ConnectorCommunicationException(unwrapped.getMessage(), unwrapped, connector);
            } else if (unwrapped instanceof ConnectorException) {
                ConnectorException ce = (ConnectorException) unwrapped;
                ce.setConnector(connector);
                throw ce;
            } else {
                throw e;
            }
        }
    }
}
