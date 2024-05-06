package com.czertainly.api.interfaces.core.client.v2;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.v2.*;
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

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping("/v2/operations/authorities/{authorityUuid}/raProfiles/{raProfileUuid}")
@Tag(name = "Client Operations v2", description = "Client Operations v2 API")
@ApiResponses(
		value = {
				@ApiResponse(
						responseCode = "400",
						description = "Bad Request",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
				@ApiResponse(
						responseCode = "401",
						description = "Unauthorized",
						content = @Content(schema = @Schema())
				),
				@ApiResponse(
						responseCode = "403",
						description = "Forbidden",
						content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))
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
	@RequestMapping(path = "/attributes/issue", method = RequestMethod.GET, produces = {"application/json"})
	List<BaseAttribute> listIssueCertificateAttributes(
			@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
			@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException, ConnectorException;

	@Operation(summary = "Validate issue Certificate Attributes")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Attributes validated"),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
	@RequestMapping(path = "/attributes/issue/validate", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	void validateIssueCertificateAttributes(
			@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
			@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid,
			@RequestBody List<RequestAttributeDto> attributes) throws NotFoundException, ConnectorException, ValidationException;

	@Operation(summary = "Issue existing certificate with status New")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate issued"),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
	@RequestMapping(path = "/certificates/{certificateUuid}/issue", method = RequestMethod.POST, produces = {"application/json"})
	ClientCertificateDataResponseDto issueNewCertificate(
			@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
			@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid,
			@Parameter(description = "Certificate UUID") @PathVariable String certificateUuid) throws ConnectorException, CertificateException, NoSuchAlgorithmException, AlreadyExistException, CertificateRequestException;

	@Operation(summary = "Issue Certificate")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate issued"),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
	@RequestMapping(path = "/certificates", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	ClientCertificateDataResponseDto issueCertificate(
			@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
			@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid,
			@RequestBody ClientCertificateSignRequestDto request) throws NotFoundException, CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, CertificateOperationException, CertificateRequestException;

	@Operation(summary = "Renew Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificate renewed"),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}))})
	@RequestMapping(path = "/certificates/{certificateUuid}/renew", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	ClientCertificateDataResponseDto renewCertificate(
			@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
			@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid,
			@Parameter(description = "Certificate UUID") @PathVariable String certificateUuid,
			@RequestBody ClientCertificateRenewRequestDto request) throws NotFoundException, CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, CertificateOperationException, CertificateRequestException;

	@Operation(summary = "Rekey Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificate regenerated"),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}))})
	@RequestMapping(path = "/certificates/{certificateUuid}/rekey", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	ClientCertificateDataResponseDto rekeyCertificate(
			@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
			@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid,
			@Parameter(description = "Certificate UUID") @PathVariable String certificateUuid,
			@RequestBody ClientCertificateRekeyRequestDto request) throws NotFoundException, CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, CertificateOperationException, CertificateRequestException;

	@Operation(summary = "Get revocation Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes obtained") })
	@RequestMapping(path = "/attributes/revoke", method = RequestMethod.GET, produces = {"application/json"})
	List<BaseAttribute> listRevokeCertificateAttributes(
			@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
			@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws ConnectorException;

	@Operation(summary = "Validate revocation Attributes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes validated")})
	@RequestMapping(path = "/attributes/revoke/validate", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	void validateRevokeCertificateAttributes(
			@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
			@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid,
			@RequestBody List<RequestAttributeDto> attributes) throws ConnectorException, ValidationException;

	@Operation(summary = "Revoke Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Certificate revoked")})
	@RequestMapping(path = "/certificates/{certificateUuid}/revoke", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void revokeCertificate(
			@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
			@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid,
			@Parameter(description = "Certificate UUID") @PathVariable String certificateUuid,
			@RequestBody ClientCertificateRevocationDto request) throws ConnectorException, AttributeException;

}
