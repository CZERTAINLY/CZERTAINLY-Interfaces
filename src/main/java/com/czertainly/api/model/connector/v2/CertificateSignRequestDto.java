package com.czertainly.api.model.connector.v2;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing a request to sign CSR
 */
public class CertificateSignRequestDto {

    @Schema(description = "Certificate sign request (PKCS#10) encoded as Base64 string",
            required = true)
    private String pkcs10;

    @Schema(description = "List of RA Profiles attributes",
            required = true)
    private List<RequestAttributeDto> raProfileAttributes;

    @Schema(description = "List of Attributes to issue Certificate",
            required = true)
    private List<RequestAttributeDto> attributes;

    public String getPkcs10() {
        return pkcs10;
    }

    public void setPkcs10(String pkcs10) {
        this.pkcs10 = pkcs10;
    }

    public List<RequestAttributeDto> getRaProfileAttributes() {
        return raProfileAttributes;
    }

    public void setRaProfileAttributes(List<RequestAttributeDto> raProfileAttributes) {
        this.raProfileAttributes = raProfileAttributes;
    }

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("pkcs10", pkcs10)
                .append("raProfile", raProfileAttributes)
                .append("attributes", attributes)
                .toString();
    }
}
