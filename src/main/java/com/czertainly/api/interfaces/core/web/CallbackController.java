package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.common.callback.RequestAttributeCallback;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1")
@Tag(name = "Callback", description = "Callback API")
@ApiResponses(
		value = {
				@ApiResponse(
						responseCode = "404",
						description = "Not Found",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
				@ApiResponse(responseCode = "422",
						description = "Unprocessable Entity",
						content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
								examples = {
										@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")
								})
				),
				@ApiResponse(
						responseCode = "502",
						description = "Connector Error",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
				@ApiResponse(
						responseCode = "503",
						description = "Connector Communication Error",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
		})

public interface CallbackController extends AuthProtectedController {

	@Operation(summary = "Connector Callback API", description = "API to trigger the Callback for Connector.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Callback executed")})
	@RequestMapping(path = "/connectors/{uuid}/{functionGroup}/{kind}/callback", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Object callback(@Parameter(description = "Connector UUID") @PathVariable String uuid,
						   @Parameter(description = "Function Group") @PathVariable String functionGroup,
						   @Parameter(description = "Kind") @PathVariable String kind,
						   @RequestBody RequestAttributeCallback callback) throws NotFoundException, ConnectorException, ValidationException;

    @Operation(summary = "Resource Callback API", description = "API to trigger the Callback for resource.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Callback executed")})
	@RequestMapping(path = "/{resource}/{parentObjectUuid}/callback", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Object resourceCallback(
			@Parameter(description = "Name of the resource") @PathVariable Resource resource,
			@Parameter(description = "Parent Object UUID") @PathVariable String parentObjectUuid,
			@RequestBody RequestAttributeCallback callback)
			throws NotFoundException, ConnectorException, ValidationException;

	}
