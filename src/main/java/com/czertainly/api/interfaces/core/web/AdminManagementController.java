package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.admin.AddAdminRequestDto;
import com.czertainly.api.model.client.admin.EditAdminRequestDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.admin.AdminDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping("/v1/admins")
@Tag(name = "Admin Management API", description = "Admin Management API. "
		+ "Provides to Information regarding the Administrators in the platform "
		+ "With the Admin API, operations like addition, removal, enable etc... operations can be performed ")
@ApiResponses(
		value = {
				@ApiResponse(
						responseCode = "400",
						description = "Bad Request",
						content = @Content
				),
				@ApiResponse(
						responseCode = "404",
						description = "Not Found",
						content = @Content
				),
				@ApiResponse(
						responseCode = "500",
						description = "Internal Server Error",
						content = @Content
				)
		})
public interface AdminManagementController {

	@Operation(summary = "List available administrators")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of administrator")})
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<AdminDto> listAdmins();

	@Operation(summary = "Create a new Administrator")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "New administrator created",  content = @Content(schema = @Schema(implementation = UuidDto.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = ValidationException.class))), })
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> addAdmin(@RequestBody AddAdminRequestDto request)
			throws CertificateException, AlreadyExistException, ValidationException, NotFoundException;

	@Operation(summary = "Get details of an Administrator")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Administrator details retrieved")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public AdminDto getAdmin(@Parameter(description = "Administrator UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Edit Administrator")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Administrator edit success")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public AdminDto editAdmin(@Parameter(description = "Administrator UUID") @PathVariable String uuid, @RequestBody EditAdminRequestDto request)
			throws CertificateException, NotFoundException, AlreadyExistException;

	@Operation(summary = "Remove Multiple Administrator")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Administrator removed")})
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkRemoveAdmin(@RequestBody List<String> adminUuids) throws NotFoundException;

	@Operation(summary = "Remove Administrator")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Administrator removed")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeAdmin(@Parameter(description = "Administrator UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Disable Administrator")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Disable administrator success")})
	@RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PUT, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disableAdmin(@Parameter(description = "Administrator UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Enable Administrator")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Enable administrator success")})
	@RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PUT, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void enableAdmin(@Parameter(description = "Administrator UUID") @PathVariable String uuid) throws NotFoundException, CertificateException;

	@Operation(summary = "Disable Multiple Administrator")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Disable administrator success")})
	@RequestMapping(path = "/disable", method = RequestMethod.PUT, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkDisableAdmin(@RequestBody List<String> adminUuids) throws NotFoundException;

	@Operation(summary = "Enable Multiple Administrator")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Enable administrator success")})
	@RequestMapping(path = "/enable", method = RequestMethod.PUT, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkEnableAdmin(@RequestBody List<String> adminUuids) throws NotFoundException;
}
