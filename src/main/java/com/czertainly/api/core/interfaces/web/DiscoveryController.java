package com.czertainly.api.core.interfaces.web;

import java.security.cert.CertificateException;
import java.util.List;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.discovery.DiscoveryDto;
import com.czertainly.api.model.discovery.DiscoveryHistoryDto;
import com.czertainly.api.exception.NotFoundException;
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
@RequestMapping("/v1/discovery")
@Tag(name = "Discovery Management API", description = "Discovery Management API")

public interface DiscoveryController {
	
	@Operation(summary = "List of Available Discoveries")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of availabe discoveries"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<DiscoveryHistoryDto> listDiscovery();
	
	@Operation(summary = "List a Discovery")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Dicovery details retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public DiscoveryHistoryDto getDiscovery(@PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Create a new Discovery")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "New Discovery Created"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> createDiscovery(@RequestBody DiscoveryDto request)
			throws AlreadyExistException, NotFoundException, CertificateException, InterruptedException, ConnectorException;
	
	@Operation(summary = "Delete a Discovery")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Discovery deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeDiscovery(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Delete Multiple Discovery")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Discovery deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkRemoveDiscovery(@RequestBody List<String> discoveryUuids) throws NotFoundException;
}
