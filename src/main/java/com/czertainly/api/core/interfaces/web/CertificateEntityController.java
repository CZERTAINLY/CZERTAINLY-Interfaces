package com.czertainly.api.core.interfaces.web;

import java.util.List;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.discovery.CertificateEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/certificate-entity")
@Tag(name = "Certificate Entity API", description = "Certificate Entity API")
public interface CertificateEntityController {
	
	@Operation(summary = "List of All Entities")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "list of available entities"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<CertificateEntityDto> listCertificateEntitys();
	
	@Operation(summary = "Detail of an Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Entity Details retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public CertificateEntityDto getCertificateEntity(@PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Create a new Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "New Entity Created"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> createCertificateEntity(@RequestBody CertificateEntityDto request)
			throws AlreadyExistException, NotFoundException;
	
	@Operation(summary = "Update an Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Entity updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public CertificateEntityDto updateCertificateEntity(@PathVariable String uuid,
			@RequestBody CertificateEntityDto request) throws NotFoundException;
	
	@Operation(summary = "Remove an Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "entity removed"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeCertificateEntity(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Remove multiple Entity")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "entity removed"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkRemoveCertificateEntity(@RequestBody List<String> entityUuids) throws NotFoundException;
}
