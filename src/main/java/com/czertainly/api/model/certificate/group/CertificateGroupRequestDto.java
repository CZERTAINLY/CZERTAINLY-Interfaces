package com.czertainly.api.model.certificate.group;


import io.swagger.v3.oas.annotations.media.Schema;

public class CertificateGroupRequestDto {

    @Schema(description = "Name of the Certificate Group",
            required = true)
    private String name;

    @Schema(description = "Description of the Certificate Group")
    private String description;

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
}
