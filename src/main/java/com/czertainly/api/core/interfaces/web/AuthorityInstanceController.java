package com.czertainly.api.core.interfaces.web;

import java.util.List;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.ca.AuthorityInstanceDto;
import com.czertainly.api.model.NameAndIdDto;
import com.czertainly.api.model.ca.AuthorityInstanceRequestDto;
import com.czertainly.api.model.connector.ForceDeleteMessageDto;
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
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/authorities")
@Tag(name = "Authority instance Controller", description = "Authority instance Controller")
public interface AuthorityInstanceController {

	@Operation(summary = "List of available Authority instances")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of Authority instances"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public List<AuthorityInstanceDto> listCAInstances();

	@Operation(summary = "Details of an Authority instance")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Authority instance details retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = { "application/json" })
	public AuthorityInstanceDto getCAInstance(@PathVariable String uuid) throws NotFoundException, ConnectorException;

	@Operation(summary = "Add Authority instance")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "New Authority instance added"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = ValidationException.class))), })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createCAInstance(@RequestBody AuthorityInstanceRequestDto request)
			throws AlreadyExistException, NotFoundException, ConnectorException;

	@Operation(summary = "Update a Authority instance")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Authority instance details updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = ValidationException.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public AuthorityInstanceDto updateCAInstance(@PathVariable String uuid, @RequestBody AuthorityInstanceRequestDto request)
			throws NotFoundException, ConnectorException;

	@Operation(summary = "Remove Authority instance")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Authority instance deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeCAInstance(@PathVariable String uuid) throws NotFoundException, ConnectorException;

	@RequestMapping(path = "/{uuid}/endentityprofiles", method = RequestMethod.GET, produces = { "application/json" })
	public List<NameAndIdDto> listEntityProfiles(@PathVariable String uuid) throws NotFoundException, ConnectorException;

	@RequestMapping(path = "/{uuid}/endentityprofiles/{endEntityProfileId}/certificateprofiles", method = RequestMethod.GET, produces = {
			"application/json" })
	public List<NameAndIdDto> listCertificateProfiles(@PathVariable String uuid, @PathVariable Integer endEntityProfileId)
			throws NotFoundException, ConnectorException;

	@RequestMapping(path = "/{uuid}/endentityprofiles/{endEntityProfileId}/cas", method = RequestMethod.GET, produces = {
			"application/json" })
	public List<NameAndIdDto> listCAsInProfile(@PathVariable String uuid, @PathVariable Integer endEntityProfileId)
			throws NotFoundException, ConnectorException;

	@Operation(summary = "List RA Profile Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attribute information retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/raProfile/attributes", method = RequestMethod.GET, produces = { "application/json" })
	public List<AttributeDefinition> listRAProfileAttributes(@PathVariable String uuid) throws NotFoundException, ConnectorException;

	@Operation(summary = "Validate RA Profile Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attribute information validated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/raProfile/attributes/validate", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Boolean validateRAProfileAttributes(@PathVariable String uuid, @RequestBody List<AttributeDefinition> attributes)
			throws NotFoundException, ConnectorException;

	@Operation(summary = "Delete multiple Authority instances")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Authority instances deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.DELETE)

	public List<ForceDeleteMessageDto> bulkRemoveCaInstance(@RequestBody List<String> uuids) throws NotFoundException, ConnectorException, ValidationException;
	@Operation(summary = "Force delete multiple Authority instances")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Authority instances forced to delete"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/force", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkForceRemoveCaInstance(@RequestBody List<String> uuids) throws NotFoundException, ValidationException;
}
