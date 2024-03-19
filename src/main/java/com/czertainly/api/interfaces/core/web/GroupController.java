package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.AttributeException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.certificate.group.GroupDto;
import com.czertainly.api.model.core.certificate.group.GroupRequestDto;
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
@RequestMapping("/v1/groups")
@Tag(name = "Certificate & Key Group", description = "Group API")
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

public interface GroupController {
	@Operation(summary = "List Groups")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "list of available Group")})
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<GroupDto> listGroups();
	
	@Operation(summary = "Group details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Group details retrieved") })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public GroupDto getGroup(@PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Create Group")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Group created", content = @Content(schema = @Schema(implementation = UuidDto.class)))})
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> createGroup(@RequestBody GroupRequestDto request)
            throws AlreadyExistException, NotFoundException, AttributeException;
	
	@Operation(summary = "Edit Group")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Group updated")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public GroupDto editGroup(@Parameter(description = "Group UUID") @PathVariable String uuid, @RequestBody GroupRequestDto request)
			throws NotFoundException, AttributeException;
	
	@Operation(summary = "Delete Group")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Group deleted") })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGroup(@Parameter(description = "Group UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Delete multiple Groups")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Groups deleted") })
	@RequestMapping(method = RequestMethod.DELETE, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkDeleteGroup(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Group UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
			examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
											   @RequestBody List<String> groupUuids) throws NotFoundException;

}
