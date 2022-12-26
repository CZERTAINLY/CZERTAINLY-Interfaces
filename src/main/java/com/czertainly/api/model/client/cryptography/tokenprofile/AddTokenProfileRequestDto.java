package com.czertainly.api.model.client.cryptography.tokenprofile;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing RA profile registration request
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddTokenProfileRequestDto {

    @Schema(
            description = "Token Profile name",
            required = true
    )
    private String name;

    @Schema(
            description = "Token Profile description"
    )
    private String description;

    @Schema(
            description = "List of Attributes to create Token Profile",
            required = true
    )
    private List<RequestAttributeDto> attributes;

    @Schema(
            description = "List of Custom Attributes"
    )
    private List<RequestAttributeDto> customAttributes;

    @Schema(
            description = "Enabled flag - true = enabled; false = disabled",
            defaultValue = "false"
    )
    private Boolean enabled;
}
