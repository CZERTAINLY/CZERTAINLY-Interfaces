package com.czertainly.api.interfaces.core.web;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.List;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.model.client.certificate.IdAndCertificateIdDto;
import com.czertainly.api.model.client.certificate.RemoveCertificateDto;
import com.czertainly.api.model.client.certificate.UploadCertificateRequestDto;
import com.czertainly.api.model.client.certificate.owner.CertificateOwnerBulkUpdateDto;
import com.czertainly.api.model.client.certificate.owner.CertificateOwnerRequestDto;
import com.czertainly.api.model.commons.UuidDto;
import com.czertainly.api.model.core.certificate.CertificateDto;
import com.czertainly.api.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/certificate")
@Tag(name = "Certificate Inventory API", description = "Certificate Inventory API")
public interface CertificateController {
	
	@Operation(summary = "List Certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of all the certificates"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<CertificateDto> listCertificate(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end);
	
	@Operation(summary = "Get Certificates Details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Details of the certificates"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public CertificateDto getCertificate(@PathVariable String uuid)
			throws NotFoundException, CertificateException, IOException;
	
	@Operation(summary = "Remove a certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Certificate deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeCertificate(@PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Update RA Profile for a certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profile updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/ra-profile", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateRaProfile(@PathVariable String uuid, @RequestBody UuidDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update Group for a certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Group updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/group", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateCertificateGroup(@PathVariable String uuid,
			@RequestBody UuidDto request) throws NotFoundException;
	
	@Operation(summary = "Update Entity for a certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Entity updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/entity", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateEntity(@PathVariable String uuid, @RequestBody UuidDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update Owner for a certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Owner updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/owner", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateOwner(@PathVariable String uuid, @RequestBody CertificateOwnerRequestDto request)
			throws NotFoundException;
	
	@Operation(summary = "Initiate Certificate Validation")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificate validated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, path = "/{uuid}/validate", produces = {"application/json"})
	public ResponseEntity<String> check(@PathVariable String uuid)
			throws CertificateException, IOException, NotFoundException;
	
	@Operation(summary = "Update RA Profile for multiple certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profile updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/ra-profile", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkUpdateRaProfile(@RequestBody IdAndCertificateIdDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update group for multiple certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Group updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/group", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkUpdateCertificateGroup(@RequestBody IdAndCertificateIdDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update Entity for multiple certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Entity updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/entity", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkUpdateEntity(@RequestBody IdAndCertificateIdDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update Owner for multiple certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Owner updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/owner", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkUpdateOwner(@RequestBody CertificateOwnerBulkUpdateDto request)
			throws NotFoundException;
	
	@Operation(summary = "Upload a new Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Upload a new certificate"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> upload(@RequestBody UploadCertificateRequestDto request)
			throws AlreadyExistException, CertificateException;
	
	@Operation(summary = "Delete multiple certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Certificates deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/delete", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkRemoveCertificate(@RequestBody RemoveCertificateDto request) throws NotFoundException;

	@Operation(summary = "Validate Certificates of Status Unknown")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Certificate Validation Initiated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/validate", method = RequestMethod.PUT, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void validateAllCertificate();
	
}
