package com.czertainly.api.model.core.certificate.group;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class CertificateGroupDto extends NameAndUuidDto {

    @Schema(description = "Description of the Certificate Group")
    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
