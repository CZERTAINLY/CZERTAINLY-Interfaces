package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.certificate.entity.CertificateEntityDto;
import com.czertainly.api.model.core.certificate.entity.CertificateEntityRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/entities")
@Tag(name = "Certificate Entity API", description = "Certificate Entity API")
public interface CertificateEntityController {
	
	@Operation(summary = "List Entities")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "list of available Entities"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<CertificateEntityDto> listCertificateEntities();
	
	@Operation(summary = "Entity details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Entity details retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public CertificateEntityDto getCertificateEntity(@PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Create Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "New Entity created", content = @Content(schema = @Schema(implementation = UuidDto.class))),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> createCertificateEntity(@RequestBody CertificateEntityRequestDto request)
			throws AlreadyExistException, NotFoundException;
	
	@Operation(summary = "Update Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Entity updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public CertificateEntityDto updateCertificateEntity(@PathVariable String uuid,
			@RequestBody CertificateEntityRequestDto request) throws NotFoundException;
	
	@Operation(summary = "Remove Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Entity removed"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeCertificateEntity(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Remove multiple Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Entities removed"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkRemoveCertificateEntity(@RequestBody List<String> entityUuids) throws NotFoundException;
}
