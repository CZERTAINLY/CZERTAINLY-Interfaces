package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.certificate.entity.EntityDto;
import com.czertainly.api.model.core.certificate.entity.EntityRequestDto;
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
@RequestMapping("/v1/entities")
@Tag(name = "Certificate Entity API", description = "Certificate Entity API")
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
				)
		})

public interface EntityController {
	
	@Operation(summary = "List Entities")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "list of available Entities")})
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<EntityDto> listEntities();
	
	@Operation(summary = "Entity details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Entity details retrieved")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public EntityDto getEntity(@Parameter(description = "Entity UUID") @PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Create Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "New Entity created", content = @Content(schema = @Schema(implementation = UuidDto.class)))})
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> createEntity(@RequestBody EntityRequestDto request)
			throws AlreadyExistException, NotFoundException;
	
	@Operation(summary = "Update Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Entity updated")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public EntityDto updateEntity(@Parameter(description = "Entity UUID") @PathVariable String uuid,
								  @RequestBody EntityRequestDto request) throws NotFoundException;
	
	@Operation(summary = "Remove Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Entity removed")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeEntity(@Parameter(description = "Entity UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Remove multiple Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Entities removed")})
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkRemoveEntity(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Entity UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
			examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
												@RequestBody List<String> entityUuids) throws NotFoundException;
}
