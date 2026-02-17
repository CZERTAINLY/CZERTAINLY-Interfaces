package com.czertainly.api.clients;

import com.czertainly.api.model.client.attribute.ResponseAttribute;
import com.czertainly.api.model.core.connector.AuthType;
import com.czertainly.api.model.core.connector.ConnectorStatus;

import java.util.List;

public interface ApiClientConnectorInfo {

    String getUuid();

    String getName();

    String getUrl();

    ConnectorStatus getStatus();

    AuthType getAuthType();

    List<ResponseAttribute> getAuthAttributes();

}
