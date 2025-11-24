package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v3.DataAttributeV3;
import com.czertainly.api.model.core.connector.AuthType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/v1/connectors/auth")
@Tag(name = "Connector Authentication", description = "Connector Authentication API")
@ApiResponses(
		value = {
				@ApiResponse(
						responseCode = "404",
						description = "Not Found",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				)
		})

public interface ConnectorAuthController extends AuthProtectedController {

	@Operation(summary = "Get list of Authentication Types")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Auth Types retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))) })
	@GetMapping(path = "/types", produces = {"application/json"})
	Set<AuthType> getAuthenticationTypes();

	@Operation(summary = "Get basic auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved")})
	@GetMapping(path = "/attributes/basic", produces = {"application/json"})
	List<DataAttributeV3> getBasicAuthAttributes();

	@Operation(summary = "Validate basic auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated") })
	@PostMapping(path = "/attributes/basic/validate", consumes = {
			"application/json" }, produces = { "application/json" })
	void validateBasicAuthAttributes(@RequestBody List<RequestAttributeDto> attributes);

	@Operation(summary = "Get Attributes for certificate auth")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved")})
	@GetMapping(path = "/attributes/certificate", produces = {"application/json"})
    List<DataAttributeV3> getCertificateAttributes();

	@Operation(summary = "Validate certificate auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated")})
	@PostMapping(path = "/attributes/certificate/validate", consumes = {
			"application/json" }, produces = { "application/json" })
	void validateCertificateAttributes(@RequestBody List<RequestAttributeDto> attributes);

	@Operation(summary = "Get API Key auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved")})
	@GetMapping(path = "/attributes/apiKey", produces = {"application/json"})
	List<DataAttributeV3> getApiKeyAuthAttributes();

	@Operation(summary = "Validate API Key Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated")})
	@PostMapping(path = "/attributes/apiKey/validate", consumes = {
			"application/json" }, produces = { "application/json" })
	void validateApiKeyAuthAttributes(@RequestBody List<RequestAttributeDto> attributes);

	@Operation(summary = "Get JWT auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes retrieved")})
	@GetMapping(path = "/attributes/jwt", produces = {"application/json"})
	List<DataAttributeV3> getJWTAuthAttributes();

	@Operation(summary = "Validate JWT auth Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated")})
	@PostMapping(path = "/attributes/jwt/validate", consumes = {
			"application/json" }, produces = { "application/json" })
	void validateJWTAuthAttributes(@RequestBody List<RequestAttributeDto> attributes);
}
