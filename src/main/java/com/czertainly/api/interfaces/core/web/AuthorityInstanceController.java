package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.authority.AuthorityInstanceRequestDto;
import com.czertainly.api.model.client.connector.ForceDeleteMessageDto;
import com.czertainly.api.model.common.AttributeDefinition;
import com.czertainly.api.model.common.NameAndIdDto;
import com.czertainly.api.model.common.RequestAttributeDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.authority.AuthorityInstanceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/authorities")
@Tag(name = "Authority Management API", description = "Authority Management API")
public interface AuthorityInstanceController {

	@Operation(summary = "List of available Authority instances")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of Authority instances"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public List<AuthorityInstanceDto> listAuthorityInstances();

	@Operation(summary = "Details of an Authority instance")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Authority instance details retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = { "application/json" })
	public AuthorityInstanceDto getAuthorityInstance(@PathVariable String uuid) throws NotFoundException, ConnectorException;

	@Operation(summary = "Add Authority instance")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "New Authority instance added", content = @Content(schema = @Schema(implementation = UuidDto.class))),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = ValidationException.class))), })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createAuthorityInstance(@RequestBody AuthorityInstanceRequestDto request)
			throws AlreadyExistException, NotFoundException, ConnectorException;

	@Operation(summary = "Update Authority instance")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Authority instance details updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = ValidationException.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public AuthorityInstanceDto updateAuthorityInstance(@PathVariable String uuid, @RequestBody AuthorityInstanceRequestDto request)
			throws NotFoundException, ConnectorException;

	@Operation(summary = "Remove Authority instance")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Authority instance deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeAuthorityInstance(@PathVariable String uuid) throws NotFoundException, ConnectorException;

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
	public Boolean validateRAProfileAttributes(@PathVariable String uuid, @RequestBody List<RequestAttributeDto> attributes)
			throws NotFoundException, ConnectorException;

	@Operation(summary = "Delete multiple Authority instances")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Authority instances deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.DELETE)

	public List<ForceDeleteMessageDto> bulkRemoveAuthorityInstance(@RequestBody List<String> uuids) throws NotFoundException, ConnectorException, ValidationException;
	@Operation(summary = "Force delete multiple Authority instances")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Authority instances forced to delete"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/force", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkForceRemoveAuthorityInstance(@RequestBody List<String> uuids) throws NotFoundException, ValidationException;
}
