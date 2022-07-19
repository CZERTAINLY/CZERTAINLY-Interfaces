package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.model.common.attribute.AttributeDefinition;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.RequestAttributeDto;
import com.czertainly.api.model.core.connector.AuthType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/connectors/auth")
@Tag(name = "Connector Authentication API", description = "Connector Authentication API")
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

public interface ConnectorAuthController {

	@Operation(summary = "Get list of Authentication Types")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Auth Types retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))) })
	@RequestMapping(path = "/types", method = RequestMethod.GET, produces = {"application/json"})
	public Set<AuthType> getAuthenticationTypes();

	@Operation(summary = "Get basic auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved") })
	@RequestMapping(path = "/attributes/basic", method = RequestMethod.GET, produces = {"application/json"})
	public List<AttributeDefinition> getBasicAuthAttributes();

	@Operation(summary = "Validate basic auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated") })
	@RequestMapping(path = "/attributes/basic/validate", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public void validateBasicAuthAttributes(@RequestBody List<RequestAttributeDto> attributes);

	@Operation(summary = "Get Attributes for certificate auth")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved")})
	@RequestMapping(path = "/attributes/certificate", method = RequestMethod.GET, produces = {"application/json"})
	public List<AttributeDefinition> getCertificateAttributes();

	@Operation(summary = "Validate certificate auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated")})
	@RequestMapping(path = "/attributes/certificate/validate", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public void validateCertificateAttributes(@RequestBody List<RequestAttributeDto> attributes);

	@Operation(summary = "Get API Key auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved")})
	@RequestMapping(path = "/attributes/apiKey", method = RequestMethod.GET, produces = {"application/json"})
	public List<AttributeDefinition> getApiKeyAuthAttributes();

	@Operation(summary = "Validate API Key Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated")})
	@RequestMapping(path = "/attributes/apiKey/validate", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public void validateApiKeyAuthAttributes(@RequestBody List<RequestAttributeDto> attributes);

	@Operation(summary = "Get JWT auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved")})
	@RequestMapping(path = "/attributes/jwt", method = RequestMethod.GET, produces = {"application/json"})
	public List<AttributeDefinition> getJWTAuthAttributes();

	@Operation(summary = "Validate JWT auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated")})
	@RequestMapping(path = "/attributes/jwt/validate", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public void validateJWTAuthAttributes(@RequestBody List<RequestAttributeDto> attributes);
}
