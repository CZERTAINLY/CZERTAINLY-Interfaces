package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.client.AddClientRequestDto;
import com.czertainly.api.model.client.client.EditClientRequestDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.client.ClientDto;
import com.czertainly.api.model.core.raprofile.RaProfileDto;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/v1/clients")
@Tag(name = "Client Management API", description = "Client Management API")
public interface ClientManagementController {

	@Operation(summary = "Get the list of all clients")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Client list retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public List<ClientDto> listClients();

	@Operation(summary = "Add a new client")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "New client added", content = @Content(schema = @Schema(implementation = UuidDto.class))),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> addClient(@RequestBody AddClientRequestDto request)
			throws CertificateException, AlreadyExistException, NotFoundException, ValidationException;

	@Operation(summary = "Get the details of a client")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Client details retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = { "application/json" })
	public ClientDto getClient(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Edit a client")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Client edited"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public ClientDto editClient(@PathVariable String uuid, @RequestBody EditClientRequestDto request)
			throws CertificateException, NotFoundException, AlreadyExistException;

	@Operation(summary = "Remove a client")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Client Deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeClient(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Remove multiple client")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Clients Deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkRemoveClient(@RequestBody List<String> clientUuids) throws NotFoundException;

	@Operation(summary = "Get Authorized profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of authorized profiles"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/listauth", method = RequestMethod.GET, produces = { "application/json" })
	public List<RaProfileDto> listAuthorizations(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Disable a client")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Client disabled"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PUT, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disableClient(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Enable a client")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Client enabled"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PUT, consumes = {
			"application/json" }, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void enableClient(@PathVariable String uuid) throws NotFoundException, CertificateException;

	@Operation(summary = "Disable multiple client")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Client disabled"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/disable", method = RequestMethod.PUT, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkDisableClient(@RequestBody List<String> clientUuids) throws NotFoundException;

	@Operation(summary = "Enable multiple client")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Client enabled"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/enable", method = RequestMethod.PUT, consumes = {
			"application/json" }, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkEnableClient(@RequestBody List<String> clientUuids) throws NotFoundException;

	@Operation(summary = "Authorize a client")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Client authorized with given profile"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/authorize/{raProfileUuid}", method = RequestMethod.PUT, consumes = {
			"application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void authorizeClient(@PathVariable String uuid, @PathVariable String raProfileUuid) throws NotFoundException;

	@Operation(summary = "Unauthorize Client")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Client unauthorized with given profile"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/unauthorize/{raProfileUuid}", method = RequestMethod.PUT, consumes = {
			"application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unauthorizeClient(@PathVariable String uuid, @PathVariable String raProfileUuid) throws NotFoundException;
}
