package com.czertainly.api.interfaces.core.web.v2;

import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/v2/compliance")
@Tag(name = "Compliance Management v2", description = "Compliance Management v2 API")
@ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "502", description = "Connector Error", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "503", description = "Connector Communication Error", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),})
public interface ComplianceController extends AuthProtectedController {

    @Operation(operationId = "checkComplianceV2", summary = "Initiate compliance check for requested compliance profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance check initiated")})
    @PostMapping(produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkCompliance(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Compliance Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UUID.class)))) @RequestBody List<UUID> uuids, @Parameter(description = "Filter objects checked by compliance based on its resource and evaluate only rules applicable to that resource") @RequestParam(required = false) Resource resource, @Parameter(description = "Filter objects checked by compliance based on its type and evaluate only rules applicable to that resource type", example = "X.509") @RequestParam(required = false) String type);

    @Operation(operationId = "checkResourceObjectsComplianceV2", summary = "Initiate compliance Check for requested resource objects")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance check initiated")})
    @PostMapping(path = "/{resource}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkResourceObjectsCompliance(@Parameter(description = "Resource", required = true, example = Resource.Codes.RA_PROFILE) @PathVariable Resource resource, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Resource object UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UUID.class)))) @RequestBody List<UUID> objectUuids);

}
