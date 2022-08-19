package com.czertainly.api.model.core.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/*
Contains the list of parameters to be stored in the database. These
parameters will be added with the relevent values and will be stored in the
database as a JSON field
 */
public class CertificateComplianceStorageDto {

    @Schema(description = "Compliant Rules")
    private List<String> ok = new ArrayList<>();

    @Schema(description = "Non Compliant Rules")
    private List<String> nok = new ArrayList<>();

    @Schema(description = "Not Applicable Rules")
    private List<String> na = new ArrayList<>();


    public List<String> getOk() {

        return ok;
    }

    public void setOk(List<String> ok) {
        this.ok = ok;
    }

    public List<String> getNok() {
        return nok;
    }

    public void setNok(List<String> nok) {
        this.nok = nok;
    }

    public List<String> getNa() {
        return na;
    }

    public void setNa(List<String> na) {
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
