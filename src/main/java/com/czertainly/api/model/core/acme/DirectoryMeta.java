package com.czertainly.api.model.core.acme;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * This class contains the response for meta field in the directory list API
 */
public class DirectoryMeta {

    /**
     * URL for the terms and service to be accepted by the clients
     */
    @Schema(description = "Terms of service URL")
    private String termsOfService;

    /**
     * URL of the website for the acme configuration
     */
    @Schema(description = "Website URL")
    private String website;

    /**
     * CAA Identities to be used in the configuration
     */
    private List<String> caaIdentities;

    /**
     * Boolean value of the need of external account requirement
     */
    @Schema(description = "External Account Binding flag",
            required = true)
    private Boolean externalAccountRequired;

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

    public List<String> getCaaIdentities() {
        return caaIdentities;
    }

    public void setCaaIdentities(List<String> caaIdentities) {
        this.caaIdentities = caaIdentities;
    }

    public Boolean getExternalAccountRequired() {
        return externalAccountRequired;
    }

    public void setExternalAccountRequired(Boolean externalAccountRequired) {
        this.externalAccountRequired = externalAccountRequired;
    }
}
