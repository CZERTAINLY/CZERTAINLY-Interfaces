package com.czertainly.api.core.interfaces.web;

import java.util.List;
import java.util.Set;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.RequestAttributeDto;
import com.czertainly.api.model.connector.AuthType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/connectors/auth")
@Tag(name = "Connector Authentication API", description = "Connector Authentication API")
public interface ConnectorAuthController {

	@Operation(summary = "Get list of Authentication Types")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Auth Types retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/types", method = RequestMethod.GET, produces = { "application/json" })
	public Set<AuthType> getAuthenticationTypes();

	@Operation(summary = "Get basic auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/attributes/basic", method = RequestMethod.GET, produces = { "application/json" })
	public List<AttributeDefinition> getBasicAuthAttributes();

	@Operation(summary = "Validate basic auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/attributes/basic/validate", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Boolean validateBasicAuthAttributes(@RequestBody List<RequestAttributeDto> attributes);

	@Operation(summary = "Get Attributes for certificate auth")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/attributes/certificate", method = RequestMethod.GET, produces = { "application/json" })
	public List<AttributeDefinition> getCertificateAttributes();

	@Operation(summary = "Validate certificate auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/attributes/certificate/validate", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Boolean validateCertificateAttributes(@RequestBody List<RequestAttributeDto> attributes);

	@Operation(summary = "Get API Key auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/attributes/apiKey", method = RequestMethod.GET, produces = { "application/json" })
	public List<AttributeDefinition> getApiKeyAuthAttributes();

	@Operation(summary = "Validate API Key Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/attributes/apiKey/validate", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Boolean validateApiKeyAuthAttributes(@RequestBody List<RequestAttributeDto> attributes);

	@Operation(summary = "Get JWT auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/attributes/jwt", method = RequestMethod.GET, produces = { "application/json" })
	public List<AttributeDefinition> getJWTAuthAttributes();

	@Operation(summary = "Validate JWT auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/attributes/jwt/validate", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Boolean validateJWTAuthAttributes(@RequestBody List<RequestAttributeDto> attributes);
}
