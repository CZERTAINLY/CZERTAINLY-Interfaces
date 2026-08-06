package com.otilm.api.model.connector.discovery.v2;

import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.client.connector.v2.FeatureFlag;
import com.otilm.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/**
 * A capability a discovery connector may advertise for a specific resource in
 * {@link DiscoverySupportedResourceDto#getCapabilities()}. {@link #getFeatureFlag()} carries the
 * invariant that a capability is only valid to advertise if the connector also advertises the
 * interface-level {@link FeatureFlag} it maps to.
 */
@Schema(enumAsRef = true)
public enum DiscoveryResourceCapability implements IPlatformEnum {

    STOP_RESUME("stopResume", "Stop/Resume", FeatureFlag.DISCOVERY_STOP_RESUME);

    private static final DiscoveryResourceCapability[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;
    private final FeatureFlag featureFlag;

    DiscoveryResourceCapability(String code, String label, FeatureFlag featureFlag) {
        this(code, label, null, featureFlag);
    }

    DiscoveryResourceCapability(String code, String label, String description, FeatureFlag featureFlag) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.featureFlag = featureFlag;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * The interface-level {@link FeatureFlag} a connector must also advertise for this
     * capability to be valid on any resource's {@code capabilities} list.
     */
    public FeatureFlag getFeatureFlag() {
        return this.featureFlag;
    }

    @JsonCreator
    public static DiscoveryResourceCapability findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Discovery resource capability {}", code)));
    }
}
