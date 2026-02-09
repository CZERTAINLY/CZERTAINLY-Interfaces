package com.czertainly.api.model.core.secret;

import com.czertainly.api.model.common.enums.IPlatformEnum;

public enum SecretType implements IPlatformEnum {

    BASIC_AUTH(Codes.BASIC_AUTH, "Basic Authentication", "A secret type for basic authentication, typically consisting of a username and password."),
    API_KEY(Codes.API_KEY, "API Key", "A secret type for API keys, which are used for authenticating applications or services without a username and password."),
    JWT_TOKEN(Codes.JWT_TOKEN, "JWT Token", "A secret type for JSON Web Tokens (JWT), which are used for securely transmitting information between parties as a JSON object."),
    SECRET_KEY(Codes.SECRET_KEY, "Secret Key", "A secret type for secret keys, which are used in various cryptographic operations such as encryption and signing."),
    PRIVATE_KEY(Codes.PRIVATE_KEY, "Private Key", "A secret type for private keys, which are used in asymmetric cryptography for signing and decryption."),
    KEY_STORE(Codes.KEY_STORE, "Key Store", "A secret type for key stores, which are used to store and manage cryptographic keys and certificates."),
    KEY_VALUE(Codes.KEY_VALUE, "Key-Value Pair", "A secret type for key-value pairs, which can be used to store arbitrary secrets in a simple key-value format."),
    GENERIC(Codes.GENERIC, "Generic Secret", "A generic secret type that can be used for any kind of secret that does not fit into the other predefined types.")
    ;

    private final String code;
    private final String label;
    private final String description;

    SecretType(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public class Codes {
        public static final String BASIC_AUTH = "basicAuth";
        public static final String API_KEY = "apiKey";
        public static final String JWT_TOKEN = "jwtToken";
        public static final String SECRET_KEY = "secretKey";
        public static final String PRIVATE_KEY = "privateKey";
        public static final String KEY_STORE = "keyStore";
        public static final String KEY_VALUE = "keyValue";
        public static final String GENERIC = "generic";
    }
}
