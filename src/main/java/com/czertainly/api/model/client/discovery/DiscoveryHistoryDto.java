package com.czertainly.api.model.client.discovery;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

public class DiscoveryHistoryDto extends NameAndUuidDto {

    @Schema(
            description = "Discovery Kind",
            example = "IP-HostName",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String kind;

    @Schema(
            description = "Status of Discovery",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private DiscoveryStatus status;

    @Schema(
            description = "Status of Discovery returned by connector",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private DiscoveryStatus connectorStatus;


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
            description = "Number of certificates that were discovered by connector",
            defaultValue = "0"
    )
    private Integer connectorTotalCertificatesDiscovered;

    @Schema(
            description = "UUID of the Discovery Provider",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String connectorUuid;

    @Schema(
            description = "Name of the Discovery Provider",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String connectorName;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getConnectorUuid() {
        return connectorUuid;
    }

    public void setConnectorUuid(String connectorUuid) {
        this.connectorUuid = connectorUuid;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public Integer getConnectorTotalCertificatesDiscovered() {
        return connectorTotalCertificatesDiscovered;
    }

    public void setConnectorTotalCertificatesDiscovered(Integer connectorTotalCertificatesDiscovered) {
        this.connectorTotalCertificatesDiscovered = connectorTotalCertificatesDiscovered;
    }

    public DiscoveryStatus getConnectorStatus() {
        return connectorStatus;
    }

    public void setConnectorStatus(DiscoveryStatus connectorStatus) {
        this.connectorStatus = connectorStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("kind", kind)
                .append("status", status)
                .append("connectorStatus", connectorStatus)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .append("totalCertificatesDiscovered", totalCertificatesDiscovered)
                .append("connectorTotalCertificatesDiscovered", connectorTotalCertificatesDiscovered)
                .append("connectorUuid", connectorUuid)
                .append("connectorName", connectorName)
                .append("uuid", uuid)
                .append("name", name)
                .toString();
    }
}
