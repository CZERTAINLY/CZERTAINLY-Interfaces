package com.czertainly.api.model.client.location;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing client issue Certificate request for Location
 */
@Data
public class IssueToLocationRequestDto {

    @Schema(
            description = "RA Profile UUID",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String raProfileUuid;

    @Schema(
            description = "List of CSR Attributes for Location",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> csrAttributes;

    @Schema(
            description = "List of certificate issue Attributes for RA Profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> issueAttributes;

    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;

    @Schema(description = "List of Certificate Custom Attributes")
    private List<RequestAttributeDto> certificateCustomAttributes;
}
