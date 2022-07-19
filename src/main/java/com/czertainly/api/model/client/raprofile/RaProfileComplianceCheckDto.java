package com.czertainly.api.model.client.raprofile;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RaProfileComplianceCheckDto {
    @Schema(description = "List of UUIDs of the RA Profiles")
    private List<String> raProfileUuids;

    public List<String> getRaProfileUuids() {
        return raProfileUuids;
    }

    public void setRaProfileUuids(List<String> raProfileUuids) {
        this.raProfileUuids = raProfileUuids;
    }

    @Override
    public String toString() {
        return "RaProfileComplianceCheckDto{" +
                "raProfileUuids=" + raProfileUuids +
                '}';
    }
}
