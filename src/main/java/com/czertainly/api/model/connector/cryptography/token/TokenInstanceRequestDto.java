package com.czertainly.api.model.connector.cryptography.token;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TokenInstanceRequestDto {

    @Schema(description = "Token instance name",
            required = true)
    private String name;

    @Schema(description = "Kind of Token instance",
            required = true)
    private String kind;

    @Schema(description = "List of Token instance Attributes",
            required = true)
    private List<RequestAttributeDto> attributes;

}
