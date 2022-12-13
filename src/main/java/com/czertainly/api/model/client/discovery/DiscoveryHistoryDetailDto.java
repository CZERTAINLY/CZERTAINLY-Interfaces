package com.czertainly.api.model.client.discovery;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.client.metadata.MetadataResponseDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.discovery.DiscoveryCertificatesDto;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscoveryHistoryDetailDto extends NameAndUuidDto {

    @Schema(
            description = "Discovery Kind",
            example = "IP-HostName",
            required = true
    )
    private String kind;

    @Schema(
            description = "Status of Discovery",
            required = true
    )
    private DiscoveryStatus status;

    @Schema(
            description = "Failure/Success Messages",
            example = "Failed due to network connectivity issues"
    )
    private String message;

    @Schema(
            description = "Date and time when Discovery started",
            nullable = true
    )
    private Date startTime;
    @Schema(
            description = "Date and time when Discovery finished",
            nullable = true
    )
    private Date endTime;
    @Schema(
            description = "Number of certificates that are discovered",
            defaultValue = "0"
    )
    private Integer totalCertificatesDiscovered;
    @Schema(
            description = "UUID of the Discovery Provider",
            required = true
    )
    private String connectorUuid;

    @Schema(
            description = "Name of the Discovery Provider",
            required = true
    )
    private String connectorName;
    @Schema(
            description = "List of Discovered Certificates",
            required = true
    )
    private List<DiscoveryCertificatesDto> certificate;
    @Schema(
            description = "List of Discovery Attributes",
            required = true
    )
    private List<ResponseAttributeDto> attributes = new ArrayList<>();

    @Schema(description = "List of Custom Attributes")
    private List<ResponseAttributeDto> customAttributes;

    @Schema(
            description = "Metadata of the Discovery"
    )
    private List<MetadataResponseDto> metadata;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscoveryStatus getStatus() {
        return status;
    }

    public void setStatus(DiscoveryStatus status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalCertificatesDiscovered() {
        return totalCertificatesDiscovered;
    }

    public void setTotalCertificatesDiscovered(Integer totalCertificatesDiscovered) {
        this.totalCertificatesDiscovered = totalCertificatesDiscovered;
    }

    public List<MetadataResponseDto> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataResponseDto> metadata) {
        this.metadata = metadata;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public List<DiscoveryCertificatesDto> getCertificate() {
        return certificate;
    }

    public void setCertificate(List<DiscoveryCertificatesDto> list) {
        this.certificate = list;
    }

    public String getConnectorUuid() {
        return connectorUuid;
    }

    public void setConnectorUuid(String connectorUuid) {
        this.connectorUuid = connectorUuid;
    }

    public List<ResponseAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ResponseAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public List<ResponseAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<ResponseAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("totalCertificatesDiscovered", totalCertificatesDiscovered).toString();
    }


}
