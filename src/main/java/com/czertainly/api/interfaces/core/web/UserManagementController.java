package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.auth.UserDetailDto;
import com.czertainly.api.model.core.auth.UserDto;
import com.czertainly.api.model.core.auth.UserRequestDto;
import com.czertainly.api.model.core.auth.UserUpdateRequestDto;
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
@RequestMapping("/v1/users")
@Tag(name = "User Management API", description = "User Management API")
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
public interface UserManagementController {

	@Operation(summary = "List Users")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of users fetched")})
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<UserDto> listUsers();

	@Operation(summary = "Get user details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User details retrieved")})
	@RequestMapping(path = "/{userUuid}", method = RequestMethod.GET, produces = {"application/json"})
	public UserDetailDto getUser(@Parameter(description = "User UUID") @PathVariable String userUuid) throws NotFoundException;

	@Operation(summary = "Create User")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "User details retrieved")})
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UuidDto> createUser(@RequestBody UserRequestDto request) throws NotFoundException;

	@Operation(summary = "Update User")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User details updated")})
	@RequestMapping(path = "/{userUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public UserDto updateUser(@Parameter(description = "User UUID") @PathVariable String userUuid, @RequestBody UserUpdateRequestDto request) throws NotFoundException;

	@Operation(summary = "Delete User")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "User deleted")})
	@RequestMapping(path = "/{userUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@Parameter(description = "User UUID") @PathVariable String userUuid) throws NotFoundException;

	@Operation(summary = "Add roles to User")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User roles updated")})
	@RequestMapping(path = "/{userUuid}/roles", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
	public UserDetailDto updateRoles(@Parameter(description = "User UUID") @PathVariable String userUuid, @io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Role UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
			examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
	 		@RequestBody List<String> roleUuids) throws NotFoundException;

	@Operation(summary = "Add role to User")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User roles updated")})
	@RequestMapping(path = "/{userUuid}/roles/{roleUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public UserDetailDto addRole(@Parameter(description = "User UUID") @PathVariable String userUuid, @Parameter(description = "Role UUID") @PathVariable String roleUuid) throws NotFoundException;
}
