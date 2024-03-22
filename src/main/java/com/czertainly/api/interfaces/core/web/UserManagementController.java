package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AttributeException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.auth.AddUserRequestDto;
import com.czertainly.api.model.client.auth.UpdateUserRequestDto;
import com.czertainly.api.model.client.auth.UserIdentificationRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.core.auth.RoleDto;
import com.czertainly.api.model.core.auth.SubjectPermissionsDto;
import com.czertainly.api.model.core.auth.UserDetailDto;
import com.czertainly.api.model.core.auth.UserDto;
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
@RequestMapping("/v1/users")
@Tag(name = "User Management", description = "User Management API")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))
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
                        content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content
                )
        })
public interface UserManagementController {

    @Operation(summary = "List Users")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of users fetched")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	List<UserDto> listUsers();

    @Operation(summary = "Get user details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User details retrieved")})
    @RequestMapping(path = "/{userUuid}", method = RequestMethod.GET, produces = {"application/json"})
	UserDetailDto getUser(@Parameter(description = "User UUID") @PathVariable String userUuid) throws NotFoundException;

    @Operation(summary = "Create User")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User details retrieved")})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<UserDetailDto> createUser(@RequestBody AddUserRequestDto request) throws NotFoundException, CertificateException, AttributeException;

    @Operation(summary = "Update User")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User details updated")})
    @RequestMapping(path = "/{userUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	UserDetailDto updateUser(@Parameter(description = "User UUID") @PathVariable String userUuid, @RequestBody UpdateUserRequestDto request) throws NotFoundException, CertificateException, AttributeException;

    @Operation(summary = "Enable User")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User enabled")})
    @RequestMapping(path = "/{userUuid}/enable", method = RequestMethod.PATCH, produces = {"application/json"})
	UserDetailDto enableUser(@Parameter(description = "User UUID") @PathVariable String userUuid) throws NotFoundException;

    @Operation(summary = "Disable User")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User disabled")})
    @RequestMapping(path = "/{userUuid}/disable", method = RequestMethod.PATCH, produces = {"application/json"})
	UserDetailDto disableUser(@Parameter(description = "User UUID") @PathVariable String userUuid) throws NotFoundException;

    @Operation(summary = "Delete User")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "User deleted")})
    @RequestMapping(path = "/{userUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteUser(@Parameter(description = "User UUID") @PathVariable String userUuid) throws NotFoundException;

    @Operation(summary = "Get User Roles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User roles retrieved")})
    @RequestMapping(path = "/{userUuid}/roles", method = RequestMethod.GET, produces = {"application/json"})
	List<RoleDto> getUserRoles(@Parameter(description = "User UUID") @PathVariable String userUuid) throws NotFoundException;

    @Operation(summary = "Add roles to User")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User roles updated")})
    @RequestMapping(path = "/{userUuid}/roles", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
	UserDetailDto updateRoles(@Parameter(description = "User UUID") @PathVariable String userUuid, @io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Role UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
			examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
	@RequestBody List<String> roleUuids) throws NotFoundException;

    @Operation(summary = "Add role to User")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User roles updated")})
    @RequestMapping(path = "/{userUuid}/roles/{roleUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	UserDetailDto addRole(@Parameter(description = "User UUID") @PathVariable String userUuid, @Parameter(description = "Role UUID") @PathVariable String roleUuid) throws NotFoundException;

    @Operation(summary = "Remove role from User")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User roles removed")})
    @RequestMapping(path = "/{userUuid}/roles/{roleUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
	UserDetailDto removeRole(@Parameter(description = "User UUID") @PathVariable String userUuid, @Parameter(description = "Role UUID") @PathVariable String roleUuid) throws NotFoundException;

    @Operation(summary = "Get User permissions")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User permissions removed")})
    @RequestMapping(path = "/{userUuid}/permissions", method = RequestMethod.GET, produces = {"application/json"})
	SubjectPermissionsDto getPermissions(@Parameter(description = "User UUID") @PathVariable String userUuid) throws NotFoundException;


    @Operation(summary = "Identify User")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User identified")})
    @RequestMapping(path = "/identify", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    UserDetailDto identifyUser(@RequestBody UserIdentificationRequestDto request) throws NotFoundException, CertificateException;

}
