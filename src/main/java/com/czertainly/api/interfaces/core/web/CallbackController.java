package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.RequestAttributeCallback;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Tag(name = "Callback API", description = "Callback API")
@ApiResponses(
		value = {
				@ApiResponse(
						responseCode = "400",
						description = "Bad Request",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
				@ApiResponse(
						responseCode = "404",
						description = "Not Found",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
				@ApiResponse(
						responseCode = "500",
						description = "Internal Server Error",
						content = @Content
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

public interface CallbackController {

	@Operation(summary = "Connector Callback API", description = "API to trigger the Callback for Connector.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Callback executed")})
	@RequestMapping(path = "/connectors/{uuid}/{functionGroup}/{kind}/callback", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Object callback(@Parameter(description = "Connector UUID") @PathVariable String uuid,
						   @Parameter(description = "Function Group") @PathVariable String functionGroup,
						   @Parameter(description = "Kind") @PathVariable String kind,
						   @RequestBody RequestAttributeCallback callback) throws NotFoundException, ConnectorException, ValidationException;

	@Operation(summary = "RA Profile Callback API", description = "API to trigger the Callback for RA Profile.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Callback executed")})
	@RequestMapping(path = "/{authorityUuid}/callback", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Object raProfileCallback(@Parameter(description = "Authority instance UUID") @PathVariable String authorityUuid,
						   @RequestBody RequestAttributeCallback callback) throws NotFoundException, ConnectorException, ValidationException;

	}
