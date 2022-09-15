package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.auth.PermissionDto;
import com.czertainly.api.model.core.auth.RoleDetailDto;
import com.czertainly.api.model.core.auth.RoleDto;
import com.czertainly.api.model.core.auth.RoleRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping("/v1/roles")
@Tag(name = "Role Management API", description = "Role Management API")
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
public interface RoleManagementController {

	@Operation(summary = "List Roles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of roles fetched")})
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<RoleDto> listRoles();

	@Operation(summary = "Get role details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Role details retrieved")})
	@RequestMapping(path = "/{roleUuid}", method = RequestMethod.GET, produces = {"application/json"})
	public RoleDetailDto getRole(@Parameter(description = "Role UUID") @PathVariable String roleUuid) throws NotFoundException;

	@Operation(summary = "Create Role")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Role created")})
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UuidDto> createRole(@RequestBody RoleRequestDto request) throws NotFoundException;

	@Operation(summary = "Update Role")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Role details updated")})
	@RequestMapping(path = "/{roleUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public RoleDto updateRole(@Parameter(description = "Role UUID") @PathVariable String roleUuid, @RequestBody RoleRequestDto request) throws NotFoundException;

	@Operation(summary = "Delete Role")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Role deleted")})
	@RequestMapping(path = "/{roleUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRole(@Parameter(description = "Role UUID") @PathVariable String roleUuid) throws NotFoundException;

	@Operation(summary = "Get Permissions of a Role")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Role permissions retrieved")})
	@RequestMapping(path = "/{roleUuid}/permissions", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public List<PermissionDto> getRolePermissions(@Parameter(description = "Role UUID") @PathVariable String userUuid) throws NotFoundException;

	@Operation(summary = "Add permissions to Role")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User roles updated")})
	@RequestMapping(path = "/{roleUuid}/permissions", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public RoleDetailDto addPermissions(@Parameter(description = "Role UUID") @PathVariable String userUuid, @RequestBody PermissionDto request) throws NotFoundException;
}
