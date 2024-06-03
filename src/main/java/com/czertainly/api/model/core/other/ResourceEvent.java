package com.czertainly.api.model.core.other;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.czertainly.api.model.core.auth.Resource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;

public enum ResourceEvent implements IPlatformEnum {

    DISCOVERY_FINISHED("discoveryFinished", "Discovery Finished", "Discovery has been finished.", Resource.DISCOVERY, Resource.CERTIFICATE),
    ;

    private static final ResourceEvent[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;
    private final Resource resource;
    private final Resource producedResource;

    ResourceEvent(final String code, final String label, final String description, final Resource resource, final Resource producedResource) {
        this.code = code;
        this.label = label;
        this.description =description;
        this.resource = resource;
        this.producedResource = producedResource;
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
    public Resource getResource() {
        return resource;
    }

    public Resource getProducedResource() {
        return producedResource;
    }

    @JsonCreator
    public static ResourceEvent findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Resource event {}", code)));
    }

    public static List<ResourceEvent> listEventsByResource(Resource resource) {
        return Arrays.stream(VALUES).filter(event -> event.resource == resource).toList();
    }
}
