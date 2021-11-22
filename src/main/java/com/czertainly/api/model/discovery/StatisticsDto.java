package com.czertainly.api.model.discovery;

import java.util.Map;

public class StatisticsDto {
	// Total Count of the objects. This can be used by the FE for percentage calculation
	private Long totalCertificates;
	private Long totalGroups;
	private Long totalEntities;
	private Long totalDiscoveries;
	private Long totalConnectors;
	private Long totalRaProfiles;
	private Long totalCredentials;
	private Long totalAuthorities;
	private Long totalAdministrators;
	private Long totalClients;
	
	// Data of the certificate count by group, entity and RA Profile assignment
	private Map<String, Long> groupStatByCertificateCount;
	private Map<String, Long> entityStatByCertificateCount;
	private Map<String, Long> raProfileStatByCertificateCount;
	
	//Certificate related statistics
	private Map<String, Long> certificateStatByType;
	private Map<String, Long> certificateStatByExpiry;
	private Map<String, Long> certificateStatByKeySize;
	private Map<String, Long> certificateStatByBasicConstraints;
	private Map<String, Long> certificateStatByStatus;
	
	//Other Entities by Status
	private Map<String, Long> connectorStatByStatus;
	private Map<String, Long> raProfileStatByStatus;
	private Map<String, Long> administratorStatByStatus;
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
	public Long getTotalEntities() {
		return totalEntities;
	}
	public void setTotalEntities(Long totalEntities) {
		this.totalEntities = totalEntities;
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
	public Map<String, Long> getEntityStatByCertificateCount() {
		return entityStatByCertificateCount;
	}
	public void setEntityStatByCertificateCount(Map<String, Long> entityStatByCertificateCount) {
		this.entityStatByCertificateCount = entityStatByCertificateCount;
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
	public StatisticsDto() {
		super();
	}
	
	
}
