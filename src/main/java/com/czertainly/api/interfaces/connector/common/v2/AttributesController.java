package com.czertainly.api.interfaces.connector.common.v2;

import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.core.connector.FunctionGroupCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v2/{functionalGroup}/attributes")
@Tag(
        name = "Connector Attributes v2",
        description = "Connector Attributes API. " +
                "Provides information about supported Attributes of the connector. " +
                "Attributes are specific to implementation and gives information about the " +
                "data that can be exchanged and properly parsed by the connector."
)
public interface AttributesController extends AuthProtectedConnectorController {

    @GetMapping(
            produces = {"application/json"}
    )
    @Operation(
            summary = "List available Attributes",
            parameters = {
                    @Parameter(name = "functionalGroup", description = "Function Group", in = ParameterIn.PATH, schema = @Schema(implementation = FunctionGroupCode.class))
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attributes retrieved"
                    )
            }
    )
    List<BaseAttribute> listAttributeDefinitions();

}