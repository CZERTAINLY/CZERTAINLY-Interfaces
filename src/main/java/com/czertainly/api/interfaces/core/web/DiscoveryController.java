package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.certificate.DiscoveryResponseDto;
import com.czertainly.api.model.client.certificate.SearchRequestDto;
import com.czertainly.api.model.client.discovery.DiscoveryCertificateResponseDto;
import com.czertainly.api.model.client.discovery.DiscoveryDto;
import com.czertainly.api.model.client.discovery.DiscoveryHistoryDetailDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.search.SearchFieldDataByGroupDto;
import com.czertainly.api.model.core.scheduler.ScheduleDiscoveryDto;
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

import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping("/v1/discoveries")
@Tag(name = "Discovery Management", description = "Discovery Management API")
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

public interface DiscoveryController {
	
	@Operation(summary = "List Discovery")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of available Discoveries")})
	@RequestMapping(path = "/list", method = RequestMethod.POST, produces = {"application/json"})
	DiscoveryResponseDto listDiscoveries(@RequestBody SearchRequestDto request);
	
	@Operation(summary = "Discovery Details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Discovery details retrieved")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	DiscoveryHistoryDetailDto getDiscovery(@Parameter(description = "Discovery UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Discovery Details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Discovery details retrieved")})
	@RequestMapping(path = "/{uuid}/certificates", method = RequestMethod.GET, produces = {"application/json"})
	DiscoveryCertificateResponseDto getDiscoveryCertificates(
			@Parameter(description = "Discovery UUID") @PathVariable String uuid,
			@RequestParam(required = false) Boolean newlyDiscovered,
			@RequestParam(required = false, defaultValue = "10") int itemsPerPage,
			@RequestParam(required = false, defaultValue = "0") int pageNumber
	) throws NotFoundException;
	
	@Operation(summary = "Create Discovery")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Discovery Created", content = @Content(schema = @Schema(implementation = UuidDto.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}))})
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	ResponseEntity<?> createDiscovery(@RequestBody DiscoveryDto request)
            throws AlreadyExistException, NotFoundException, CertificateException, InterruptedException, ConnectorException, AttributeException;
	
	@Operation(summary = "Delete Discovery")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Discovery deleted")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteDiscovery(@Parameter(description = "Discovery UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Delete Multiple Discoveries")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Discoveries deleted")})
	@RequestMapping(method = RequestMethod.DELETE, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void bulkDeleteDiscovery(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Discovery UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
			examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
										@RequestBody List<String> discoveryUuids) throws NotFoundException;

	@Operation(summary = "Get Discovery searchable fields information")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Discovery searchable field information retrieved")})
	@RequestMapping(path = "/search", method = RequestMethod.GET, produces = {"application/json"})
	List<SearchFieldDataByGroupDto> getSearchableFieldInformation();

	@Operation(summary = "Schedule Discovery")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Discovery Scheduled", content = @Content(schema = @Schema(implementation = UuidDto.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}))})
	@RequestMapping(path = "/schedule", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	ResponseEntity<?> scheduleDiscovery(@RequestBody ScheduleDiscoveryDto scheduleDiscoveryDto)
			throws AlreadyExistException, CertificateException, InterruptedException, ConnectorException, SchedulerException, AttributeException;

}
