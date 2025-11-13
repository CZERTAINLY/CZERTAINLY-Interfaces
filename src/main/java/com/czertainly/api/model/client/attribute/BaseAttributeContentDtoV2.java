package com.czertainly.api.model.client.attribute;

import io.swagger.v3.oas.annotations.media.Schema;


public interface BaseAttributeContentDtoV2 {

    @Schema(description = "Content Reference")
    String getReference();

}
