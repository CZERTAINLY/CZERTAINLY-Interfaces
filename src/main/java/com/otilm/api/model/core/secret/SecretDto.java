package com.otilm.api.model.core.secret;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import com.otilm.api.model.connector.secrets.SecretType;
import com.otilm.api.model.core.compliance.ComplianceStatus;
import com.otilm.api.model.core.search.AttributeProjectable;
import com.otilm.api.model.core.search.FilterFieldSource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecretDto extends NameAndUuidDto implements AttributeProjectable {

    @Schema(description = "Type of the secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private SecretType type;

    @Schema(description = "Description of the secret", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Name and UUID of the vault profile where the secret is stored",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private NameAndUuidDto sourceVaultProfile;

    @Schema(description = "State of the secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private SecretState state;

    @Schema(description = "Version of the secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private int version;

    @Schema(description = "Indicates if the secret is currently enabled", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean enabled;

    @Schema(description = "Owner of the secret", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private NameAndUuidDto owner;

    @Schema(description = "Groups that have access to the secret", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<NameAndUuidDto> groups;

    @Schema(description = "Secret compliance status", requiredMode = Schema.RequiredMode.REQUIRED)
    private ComplianceStatus complianceStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = AttributeProjectable.ATTRIBUTE_VALUES_DESCRIPTION,
            example = AttributeProjectable.ATTRIBUTE_VALUES_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<FilterFieldSource, Map<String, List<BaseAttributeContentV3<?>>>> attributeValues;
}
