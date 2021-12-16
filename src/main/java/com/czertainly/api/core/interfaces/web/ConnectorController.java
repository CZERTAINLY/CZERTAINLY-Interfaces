package com.czertainly.api.core.interfaces.web;

import java.net.ConnectException;
import java.util.List;
import java.util.Map;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.AttributeCallback;
import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.HealthDto;
import com.czertainly.api.model.UuidDto;
import com.czertainly.api.model.connector.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/connectors")
@Tag(name = "Connector Management API", description = "Connector Management API")

public interface ConnectorController {

	@Operation(summary = "List of all connectors")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List connectors"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public List<ConnectorDto> listConnectors();

	@Operation(summary = "List Connectors by Function Group")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List all connectors"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, params = { "functionGroup" }, produces = { "application/json" })
	public List<ConnectorDto> listConnectorsByFunctionGroup(@RequestParam FunctionGroupCode functionGroup)
			throws NotFoundException;

	@Operation(summary = "List Connectors by Function group and kind")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List all connectors"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, params = { "functionGroup", "kind" }, produces = { "application/json" })
	public List<ConnectorDto> listConnectors(@RequestParam FunctionGroupCode functionGroup, @RequestParam String kind)
			throws NotFoundException;

	@Operation(summary = "Get details of a connector")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Connector details retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = { "application/json" })
	public ConnectorDto getConnector(@PathVariable String uuid) throws NotFoundException, ConnectorException;

	@Operation(summary = "Create a new connector")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "New connector created"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> createConnector(@RequestBody ConnectorRequestDto request)
			throws AlreadyExistException, ConnectorException;

	@Operation(summary = "Update a connector")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Connector updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public ConnectorDto updateConnector(@PathVariable String uuid, @RequestBody ConnectorRequestDto request)
			throws ConnectorException;

	@Operation(summary = "Delete a connector")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Connector deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeConnector(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Connect to a connector")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Connector connected"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/connect", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
			"application/json" })
	public List<ConnectDto> connect(@RequestBody ConnectorDto request) throws ValidationException, ConnectException, ConnectorException;

	@Operation(summary = "Reconnect to a connector")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Reconnect to a connector"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/reconnect", method = RequestMethod.PUT, consumes = {
			"application/json" }, produces = { "application/json" })
	public List<ConnectDto> reconnect(@PathVariable String uuid) throws ValidationException, NotFoundException, ConnectException, ConnectorException;

	@Operation(summary = "Approve multiple connector")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Approve multiple connectors"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/approve", method = RequestMethod.PUT, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkApprove(@RequestBody List<String> uuids) throws NotFoundException, ValidationException;

	@Operation(summary = "Reconnect multiple connectors")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Reconnect multiple connectors initiated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/reconnect", method = RequestMethod.PUT, consumes = {
			"application/json" }, produces = { "application/json" })
	public void bulkReconnect(@RequestBody List<String> uuids) throws ValidationException, NotFoundException, ConnectException, ConnectorException;

	@Operation(summary = "Approve a connector")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Connector Approved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.PUT, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void approve(@PathVariable String uuid) throws NotFoundException, ValidationException;

	@Operation(summary = "Check Health of a connector")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Health check completed"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/health", method = RequestMethod.GET, produces = { "application/json" })
	public HealthDto checkHealth(@PathVariable String uuid) throws NotFoundException, ValidationException, ConnectorException;

	@Operation(summary = "Get Attributes from a connector")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes received"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/{functionGroup}/{functionGroupKind}/attributes", method = RequestMethod.GET, produces = {
			"application/json" })
	public List<AttributeDefinition> getAttributes(@PathVariable String uuid, @PathVariable String functionGroup,
                                                   @PathVariable String functionGroupKind) throws NotFoundException, ConnectorException;
	
	@Operation(summary = "Get attributes of all function Groups")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes received"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/attributes-all", method = RequestMethod.GET, produces = {
			"application/json" })
	public Map<FunctionGroupCode, Map<String, List<AttributeDefinition>>> getAttributesAll(@PathVariable String uuid) throws NotFoundException, ConnectorException;

	@Operation(summary = "Validate attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes Validated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/{functionGroup}/{functionGroupKind}/attributes/validate", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public boolean validateAttributes(@PathVariable String uuid, @PathVariable String functionGroup,
			@PathVariable String functionGroupKind, @RequestBody List<AttributeDefinition> attributes)
			throws NotFoundException, ConnectorException;

	@Operation(summary = "Callback API")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Callback executed"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/callback", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Object callback(@PathVariable String uuid, @RequestBody AttributeCallback callback) throws NotFoundException, ConnectorException, ValidationException;

	@Operation(summary = "Delete multiple connectors")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Connector deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.DELETE)
	public List<ForceDeleteMessageDto> bulkRemoveConnector(@RequestBody List<String> uuids) throws NotFoundException, ValidationException, ConnectorException;

	@Operation(summary = "Force Delete multiple connectors")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Connector deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/force", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkForceRemoveConnector(@RequestBody List<String> uuids) throws NotFoundException, ValidationException;
}
