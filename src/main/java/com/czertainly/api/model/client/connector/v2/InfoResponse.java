package com.czertainly.api.model.client.connector.v2;

import com.czertainly.api.model.core.connector.BaseFunctionGroupDto;
import com.czertainly.api.model.core.connector.EndpointDto;
import com.czertainly.api.model.core.connector.FunctionGroupCode;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Data
public class InfoResponse {

    private ConnectorInfo connectorInfo;
}
