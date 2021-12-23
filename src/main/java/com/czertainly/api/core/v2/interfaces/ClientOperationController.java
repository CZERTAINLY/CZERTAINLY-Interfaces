package com.czertainly.api.core.v2.interfaces;

import com.czertainly.api.core.v2.model.ClientCertificateDataResponseDto;
import com.czertainly.api.core.v2.model.ClientCertificateRenewRequestDto;
import com.czertainly.api.core.v2.model.ClientCertificateRevocationDto;
import com.czertainly.api.core.v2.model.ClientCertificateSignRequestDto;
import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.ClientAttributeDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
public interface ClientOperationController {
	
	@Operation(summary = "Get list of all Attributes for Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes list obtained"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = ValidationException.class)))})
	@RequestMapping(path = "/issue/attributes", method = RequestMethod.GET)
	List<AttributeDefinition> listIssueCertificateAttributes(
            @PathVariable String raProfileName) throws NotFoundException, ConnectorException;
    
	@Operation(summary = "Validate issue Certificate Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = ValidationException.class)))})
    @RequestMapping(path = "/issue/attributes/validate", method = RequestMethod.POST)
	boolean validateIssueCertificateAttributes(
            @PathVariable String raProfileName,
            @RequestBody List<ClientAttributeDefinition> attributes) throws NotFoundException, ConnectorException, ValidationException;
	
	@Operation(summary = "Issue Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificate issued"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = ValidationException.class)))})
	@RequestMapping(path = "/issue", method = RequestMethod.POST)
    ClientCertificateDataResponseDto issueCertificate(
            @PathVariable String raProfileName,
            @RequestBody ClientCertificateSignRequestDto request) throws NotFoundException, ConnectorException, AlreadyExistException, CertificateException;
    
	@Operation(summary = "Renew Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificate renewed"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = ValidationException.class)))})
    @RequestMapping(path = "/{certificateId}/renew", method = RequestMethod.POST)
    ClientCertificateDataResponseDto renewCertificate(
            @PathVariable String raProfileName,
            @PathVariable String certificateId,
            @RequestBody ClientCertificateRenewRequestDto request) throws NotFoundException, ConnectorException, AlreadyExistException, CertificateException;
    
	@Operation(summary = "Get revocation Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes obtained"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
    @RequestMapping(path = "/revoke/attributes", method = RequestMethod.GET)
	List<AttributeDefinition> listRevokeCertificateAttributes(
            @PathVariable String raProfileName) throws NotFoundException, ConnectorException;
    
	@Operation(summary = "Validate revocation Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
    @RequestMapping(path = "/revoke/attributes/validate", method = RequestMethod.POST)
	boolean validateRevokeCertificateAttributes(
            @PathVariable String raProfileName,
            @RequestBody List<ClientAttributeDefinition> attributes) throws NotFoundException, ConnectorException, ValidationException;
    
	@Operation(summary = "Revoke Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Certificate revoked"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
    @RequestMapping(path = "/{certificateId}/revoke", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
    void revokeCertificate(
            @PathVariable String raProfileName,
            @PathVariable String certificateId,
            @RequestBody ClientCertificateRevocationDto request) throws NotFoundException, ConnectorException;

}
