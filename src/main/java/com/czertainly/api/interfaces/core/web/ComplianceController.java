package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.AttributeException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.client.compliance.v2.ComplianceProfileGroupsPatchRequestDto;
import com.czertainly.api.model.client.compliance.v2.ComplianceProfileRequestDto;
import com.czertainly.api.model.client.compliance.v2.ComplianceProfileRulesPatchRequestDto;
import com.czertainly.api.model.client.compliance.v2.ComplianceProfileUpdateRequestDto;
import com.czertainly.api.model.common.BulkActionMessageDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.compliance.v2.ComplianceGroupListDto;
import com.czertainly.api.model.core.compliance.v2.ComplianceProfileDto;
import com.czertainly.api.model.core.compliance.v2.ComplianceProfileListDto;
import com.czertainly.api.model.core.compliance.v2.ComplianceRuleListDto;
import com.czertainly.api.model.core.other.ResourceObjectDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/v2/compliance")
@Tag(name = "Compliance Profile Management v2", description = "Compliance Profile Management v2 API")
@ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "502", description = "Connector Error", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "503", description = "Connector Communication Error", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),})
public interface ComplianceController extends AuthProtectedController {

    @Operation(summary = "Initiate Certificate Compliance Check for requested compliance profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance check initiated")})
    @PostMapping(produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkCompliance(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Compliance Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UUID.class)))) @RequestBody List<UUID> uuids, @Parameter(description = "Filter objects checked by compliance based on its resource and evaluate only rules applicable to that resource") @RequestParam(required = false) Resource resource, @Parameter(description = "Filter objects checked by compliance based on its type and evaluate only rules applicable to that resource type", example = "X.509") @RequestParam(required = false) String type);

    @Operation(summary = "Initiate Certificate Compliance Check for requested resource objects")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance check initiated")})
    @PostMapping(path = "/{resource}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkResourceObjectCompliance(@Parameter(description = "Resource", required = true, example = Resource.Codes.RA_PROFILE) @PathVariable Resource resource, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Resource object UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UUID.class)))) @RequestBody List<UUID> objectUuids);

}
