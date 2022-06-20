package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.connector.compliance.ComplianceResponseRulesDto;
import com.czertainly.api.model.core.compliance.ComplianceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/*
Contains the list of parameters to be stored in the database. These
parameters will be added with the relevent values and will be stored in the
database as a JSON field
 */
public class CertificateComplianceStorageDto {

    @Schema(description = "Compliant Rules")
    private List<Long> ok;

    @Schema(description = "Non Compliant Rules")
    private List<Long> nok;

    @Schema(description = "Not Applicable Rules")
    private List<Long> na;


    public List<Long> getOk() {

        return ok;
    }

    public void setOk(List<Long> ok) {
        this.ok = ok;
    }

    public List<Long> getNok() {
        return nok;
    }

    public void setNok(List<Long> nok) {
        this.nok = nok;
    }

    public List<Long> getNa() {
        return na;
    }

    public void setNa(List<Long> na) {
        this.na = na;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("Compliant", ok)
                .append("Non Compliant", nok)
                .append("Not Applicable", na)
                .toString();
    }
}
