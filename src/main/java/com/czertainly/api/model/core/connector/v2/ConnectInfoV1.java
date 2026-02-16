package com.czertainly.api.model.core.connector.v2;

import com.czertainly.api.model.core.connector.FunctionGroupDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ConnectInfoV1 extends ConnectInfo {

    @Schema(description = "Function Group information of a connector", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<FunctionGroupDto> functionGroups;

}
