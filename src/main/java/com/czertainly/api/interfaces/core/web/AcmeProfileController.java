package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.acme.AcmeProfileEditRequestDto;
import com.czertainly.api.model.client.acme.AcmeProfileRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.BulkActionMessageDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.acme.AcmeProfileDto;
import com.czertainly.api.model.core.acme.AcmeProfileListDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/acmeProfiles")
@Tag(name = "ACME Profile Management", description = "ACME Profile Management API")
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
public interface AcmeProfileController {

	@Operation(summary = "Get list of ACME Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Profile list retrieved")})
	@RequestMapping(produces = {"application/json"}, method = RequestMethod.GET)
	public List<AcmeProfileListDto> listAcmeProfiles();

	@Operation(summary = "Get details of ACME Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Profile details retrieved") })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = { "application/json" })
	public AcmeProfileDto getAcmeProfile(@Parameter(description = "ACME Profile UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Create ACME Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "ACME Profile created") })
	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<UuidDto> createAcmeProfile(@RequestBody AcmeProfileRequestDto request) throws AlreadyExistException, ValidationException, ConnectorException, AttributeException;

	@Operation(summary = "Edit ACME Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Profile updated") })
	@RequestMapping(path="/{uuid}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = { "application/json" })
	public AcmeProfileDto editAcmeProfile(@Parameter(description = "ACME Profile UUID") @PathVariable String uuid, @RequestBody AcmeProfileEditRequestDto request) throws ConnectorException, AttributeException;

	@Operation(summary = "Delete ACME Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "ACME Profile deleted") })
	@RequestMapping(path="/{uuid}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAcmeProfile(@Parameter(description = "ACME Profile UUID") @PathVariable String uuid) throws NotFoundException, ValidationException;

	@Operation(summary = "Enable ACME Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Profile enabled") })
	@RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PATCH, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void enableAcmeProfile(@Parameter(description = "ACME Profile UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Disable ACME Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Profile disabled") })
	@RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PATCH, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disableAcmeProfile(@Parameter(description = "ACME Profile UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Enable multiple ACME Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Profiles enabled") })
	@RequestMapping(path = "/enable", method = RequestMethod.PATCH, consumes = { "application/json" }, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkEnableAcmeProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "ACME Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
			examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<String> uuids);

	@Operation(summary = "Disable multiple ACME Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Profiles disabled") })
	@RequestMapping(path = "/disable", method = RequestMethod.PATCH, consumes = { "application/json" }, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkDisableAcmeProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "ACME Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
			examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<String> uuids);

	@Operation(summary = "Delete multiple ACME Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Profiles deleted") })
	@RequestMapping(path = "/delete", method = RequestMethod.DELETE, consumes = { "application/json" }, produces = { "application/json" })
	public List<BulkActionMessageDto> bulkDeleteAcmeProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "ACME Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
			examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<String> uuids);

	@Operation(summary = "Force delete multiple ACME Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Profiles forced to delete"),
			@ApiResponse(responseCode = "422", description = "Unprocessible Entity",content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
	@RequestMapping(path = "/delete/force", method = RequestMethod.DELETE, produces = {"application/json"})
	public List<BulkActionMessageDto> forceDeleteACMEProfiles(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "ACME Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
			examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<String> uuids) throws NotFoundException, ValidationException;

	@Operation(summary = "Update RA Profile for ACME Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "RA Profile updated") })
	@RequestMapping(path = "/{uuid}/raprofile/{raProfileUuid}", method = RequestMethod.PATCH, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateRaProfile(@Parameter(description = "ACME Profile UUID") @PathVariable String uuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;
}
