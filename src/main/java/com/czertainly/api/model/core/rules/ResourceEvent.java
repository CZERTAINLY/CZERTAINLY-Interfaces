package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.czertainly.api.model.core.auth.Resource;

import java.util.Arrays;
import java.util.List;

public enum ResourceEvent implements IPlatformEnum {

    DISCOVERY_FINISHED("discoveryFinished", "Discovery Finished", "Discovery has been finished.", Resource.DISCOVERY, Resource.CERTIFICATE),
    ;

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

    public static List<ResourceEvent> listEventsByResource(Resource resource) {
        return Arrays.stream(ResourceEvent.values()).toList().stream().filter(event -> event.resource == resource).toList();
    }
}
