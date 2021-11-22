package com.czertainly.api.model.discovery;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.Identified;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DiscoveryHistoryDto implements Identified {

    @Schema(
            description = "Id of the discovery",
            //example = "123456",
            required = true
    )
    private Long id;
    @Schema(
            description = "UUID of the discovery",
            example = "7776f44d-eb8d-4975-bdeb-d8ec3a84488f",
            required = true
    )
    private String uuid;
    @Schema(
            description = "Name of the discovery",
            example = "Test discovery",
            required = true
    )
    private String name;

    @Schema(
            description = "Type of the discovery",
            example = "IP-HostName-discovery",
            required = true
    )
    private String discoveryType;

    @Schema(
            description = "Status of discovery",
            //example = "IN_PROGRESS",
            required = true
    )
    private DiscoveryStatus status;

    @Schema(
            description = "Failure/Success Messages",
            example = "Failed due to network connectivity issues",
            required = false
    )
    private String message;

    @Schema(
            description = "Date and time when discovery started",
            //example = "2021-08-18T13:08:51",
            nullable = true
    )
    private Date startTime;
    @Schema(
            description = "Date and time when discovery finished",
            //example = "2021-08-18T13:08:51",
            nullable = true
    )
    private Date endTime;
    @Schema(
            description = "Total number of certificates that are discovered",
            //example = "Test discovery",
            defaultValue = "0"
    )
    private Integer totalCertificatesDiscovered;
    @Schema(
            description = "ID of the Discovery Connector",
            //example = "1",
            required = true
    )
    private Long connectorId;
    
    @Schema(
            description = "Name of the Discovery Connector",
            //example = "1",
            required = true
    )
    private String connectorName;
    @Schema(
            description = "List of discovered certificates",
            required = true
    )
    private List<DiscoveryCertificatesDto> certificate;
    @Schema(
            description = "List of discovery attributes",
            required = true
    )
    private List<AttributeDefinition> attributes;
    @Schema(
            description = "Meta data of the discovery"
    )
    private Map<String, Object> meta;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
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

    public Long getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(Long connectorId) {
        this.connectorId = connectorId;
    }

    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDefinition> attributes) {
        this.attributes = attributes;
    }

    public String getDiscoveryType() {
        return discoveryType;
    }

    public void setDiscoveryType(String discoveryType) {
        this.discoveryType = discoveryType;
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

	@Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id).append("uuid", uuid)
                .append("totalCertificatesDiscovered", totalCertificatesDiscovered).toString();
    }


}
