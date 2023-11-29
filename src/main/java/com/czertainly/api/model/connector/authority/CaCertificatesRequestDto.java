package com.czertainly.api.model.connector.authority;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CaCertificatesRequestDto {

    @Schema(
        description = "List of RA Profiles attributes",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> raProfileAttributes;

}
