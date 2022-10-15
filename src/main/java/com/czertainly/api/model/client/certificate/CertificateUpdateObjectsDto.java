package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;

public class CertificateUpdateObjectsDto {

    @Schema(description = "RA Profile UUID")
    private String raProfileUuid;

    @Schema(description = "Group UUID")
    private String groupUuid;

    @Schema(description = "Certificate Owner")
    private String owner;

    public String getRaProfileUuid() {
        return raProfileUuid;
    }

    public void setRaProfileUuid(String raProfileUuid) {
        this.raProfileUuid = raProfileUuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
