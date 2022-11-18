package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.client.metadata.MetadataResponseDto;
import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.core.certificate.group.GroupDto;
import com.czertainly.api.model.core.compliance.ComplianceStatus;
import com.czertainly.api.model.core.location.LocationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CertificateDto {

    @Schema(description = "UUID of the Certificate",
            required = true)
    private String uuid;
    @Schema(description = "Certificate common name",
            required = true)
    private String commonName;
    @Schema(description = "Certificate serial number",
            required = true)
    private String serialNumber;
    @Schema(description = "Certificate issuer common name",
            required = true)
    private String issuerCommonName;
    @Schema(description = "Base64 encoded Certificate content",
            required = true)
    private String certificateContent;
    @Schema(description = "Issuer DN of the Certificate",
            required = true)
    private String issuerDn;
    @Schema(description = "Subject DN of the Certificate",
            required = true)
    private String subjectDn;
    @Schema(description = "Certificate validity start date",
            required = true)
    private Date notBefore;
    @Schema(description = "Certificate expiration date",
            required = true)
    private Date notAfter;
    @Schema(description = "Public key algorithm",
            required = true)
    private String publicKeyAlgorithm;
    @Schema(description = "Certificate signature algorithm",
            required = true)
    private String signatureAlgorithm;
    @Schema(description = "Certificate key size",
            required = true)
    private Integer keySize;
    @Schema(description = "Extended key usages")
    private List<String> extendedKeyUsage;
    @Schema(description = "Key usages",
            required = true)
    private List<String> keyUsage;
    @Schema(description = "Basic Constraints",
            required = true)
    private String basicConstraints;
    @Schema(description = "Certificate metadata")
    private List<MetadataResponseDto> metadata;
    @Schema(description = "Status of the Certificate",
            required = true)
    private CertificateStatus status;
    @Schema(description = "RA Profile associated to the Certificate")
    private SimplifiedRaProfileDto raProfile;
    @Schema(description = "SHA256 fingerprint of the Certificate", required = true)
    private String fingerprint;
    @Schema(description = "Subject alternative names")
    private Map<String, Object> subjectAlternativeNames;
    @Schema(description = "Locations associated to the Certificate")
    private Set<LocationDto> locations;
    @Schema(description = "Group associated to the Certificate")
    private GroupDto group;
    @Schema(description = "Certificate Owner")
    private String owner;
    @Schema(description = "Certificate type")
    private CertificateType certificateType;
    @Schema(description = "Serial number of the issuer")
    private String issuerSerialNumber;
    @Schema(description = "Certificate compliance check result")
    private List<CertificateComplianceResultDto> nonCompliantRules;
    @Schema(description = "Certificate compliance status")
    private ComplianceStatus complianceStatus;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIssuerCommonName() {
        return issuerCommonName;
    }

    public void setIssuerCommonName(String issuerCommonName) {
        this.issuerCommonName = issuerCommonName;
    }

    public String getCertificateContent() {
        return certificateContent;
    }

    public void setCertificateContent(String certificateContent) {
        this.certificateContent = certificateContent;
    }

    public String getIssuerDn() {
        return issuerDn;
    }

    public void setIssuerDn(String issuerDn) {
        this.issuerDn = issuerDn;
    }

    public String getSubjectDn() {
        return subjectDn;
    }

    public void setSubjectDn(String subjectDn) {
        this.subjectDn = subjectDn;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }

    public String getPublicKeyAlgorithm() {
        return publicKeyAlgorithm;
    }

    public void setPublicKeyAlgorithm(String publicKeyAlgorithm) {
        this.publicKeyAlgorithm = publicKeyAlgorithm;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String getBasicConstraints() {
        return basicConstraints;
    }

    public void setBasicConstraints(String basicConstraints) {
        this.basicConstraints = basicConstraints;
    }

    public List<MetadataResponseDto> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataResponseDto> metadata) {
        this.metadata = metadata;
    }

    public CertificateStatus getStatus() {
        return status;
    }

    public void setStatus(CertificateStatus status) {
        this.status = status;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Map<String, Object> getSubjectAlternativeNames() {
        return subjectAlternativeNames;
    }

    public void setSubjectAlternativeNames(Map<String, Object> subjectAlternativeNames) {
        this.subjectAlternativeNames = subjectAlternativeNames;
    }

    public Integer getKeySize() {
        return keySize;
    }

    public void setKeySize(Integer keySize) {
        this.keySize = keySize;
    }

    public List<String> getExtendedKeyUsage() {
        return extendedKeyUsage;
    }

    public void setExtendedKeyUsage(List<String> extendedKeyUsage) {
        this.extendedKeyUsage = extendedKeyUsage;
    }

    public List<String> getKeyUsage() {
        return keyUsage;
    }

    public void setKeyUsage(List<String> keyUsage) {
        this.keyUsage = keyUsage;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public SimplifiedRaProfileDto getRaProfile() {
        return raProfile;
    }

    public void setRaProfile(SimplifiedRaProfileDto raProfileDTO) {
        this.raProfile = raProfileDTO;
    }

    public Set<LocationDto> getLocations() {
        return locations;
    }

    public void setLocations(Set<LocationDto> locations) {
        this.locations = locations;
    }

    public GroupDto getGroup() {
        return group;
    }

    public void setGroup(GroupDto group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("commonName", commonName)
                .append("serialNumber", serialNumber)
                .toString();
    }

    public String getIssuerSerialNumber() {
        return issuerSerialNumber;
    }

    public void setIssuerSerialNumber(String issuerSerialNumber) {
        this.issuerSerialNumber = issuerSerialNumber;
    }

    public List<CertificateComplianceResultDto> getNonCompliantRules() {
        return nonCompliantRules;
    }

    public void setNonCompliantRules(List<CertificateComplianceResultDto> nonCompliantRules) {
        this.nonCompliantRules = nonCompliantRules;
    }

    public ComplianceStatus getComplianceStatus() {
        return complianceStatus;
    }

    public void setComplianceStatus(ComplianceStatus complianceStatus) {
        this.complianceStatus = complianceStatus;
    }
}
