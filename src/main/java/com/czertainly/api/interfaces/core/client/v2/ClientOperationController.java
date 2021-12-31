package com.czertainly.api.interfaces.core.client.v2;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.AttributeDefinition;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.RequestAttributeDto;
import com.czertainly.api.model.core.v2.ClientCertificateDataResponseDto;
import com.czertainly.api.model.core.v2.ClientCertificateRenewRequestDto;
import com.czertainly.api.model.core.v2.ClientCertificateRevocationDto;
import com.czertainly.api.model.core.v2.ClientCertificateSignRequestDto;
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
import org.springframework.web.bind.annotation.*;

import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping("/v2/operations/{raProfileName}")
@Tag(name = "Client Operation API", description = "Client Operation API")
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
				),
				@ApiResponse(
						responseCode = "502",
						description = "Connector Error",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
				@ApiResponse(
						responseCode = "503",
						description = "Connector Communication Error",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
		})
public interface ClientOperationController {
	
	@Operation(summary = "Get issue Certificate Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes list obtained"),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}))})
	@RequestMapping(path = "/issue/attributes", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	List<AttributeDefinition> listIssueCertificateAttributes(
			@Parameter(description = "RA Profile name") @PathVariable String raProfileName) throws NotFoundException, ConnectorException;
    
	@Operation(summary = "Validate issue Certificate Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated"),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/issue/attributes/validate", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	void validateIssueCertificateAttributes(
			@Parameter(description = "RA Profile name") @PathVariable String raProfileName,
            @RequestBody List<RequestAttributeDto> attributes) throws NotFoundException, ConnectorException, ValidationException;
	
	@Operation(summary = "Issue Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificate issued"),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}))})
	@RequestMapping(path = "/issue", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ClientCertificateDataResponseDto issueCertificate(
			@Parameter(description = "RA Profile name") @PathVariable String raProfileName,
            @RequestBody ClientCertificateSignRequestDto request) throws NotFoundException, ConnectorException, AlreadyExistException, CertificateException;
    
	@Operation(summary = "Renew Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificate renewed"),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/{certificateId}/renew", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ClientCertificateDataResponseDto renewCertificate(
			@Parameter(description = "RA Profile name") @PathVariable String raProfileName,
			@Parameter(description = "Certificate serial number") @PathVariable String certificateId,
            @RequestBody ClientCertificateRenewRequestDto request) throws NotFoundException, ConnectorException, AlreadyExistException, CertificateException;
    
	@Operation(summary = "Get revocation Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes obtained") })
    @RequestMapping(path = "/revoke/attributes", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	List<AttributeDefinition> listRevokeCertificateAttributes(
			@Parameter(description = "RA Profile name") @PathVariable String raProfileName) throws NotFoundException, ConnectorException;
    
	@Operation(summary = "Validate revocation Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated")})
    @RequestMapping(path = "/revoke/attributes/validate", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	void validateRevokeCertificateAttributes(
			@Parameter(description = "RA Profile name") @PathVariable String raProfileName,
            @RequestBody List<RequestAttributeDto> attributes) throws NotFoundException, ConnectorException, ValidationException;
    
	@Operation(summary = "Revoke Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Certificate revoked")})
    @RequestMapping(path = "/{certificateId}/revoke", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
    void revokeCertificate(
			@Parameter(description = "RA Profile name") @PathVariable String raProfileName,
			@Parameter(description = "Certificate serial number") @PathVariable String certificateId,
            @RequestBody ClientCertificateRevocationDto request) throws NotFoundException, ConnectorException;

}
