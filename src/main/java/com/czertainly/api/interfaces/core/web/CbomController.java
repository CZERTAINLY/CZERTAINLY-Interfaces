package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AttributeException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.cbom.CbomDetailDto;
import com.czertainly.api.model.core.cbom.CbomListDto;
import com.czertainly.api.model.core.cbom.CbomUploadRequestDto;
import com.czertainly.api.model.client.certificate.SearchRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/cboms")
@Tag(name = "CBOM management", description = "CBOM management API")
public interface CbomController extends AuthProtectedController {

	@Operation(summary = "List CBOMs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of available CBOMs") })
	@PostMapping(consumes = { "application/json" }, produces = { "application/json" })
	List<CbomListDto> listCboms(@RequestBody SearchRequestDto request);

	@Operation(summary = "CBOM detail")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "CBOM details retrieved"),
			@ApiResponse(responseCode = "404", description = "CBOM not found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
	})
	@GetMapping(path = "/{urn}", produces = { "application/json" })
	CbomDetailDto getCbomDetail(
			@Parameter(description = "CBOM URN") @PathVariable String urn,
			@Parameter(description = "CBOM version") @RequestParam(required = false) String version)
			throws NotFoundException;

	@Operation(summary = "List CBOM versions")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of CBOM versions retrieved"),
			@ApiResponse(responseCode = "404", description = "CBOM not found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
	})
	@GetMapping(path = "/{urn}/versions", produces = { "application/json" })
	List<CbomListDto> listCbomVersions(
			@Parameter(description = "CBOM URN") @PathVariable String urn)
			throws NotFoundException;

	@Operation(summary = "Upload CBOM")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "CBOM uploaded"),
			@ApiResponse(responseCode = "400", description = "Invalid CBOM content", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
	})
	@PostMapping(path = "/upload", consumes = { "application/json" }, produces = { "application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	CbomListDto uploadCbom(@RequestBody CbomUploadRequestDto request) throws AttributeException;

}
