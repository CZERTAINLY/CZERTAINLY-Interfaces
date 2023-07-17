package com.czertainly.api.model.client.auth;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class RoleRequestDto {

    @Schema(description = "Name of the role")
    private String name;

    @Schema(description = "Description for the role")
    private String description;

    @Schema(description = "Role contact email")
    private String email;

    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RequestAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<RequestAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("description", description)
                .append("email", email)
                .append("customAttributes", customAttributes)
                .toString();
    }
}
