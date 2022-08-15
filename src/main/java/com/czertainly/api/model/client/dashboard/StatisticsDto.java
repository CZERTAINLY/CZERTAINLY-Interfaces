package com.czertainly.api.model.client.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public class StatisticsDto {
    // Total Count of the objects. This can be used by the FE for percentage calculation
    @Schema(description = "Number Certificates available")
    private Long totalCertificates;
	@Schema(description = "Number of Groups")
    private Long totalGroups;
	@Schema(description = "Number of Discoveries triggered")
    private Long totalDiscoveries;
	@Schema(description = "Number of Connectors added")
    private Long totalConnectors;
	@Schema(description = "Number of RA Profiles in the platform")
    private Long totalRaProfiles;
	@Schema(description = "Number of Credentials")
    private Long totalCredentials;
	@Schema(description = "Number of Authority instances")
    private Long totalAuthorities;
	@Schema(description = "Number of Administrators")
    private Long totalAdministrators;
	@Schema(description = "Number of Clients added")
    private Long totalClients;

    // Data of the certificate count by group, and RA Profile assignment
	@Schema(description = "Map of Certificate count by Group")
    private Map<String, Long> groupStatByCertificateCount;
	@Schema(description = "Map of Certificate count by RA Profile")
    private Map<String, Long> raProfileStatByCertificateCount;

    //Certificate related statistics
	@Schema(description = "Map of Certificate count by Type")
    private Map<String, Long> certificateStatByType;
	@Schema(description = "Map of Certificate count by expiry date")
    private Map<String, Long> certificateStatByExpiry;
	@Schema(description = "Map of Certificate count by key size")
    private Map<String, Long> certificateStatByKeySize;
	@Schema(description = "Map of Certificate count by basic constraints")
    private Map<String, Long> certificateStatByBasicConstraints;
	@Schema(description = "Map of Certificate count by status")
    private Map<String, Long> certificateStatByStatus;
    @Schema(description = "Map of Certificate count by compliance status")
    private Map<String, Long> certificateStatByComplianceStatus;

    //Other Entities by Status
	@Schema(description = "Map of Connector count by status")
    private Map<String, Long> connectorStatByStatus;
	@Schema(description = "Map of RA Profile count by status")
    private Map<String, Long> raProfileStatByStatus;
	@Schema(description = "Map of Administrator count by status")
    private Map<String, Long> administratorStatByStatus;
	@Schema(description = "Map of Client count by status")
    private Map<String, Long> clientStatByStatus;

    public Long getTotalCertificates() {
        return totalCertificates;
    }

    public void setTotalCertificates(Long totalCertificates) {
        this.totalCertificates = totalCertificates;
    }

    public Long getTotalGroups() {
        return totalGroups;
    }

    public void setTotalGroups(Long totalGroups) {
        this.totalGroups = totalGroups;
    }

    public Long getTotalDiscoveries() {
        return totalDiscoveries;
    }

    public void setTotalDiscoveries(Long totalDiscoveries) {
        this.totalDiscoveries = totalDiscoveries;
    }

    public Long getTotalConnectors() {
        return totalConnectors;
    }

    public void setTotalConnectors(Long totalConnectors) {
        this.totalConnectors = totalConnectors;
    }

    public Long getTotalRaProfiles() {
        return totalRaProfiles;
    }

    public void setTotalRaProfiles(Long totalRaProfiles) {
        this.totalRaProfiles = totalRaProfiles;
    }

    public Long getTotalCredentials() {
        return totalCredentials;
    }

    public void setTotalCredentials(Long totalCredentials) {
        this.totalCredentials = totalCredentials;
    }

    public Long getTotalAuthorities() {
        return totalAuthorities;
    }

    public void setTotalAuthorities(Long totalAuthorities) {
        this.totalAuthorities = totalAuthorities;
    }

    public Long getTotalAdministrators() {
        return totalAdministrators;
    }

    public void setTotalAdministrators(Long totalAdministrators) {
        this.totalAdministrators = totalAdministrators;
    }

    public Long getTotalClients() {
        return totalClients;
    }

    public void setTotalClients(Long totalClients) {
        this.totalClients = totalClients;
    }

    public Map<String, Long> getGroupStatByCertificateCount() {
        return groupStatByCertificateCount;
    }

    public void setGroupStatByCertificateCount(Map<String, Long> groupStatByCertificateCount) {
        this.groupStatByCertificateCount = groupStatByCertificateCount;
    }

    public Map<String, Long> getRaProfileStatByCertificateCount() {
        return raProfileStatByCertificateCount;
    }

    public void setRaProfileStatByCertificateCount(Map<String, Long> raProfileStatByCertificateCount) {
        this.raProfileStatByCertificateCount = raProfileStatByCertificateCount;
    }

    public Map<String, Long> getCertificateStatByType() {
        return certificateStatByType;
    }

    public void setCertificateStatByType(Map<String, Long> certificateStatByType) {
        this.certificateStatByType = certificateStatByType;
    }

    public Map<String, Long> getCertificateStatByExpiry() {
        return certificateStatByExpiry;
    }

    public void setCertificateStatByExpiry(Map<String, Long> certificateStatByExpiry) {
        this.certificateStatByExpiry = certificateStatByExpiry;
    }

    public Map<String, Long> getCertificateStatByKeySize() {
        return certificateStatByKeySize;
    }

    public void setCertificateStatByKeySize(Map<String, Long> certificateStatByKeySize) {
        this.certificateStatByKeySize = certificateStatByKeySize;
    }

    public Map<String, Long> getCertificateStatByBasicConstraints() {
        return certificateStatByBasicConstraints;
    }

    public void setCertificateStatByBasicConstraints(Map<String, Long> certificateStatByBasicConstraints) {
        this.certificateStatByBasicConstraints = certificateStatByBasicConstraints;
    }

    public Map<String, Long> getConnectorStatByStatus() {
        return connectorStatByStatus;
    }

    public void setConnectorStatByStatus(Map<String, Long> connectorStatByStatus) {
        this.connectorStatByStatus = connectorStatByStatus;
    }

    public Map<String, Long> getRaProfileStatByStatus() {
        return raProfileStatByStatus;
    }

    public void setRaProfileStatByStatus(Map<String, Long> raProfileStatByStatus) {
        this.raProfileStatByStatus = raProfileStatByStatus;
    }

    public Map<String, Long> getAdministratorStatByStatus() {
        return administratorStatByStatus;
    }

    public void setAdministratorStatByStatus(Map<String, Long> administratorStatByStatus) {
        this.administratorStatByStatus = administratorStatByStatus;
    }

    public Map<String, Long> getClientStatByStatus() {
        return clientStatByStatus;
    }

    public void setClientStatByStatus(Map<String, Long> clientStatByStatus) {
        this.clientStatByStatus = clientStatByStatus;
    }

    public Map<String, Long> getCertificateStatByStatus() {
        return certificateStatByStatus;
    }

    public void setCertificateStatByStatus(Map<String, Long> certificateStatByStatus) {
        this.certificateStatByStatus = certificateStatByStatus;
    }

    public Map<String, Long> getCertificateStatByComplianceStatus() {
        return certificateStatByComplianceStatus;
    }

    public void setCertificateStatByComplianceStatus(Map<String, Long> certificateStatByComplianceStatus) {
        this.certificateStatByComplianceStatus = certificateStatByComplianceStatus;
    }

    public StatisticsDto() {
        super();
    }


}
