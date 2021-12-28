package com.czertainly.api.interfaces.core.web;

import java.util.List;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.connector.ForceDeleteMessageDto;
import com.czertainly.api.model.core.credential.CredentialDto;
import com.czertainly.api.model.core.credential.CredentialRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/credentials")
@Tag(name = "Credential Management API", description = "Credential Management API")

public interface CredentialController {

	@Operation(summary = "List of All Credentials")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of all Credentials"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public List<CredentialDto> listCredentials();

	@Operation(summary = "Details of a Credentials")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Credential details retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = { "application/json" })
	public CredentialDto getCredential(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Add a new Credential")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "New Credential added"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> createCredential(@RequestBody CredentialRequestDto request)
			throws AlreadyExistException, NotFoundException, ConnectorException;

	@Operation(summary = "Update a Credential")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Credential updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public CredentialDto updateCredential(@PathVariable String uuid, @RequestBody CredentialRequestDto request)
			throws NotFoundException, ConnectorException;

	@Operation(summary = "Remove a Credential")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Credential deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeCredential(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Enable a Credential")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Credential enabled"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PUT, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void enableCredential(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Disable a Credential")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Credential disabled"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PUT, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disableCredential(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Delete multiple Credentials")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Credentials deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.DELETE)
	public List<ForceDeleteMessageDto> bulkRemoveCredential(@RequestBody List<String> uuids) throws NotFoundException, ValidationException;

	@Operation(summary = "Force delete multiple Credentials")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Credentials forced to delete"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/force", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkForceRemoveCredential(@RequestBody List<String> uuids) throws NotFoundException, ValidationException;

}
