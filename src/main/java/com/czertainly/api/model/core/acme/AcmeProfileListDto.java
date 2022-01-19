package com.czertainly.api.model.core.acme;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.RequestAttributeDto;
import com.czertainly.api.model.core.raprofile.RaProfileDto;

import java.util.List;

public class AcmeProfileListDto extends NameAndUuidDto {

    private boolean isEnabled;
    private String description;
    private String raProfileName;
    private String raProfileUuid;
    private String directoryUrl;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getRaProfileName() {
        return raProfileName;
    }

    public void setRaProfileName(String raProfileName) {
        this.raProfileName = raProfileName;
    }

    public String getRaProfileUuid() {
        return raProfileUuid;
    }

    public void setRaProfileUuid(String raProfileUuid) {
        this.raProfileUuid = raProfileUuid;
    }

    public String getDirectoryUrl() {
        return directoryUrl;
    }

    public void setDirectoryUrl(String directoryUrl) {
        this.directoryUrl = directoryUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AcmeProfileListDto() {
    }
}
