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

@RequestMapping("/v1/cbom")
@Tag(name = "Cbom management", description = "Cbom management API")
public interface CbomController extends AuthProtectedController {

	@Operation(summary = "List Cboms")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of available Cboms") })
	@PostMapping(consumes = { "application/json" }, produces = { "application/json" })
	List<CbomListDto> listCboms(@RequestBody SearchRequestDto request);

	@Operation(summary = "Cbom detail")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cbom details retrieved"),
			@ApiResponse(responseCode = "404", description = "Cbom not found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
	})
	@GetMapping(path = "/{urn}", produces = { "application/json" })
	CbomDetailDto getCbomDetail(
			@Parameter(description = "Cbom URN") @PathVariable String urn,
			@Parameter(description = "Cbom version") @RequestParam(required = false) String version)
			throws NotFoundException;

	@Operation(summary = "List Cbom versions")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of Cbom versions retrieved"),
			@ApiResponse(responseCode = "404", description = "Cbom not found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
	})
	@GetMapping(path = "/{urn}/versions", produces = { "application/json" })
	List<CbomListDto> listCbomVersions(
			@Parameter(description = "Cbom URN") @PathVariable String urn)
			throws NotFoundException;

	@Operation(summary = "Upload Cbom")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cbom uploaded"),
			@ApiResponse(responseCode = "400", description = "Invalid Cbom content", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
	})
	@PostMapping(path = "/upload", consumes = { "application/json" }, produces = { "application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	CbomListDto uploadCbom(@RequestBody CbomUploadRequestDto request) throws AttributeException;

}
