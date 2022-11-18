package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.client.entity.EntityInstanceRequestDto;
import com.czertainly.api.model.client.entity.EntityInstanceUpdateRequestDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.core.entity.EntityInstanceDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/entities")
@Tag(name = "Entity Management", description = "Entity Management API")
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
public interface EntityInstanceController {

	@Operation(
			summary = "List Entity instances"
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "List of Entity instances"
					)
			})
	@RequestMapping(
			method = RequestMethod.GET,
			produces = {"application/json"}
	)
	List<EntityInstanceDto> listEntityInstances();

	@Operation(
			summary = "Get Entity instance details"
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Authority instance details retrieved"
					)
			})
	@RequestMapping(
			path = "/{entityUuid}",
			method = RequestMethod.GET,
			produces = {"application/json"}
	)
	EntityInstanceDto getEntityInstance(
			@Parameter(description = "Entity instance UUID") @PathVariable String entityUuid
	) throws ConnectorException;

	@Operation(
			summary = "Add Entity instance"
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "201",
							description = "Entity instance created",
							content = @Content(schema = @Schema(implementation = UuidDto.class))
					),
					@ApiResponse(
							responseCode = "422",
							description = "Unprocessable Entity",
							content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
							examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")})
					)
			})
	@RequestMapping(
			method = RequestMethod.POST,
			consumes = {"application/json"},
			produces = {"application/json"}
	)
	ResponseEntity<?> createEntityInstance(
			@RequestBody EntityInstanceRequestDto request
	) throws AlreadyExistException, ConnectorException;

	@Operation(
			summary = "Edit Entity instance"
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Authority instance details updated"
					),
					@ApiResponse(
							responseCode = "422",
							description = "Unprocessable Entity",
							content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
							examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")})
					)
			})
	@RequestMapping(
			path = "/{entityUuid}",
			method = RequestMethod.PUT,
			consumes = {"application/json"},
			produces = {"application/json"}
	)
	EntityInstanceDto editEntityInstance(
			@Parameter(description = "Entity instance UUID") @PathVariable String entityUuid,
			@RequestBody EntityInstanceUpdateRequestDto request
	) throws ConnectorException;

	@Operation(
			summary = "Delete Entity instance"
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "204",
							description = "Entity instance deleted"
					)
			})
	@RequestMapping(
			path = "/{entityUuid}",
			method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteEntityInstance(
			@Parameter(description = "Entity instance UUID") @PathVariable String entityUuid
	) throws ConnectorException;

	@Operation(
			summary = "List Location Attributes"
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Location attributes retrieved"
					)
			})
	@RequestMapping(
			path = "/{entityUuid}/attributes/location",
			method = RequestMethod.GET,
			produces = {"application/json"}
	)
	List<BaseAttribute> listLocationAttributes(
			@Parameter(description = "Entity instance UUID") @PathVariable String entityUuid
	) throws ConnectorException;

	@Operation(
			summary = "Validate Location Attributes"
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Attributes validated"
					),
					@ApiResponse(
							responseCode = "422",
							description = "Unprocessable Entity",
							content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
							examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})
					)
			})
	@RequestMapping(
			path = "/{entityUuid}/attributes/location/validate",
			method = RequestMethod.POST,
			consumes = {"application/json"}
	)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void validateLocationAttributes(
			@Parameter(description = "Entity instance UUID") @PathVariable String entityUuid,
			@RequestBody List<RequestAttributeDto> attributes
	) throws ConnectorException;
}
