package com.czertainly.api.model.client.acme;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.core.protocol.ProtocolCertificateAssociationsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Data
public class AcmeProfileRequestDto {

    @Schema(
            description = "Name of the ACME Profile",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"Profile Name 1"}
    )
    private String name;

    @Schema(
            description = "Description of the ACME Profile",
            examples = {"Sample description"}
    )
    private String description;
    @Schema(
            description = "Terms of Service URL",
            examples = {"https://sample-url.com/termsOfService"}
    )
    private String termsOfServiceUrl;
    @Schema(
            description = "Website URL",
            examples = {"https://sample-url.com"}
    )
    private String websiteUrl;
    @Schema(
            description = "DNS Resolver IP address",
            defaultValue = "System Default",
            examples = {"8.8.8.8"}
    )
    private String dnsResolverIp;
    @Schema(
            description = "DNS Resolver port number",
            defaultValue = "53",
            examples = {"53"}
    )
    private String dnsResolverPort;
    @Schema(
            description = "RA Profile UUID",
            examples = {"6b55de1c-844f-11ec-a8a3-0242ac120002"}
    )
    private String raProfileUuid;
    @Schema(
            description = "Retry interval for the Orders",
            defaultValue = "30",
            examples = {"60"}
    )
    private Integer retryInterval;
    @Schema(
            description = "Order Validity",
            defaultValue = "36000",
            examples = {"3000"}
    )
    private Integer validity;
    @Schema(
            description = "List of Attributes to issue Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> issueCertificateAttributes;
    @Schema(
            description = "List of Attributes to revoke Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> revokeCertificateAttributes;
    @Schema(
            description = "Require contact information for new Account",
            defaultValue = "false",
            examples = {"true"}
    )
    private Boolean requireContact;
    @Schema(
            description = "Require new Account to agree on Terms of Service",
            defaultValue = "false",
            examples = {"false"}
    )
    private Boolean requireTermsOfService;
    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;

    @Schema(description = "Properties to set for certificates associated with protocol", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ProtocolCertificateAssociationsDto protocolCertificateAssociations;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("description", description)
                .append("termsOfServiceUrl", termsOfServiceUrl)
                .append("websiteUrl", websiteUrl)
                .append("dnsResolverIp", dnsResolverIp)
                .append("dnsResolverPort", dnsResolverPort)
                .append("raProfileUuid", raProfileUuid)
                .append("retryInterval", retryInterval)
                .append("validity", validity)
                .append("issueCertificateAttributes", issueCertificateAttributes)
                .append("revokeCertificateAttributes", revokeCertificateAttributes)
                .append("requireContact", requireContact)
                .append("requireTermsOfService", requireTermsOfService)
                .append("customAttributes", customAttributes)
                .toString();
    }

    public Boolean isRequireTermsOfService() {
        return requireTermsOfService;
    }

    public Boolean isRequireContact() {
        return requireContact;
    }
}
