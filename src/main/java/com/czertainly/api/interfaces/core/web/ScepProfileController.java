package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.scep.ScepProfileEditRequestDto;
import com.czertainly.api.model.client.scep.ScepProfileRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.BulkActionMessageDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.certificate.CertificateDto;
import com.czertainly.api.model.core.scep.ScepProfileDetailDto;
import com.czertainly.api.model.core.scep.ScepProfileDto;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/scepProfiles")
@Tag(name = "SCEP Profile Management", description = "SCEP Profile Management API")
@ApiResponses(
		value = {
				@ApiResponse(
						responseCode = "400",
						description = "Bad Request",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
				@ApiResponse(
						responseCode = "401",
						description = "Unauthorized",
						content = @Content(schema = @Schema())
				),
				@ApiResponse(
						responseCode = "403",
						description = "Forbidden",
						content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))
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
				)
		})
public interface ScepProfileController {

	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------
	// List and Detail
	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------

	@Operation(
			summary = "Get list of SCEP Profiles"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "200", description = "SCEP Profile list retrieved")}
	)
	@RequestMapping(
			produces = {"application/json"},
			method = RequestMethod.GET
	)
	public List<ScepProfileDto> listScepProfiles();


	@Operation(
			summary = "Get details of SCEP Profile"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "200", description = "SCEP Profile details retrieved") }
	)
	@RequestMapping(
			path = "/{uuid}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ScepProfileDetailDto getScepProfile(
			@Parameter(description = "SCEP Profile UUID") @PathVariable String uuid
	) throws NotFoundException;

	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------
	// Create and Update
	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------

	@Operation(
			summary = "Create SCEP Profile"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "201", description = "SCEP Profile created") }
	)
	@RequestMapping(
			method = RequestMethod.POST,
			consumes = { "application/json" },
			produces = { "application/json" }
	)
	public ResponseEntity<ScepProfileDetailDto> createScepProfile(
			@RequestBody ScepProfileRequestDto request
	) throws AlreadyExistException, ValidationException, ConnectorException, AttributeException;


	@Operation(
			summary = "Edit SCEP Profile"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "200", description = "SCEP Profile updated") }
	)
	@RequestMapping(
			path="/{uuid}",
			method = RequestMethod.PUT,
			consumes = { "application/json" },
			produces = { "application/json" }
	)
	public ScepProfileDetailDto editScepProfile(
			@Parameter(description = "SCEP Profile UUID") @PathVariable String uuid,
			@RequestBody ScepProfileEditRequestDto request
	) throws ConnectorException, AttributeException;

	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------
	// Delete
	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------

	@Operation(
			summary = "Delete SCEP Profile"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "204", description = "SCEP Profile deleted") }
	)
	@RequestMapping(
			path="/{uuid}",
			method = RequestMethod.DELETE,
			produces = { "application/json" }
	)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteScepProfile(
			@Parameter(description = "SCEP Profile UUID") @PathVariable String uuid
	) throws NotFoundException, ValidationException;


	@Operation(
			summary = "Delete multiple SCEP Profiles"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "200", description = "SCEP Profiles deleted") }
	)
	@RequestMapping(
			path = "/delete",
			method = RequestMethod.DELETE,
			consumes = { "application/json" },
			produces = { "application/json" }
	)
	public List<BulkActionMessageDto> bulkDeleteScepProfile(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "SCEP Profile UUIDs",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
							examples={
									@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")
							})) @RequestBody List<String> uuids
	);

	@Operation(
			summary = "Force delete multiple SCEP Profiles"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "SCEP Profiles forced to delete"),
			@ApiResponse(responseCode = "422", description = "Unprocessible Entity",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
							examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))
	})
	@RequestMapping(
			path = "/delete/force",
			method = RequestMethod.DELETE,
			produces = {"application/json"}
	)
	public List<BulkActionMessageDto> forceDeleteScepProfiles(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "SCEP Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
			examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<String> uuids
	) throws NotFoundException, ValidationException;


	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------
	// Enable
	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------

	@Operation(
			summary = "Enable SCEP Profile"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "204", description = "SCEP Profile enabled") }
	)
	@RequestMapping(
			path = "/{uuid}/enable",
			method = RequestMethod.PATCH,
			produces = { "application/json" }
	)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void enableScepProfile(
			@Parameter(description = "SCEP Profile UUID") @PathVariable String uuid
	) throws NotFoundException;


	@Operation(
			summary = "Enable multiple SCEP Profiles"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "204", description = "SCEP Profiles enabled") }
	)
	@RequestMapping(
			path = "/enable",
			method = RequestMethod.PATCH,
			consumes = { "application/json" },
			produces = { "application/json" }
	)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkEnableScepProfile(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "SCEP Profile UUIDs",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
							examples={
									@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")
							})) @RequestBody List<String> uuids);

	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------
	// Disable
	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------
	@Operation(
			summary = "Disable SCEP Profile"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "204", description = "SCEP Profile disabled") }
	)
	@RequestMapping(
			path = "/{uuid}/disable",
			method = RequestMethod.PATCH,
			produces = { "application/json" }
	)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disableScepProfile(
			@Parameter(description = "SCEP Profile UUID") @PathVariable String uuid
	) throws NotFoundException;


	@Operation(
			summary = "Disable multiple SCEP Profile"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "204", description = "SCEP Profiles disabled") }
	)
	@RequestMapping(
			path = "/disable",
			method = RequestMethod.PATCH,
			consumes = { "application/json" },
			produces = { "application/json" }
	)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkDisableScepProfile(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "SCEP Profile UUIDs",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples={
							@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")
					})) @RequestBody List<String> uuids);


	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------
	// RA profile Assignment
	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------

	@Operation(
			summary = "Update RA Profile for SCEP Profile"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "200", description = "RA Profile updated") }
	)
	@RequestMapping(
			path = "/{uuid}/raProfiles/{raProfileUuid}",
			method = RequestMethod.PATCH,
			produces = { "application/json" }
	)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateRaProfile(
			@Parameter(description = "SCEP Profile UUID") @PathVariable String uuid,
			@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid
	) throws NotFoundException;

	@Operation(
			summary = "Get list of certificates eligible for CA certificate of SCEP requests"
	)
	@ApiResponses(
			value = { @ApiResponse(responseCode = "200", description = "List of CA certificates retrieved") }
	)
	@RequestMapping(
			path = "/caCertificates",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<CertificateDto> listScepCaCertificates(@Parameter(description = "flag to return certificates that are eligible for Intune integration") @RequestParam boolean intuneEnabled);
}
