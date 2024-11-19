package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.model.client.certificate.SearchFilterRequestDto;
import com.czertainly.api.model.client.certificate.SearchRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.audit.AuditLogResponseDto;
import com.czertainly.api.model.core.search.SearchFieldDataByGroupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/auditLogs")
@Tag(name = "Audit Log", description = "Audit Log API")
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
				)
		})
public interface AuditLogController {

	@Operation(summary = "List Audit logs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of audit logs")})
	@PostMapping(produces = {"application/json"})
    public AuditLogResponseDto listAuditLogs(@RequestBody SearchRequestDto requestDto);

	@Operation(summary = "Export Audit logs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Export of audit logs")})
	@PostMapping(path = "/export", produces = {"application/json"})
    public ResponseEntity<Resource> exportAuditLogs(@RequestBody List<SearchFilterRequestDto> filters);

	@Operation(summary = "Purge Audit logs")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Audit logs purged")})
	@PostMapping(path = "/purge", produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void purgeAuditLogs(@RequestBody List<SearchFilterRequestDto> filters);

	@Operation(summary = "Get Audit logs searchable fields information")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Audit logs searchable field information retrieved")})
	@GetMapping(path = "/search", produces = {"application/json"})
	List<SearchFieldDataByGroupDto> getSearchableFieldInformation();
}
