package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.certificate.IdAndCertificateIdDto;
import com.czertainly.api.model.client.certificate.RemoveCertificateDto;
import com.czertainly.api.model.client.certificate.UploadCertificateRequestDto;
import com.czertainly.api.model.client.certificate.owner.CertificateOwnerBulkUpdateDto;
import com.czertainly.api.model.client.certificate.owner.CertificateOwnerRequestDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.certificate.CertificateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping("/v1/certificate")
@Tag(name = "Certificate Inventory API", description = "Certificate Inventory API")
@ApiResponses(
		value = {
				@ApiResponse(
						responseCode = "400",
						description = "Bad Request",
						content = @Content
				),
				@ApiResponse(
						responseCode = "404",
						description = "Not Found",
						content = @Content
				),
				@ApiResponse(
						responseCode = "500",
						description = "Internal Server Error",
						content = @Content
				)
		})
public interface CertificateController {
	
	@Operation(summary = "List Certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of all the certificates")})
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<CertificateDto> listCertificate(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end);
	
	@Operation(summary = "Get Certificates Details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Details of the certificates")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public CertificateDto getCertificate(@Parameter(description = "Certificate UUID") @PathVariable String uuid)
			throws NotFoundException, CertificateException, IOException;
	
	@Operation(summary = "Remove a certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Certificate deleted")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeCertificate(@Parameter(description = "Certificate UUID") @PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Update RA Profile for a certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profile updated")})
	@RequestMapping(path = "/{uuid}/ra-profile", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateRaProfile(@Parameter(description = "Certificate UUID") @PathVariable String uuid, @RequestBody UuidDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update Group for a certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Group updated")})
	@RequestMapping(path = "/{uuid}/group", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateCertificateGroup(@Parameter(description = "Certificate UUID") @PathVariable String uuid,
			@RequestBody UuidDto request) throws NotFoundException;
	
	@Operation(summary = "Update Entity for a certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Entity updated")})
	@RequestMapping(path = "/{uuid}/entity", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateEntity(@Parameter(description = "Certificate UUID") @PathVariable String uuid, @RequestBody UuidDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update Owner for a certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Owner updated")})
	@RequestMapping(path = "/{uuid}/owner", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateOwner(@Parameter(description = "Certificate UUID") @PathVariable String uuid, @RequestBody CertificateOwnerRequestDto request)
			throws NotFoundException;
	
	@Operation(summary = "Initiate Certificate Validation")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificate validated")})
	@RequestMapping(method = RequestMethod.GET, path = "/{uuid}/validate", produces = {"application/json"})
	public ResponseEntity<String> check(@Parameter(description = "Certificate UUID") @PathVariable String uuid)
			throws CertificateException, IOException, NotFoundException;
	
	@Operation(summary = "Update RA Profile for multiple certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profile updated") })
	@RequestMapping(path = "/ra-profile", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkUpdateRaProfile(@RequestBody IdAndCertificateIdDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update group for multiple certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Group updated")})
	@RequestMapping(path = "/group", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkUpdateCertificateGroup(@RequestBody IdAndCertificateIdDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update Entity for multiple certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Entity updated")})
	@RequestMapping(path = "/entity", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkUpdateEntity(@RequestBody IdAndCertificateIdDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update Owner for multiple certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Owner updated")})
	@RequestMapping(path = "/owner", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkUpdateOwner(@RequestBody CertificateOwnerBulkUpdateDto request)
			throws NotFoundException;
	
	@Operation(summary = "Upload a new Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Upload a new certificate") })
	@RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> upload(@RequestBody UploadCertificateRequestDto request)
			throws AlreadyExistException, CertificateException;
	
	@Operation(summary = "Delete multiple certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Certificates deleted") })
	@RequestMapping(path = "/delete", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkRemoveCertificate(@RequestBody RemoveCertificateDto request) throws NotFoundException;

	@Operation(summary = "Validate Certificates of Status Unknown")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Certificate Validation Initiated") })
	@RequestMapping(path = "/validate", method = RequestMethod.PUT, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void validateAllCertificate();
	
}
