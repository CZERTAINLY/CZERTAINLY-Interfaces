package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.certificate.*;
import com.czertainly.api.model.client.certificate.owner.CertificateOwnerBulkUpdateDto;
import com.czertainly.api.model.client.certificate.owner.CertificateOwnerRequestDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.certificate.CertificateDto;
import com.czertainly.api.model.core.certificate.CertificateEventHistoryDto;
import com.czertainly.api.model.core.location.LocationDto;
import com.czertainly.api.model.core.search.SearchFieldDataDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
public interface CertificateController {
	
	@Operation(summary = "List Certificates")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of all the certificates")})
	@RequestMapping(method = RequestMethod.POST, produces = {"application/json"})
	public CertificateResponseDto listCertificate(@RequestBody SearchRequestDto request) throws ValidationException;
	
	@Operation(summary = "Get Certificate Details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificate detail retrieved")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public CertificateDto getCertificate(@Parameter(description = "Certificate UUID") @PathVariable String uuid)
			throws NotFoundException, CertificateException, IOException;
	
	@Operation(summary = "Remove a certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Certificate deleted")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeCertificate(@Parameter(description = "Certificate UUID") @PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Update RA Profile for a Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profile updated")})
	@RequestMapping(path = "/{uuid}/ra-profile", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateRaProfile(@Parameter(description = "Certificate UUID") @PathVariable String uuid, @RequestBody CertificateUpdateRAProfileDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update Group for a Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Group updated")})
	@RequestMapping(path = "/{uuid}/group", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateCertificateGroup(@Parameter(description = "Certificate UUID") @PathVariable String uuid,
			@RequestBody CertificateUpdateGroupDto request) throws NotFoundException;
	
	@Operation(summary = "Update Owner for a Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Owner updated")})
	@RequestMapping(path = "/{uuid}/owner", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateOwner(@Parameter(description = "Certificate UUID") @PathVariable String uuid, @RequestBody CertificateOwnerRequestDto request)
			throws NotFoundException;
	
	@Operation(summary = "Initiate Certificate validation")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificate validation initiated")})
	@RequestMapping(method = RequestMethod.GET, path = "/{uuid}/validate", produces = {"application/json"})
	public void check(@Parameter(description = "Certificate UUID") @PathVariable String uuid)
			throws CertificateException, IOException, NotFoundException;
	
	@Operation(summary = "Update RA Profile for multiple Certificates", description = "In this operation, when the list of " +
			"Certificate UUIDs are provided and the filter is left as null or undefined, then the change will " +
			"be applied only to the list of Certificate UUIDs provided. When the filter is provided in the request, " +
			"the list of UUIDs will be ignored and the change will be applied for the all the certificates that matches " +
			"the filter criteria. To apply this change for all the Certificates in the inventory, " +
			"provide an empty array \"[]\" for the value of \"filters\" in the request body")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profile updated") })
	@RequestMapping(path = "/ra-profile", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkUpdateRaProfile(@RequestBody MultipleRAProfileUpdateDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update Group for multiple Certificates", description = "In this operation, when the list of " +
			"Certificate UUIDs are provided and the filter is left as null or undefined, then the change will " +
			"be applied only to the list of Certificate UUIDs provided. When the filter is provided in the request, " +
			"the list of UUIDs will be ignored and the change will be applied for the all the certificates that matches " +
			"the filter criteria. To apply this change for all the Certificates in the inventory, " +
			"provide an empty array \"[]\" for the value of \"filters\" in the request body")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Group updated")})
	@RequestMapping(path = "/group", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkUpdateCertificateGroup(@RequestBody MultipleGroupUpdateDto request)
			throws NotFoundException;
	
	@Operation(summary = "Update Owner for multiple Certificates", description = "In this operation, when the list of " +
			"Certificate UUIDs are provided and the filter is left as null or undefined, then the change will " +
			"be applied only to the list of Certificate UUIDs provided. When the filter is provided in the request, " +
			"the list of UUIDs will be ignored and the change will be applied for the all the certificates that matches " +
			"the filter criteria. To apply this change for all the Certificates in the inventory, " +
			"provide an empty array \"[]\" for the value of \"filters\" in the request body")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Owner updated")})
	@RequestMapping(path = "/owner", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkUpdateOwner(@RequestBody CertificateOwnerBulkUpdateDto request)
			throws NotFoundException;
	
	@Operation(summary = "Upload a new Certificate")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Certificate uploaded", content = @Content(schema = @Schema(implementation = UuidDto.class)))})
	@RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UuidDto> upload(@RequestBody UploadCertificateRequestDto request)
			throws AlreadyExistException, CertificateException;
	
	@Operation(summary = "Delete multiple certificates", description = "In this operation, when the list of " +
			"Certificate UUIDs are provided and the filter is left as null or undefined, then the change will " +
			"be applied only to the list of Certificate UUIDs provided. When the filter is provided in the request, " +
			"the list of UUIDs will be ignored and the change will be applied for the all the certificates that matches " +
			"the filter criteria. To apply this change for all the Certificates in the inventory, " +
			"provide an empty array \"[]\" for the value of \"filters\" in the request body")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificates deleted") })
	@RequestMapping(path = "/delete", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public BulkOperationResponse bulkRemoveCertificate(@RequestBody RemoveCertificateDto request) throws NotFoundException;

	@Operation(summary = "Validate Certificates of Status Unknown")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Certificate Validation Initiated") })
	@RequestMapping(path = "/validate", method = RequestMethod.PUT, consumes = {"application/json"}, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void validateAllCertificate();

	@Operation(summary = "Get Certificate searchable fields information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Certificate searchable field information retrieved") })
	@RequestMapping(path = "/search", method = RequestMethod.GET, produces = {"application/json"})
	public List<SearchFieldDataDto> getSearchableFieldInformation();

	@Operation(summary = "Get Certificate event history")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate event history retrieved")})
	@RequestMapping(path = "/{uuid}/history", method = RequestMethod.GET, produces = {"application/json"})
	public List<CertificateEventHistoryDto> getCertificateEventHistory(@Parameter(description = "Certificate UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(
			summary = "List of available Locations for the Certificate"
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Locations retrieved"
					)
			})
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/{certificateUuid}/locations",
			produces = {"application/json"}
	)
	List<LocationDto> listLocations(
			@Parameter(description = "Certificate UUID") @PathVariable String certificateUuid
	) throws NotFoundException;

}
