package com.czertainly.api.model.core.acme;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Response for meta field in the Directory list API
 */
public class DirectoryMeta {

    /**
     * URL for the Terms and Service to be accepted by the clients
     */
    @Schema(description = "Terms of Service URL")
    private String termsOfService;

    /**
     * URL of the website for the ACME configuration
     */
    @Schema(description = "Website URL")
    private String website;

    /**
     * Boolean value of the need of external account requirement
     */
    @Schema(description = "External Account Binding flag",
            required = false)
    private Boolean externalAccountRequired;

    /**
     * CAA record validation list of hostnames
     */
    @Schema(description = "Array of CAA record validation servers",
            required = false)
    private String[] caaIdentities;

    public String getTermsOfService() {
        return termsOfService;
    }

    public void setTermsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getExternalAccountRequired() {
        return externalAccountRequired;
    }

    public void setExternalAccountRequired(Boolean externalAccountRequired) {
        this.externalAccountRequired = externalAccountRequired;
    }

    public String[] getCaaIdentities() { return caaIdentities; }

    public void setCaaIdentities(String[] caaIdentities) { this.caaIdentities = caaIdentities; }
}
