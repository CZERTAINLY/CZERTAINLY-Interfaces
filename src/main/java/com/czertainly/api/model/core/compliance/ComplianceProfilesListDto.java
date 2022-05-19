package com.czertainly.api.model.core.compliance;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ComplianceProfilesListDto extends NameAndUuidDto {
    @Schema(
            description = "Enabled flag. true = enabled, false = disabled",
            defaultValue = "false"
    )
    private Boolean enabled;

    @Schema(description = "Rules summary", required = true)
    private List<ComplianceProviderSummaryDto> rules;

    //Default getters and setters
    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<ComplianceProviderSummaryDto> getRules() {
        return rules;
    }

    public void setRules(List<ComplianceProviderSummaryDto> rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("rules", rules)
                .append("enabled", enabled)
                .toString();
    }
}
