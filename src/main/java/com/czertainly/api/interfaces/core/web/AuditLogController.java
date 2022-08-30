package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.audit.AuditLogFilter;
import com.czertainly.api.model.core.audit.AuditLogResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/auditLogs")
@Tag(name = "Audit Log API", description = "Audit Log API")
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
public interface AuditLogController {
	
	@Operation(summary = "List Audit logs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of audit logs")})
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    public AuditLogResponseDto listAuditLogs(AuditLogFilter filter, Pageable pageable);
	
	@Operation(summary = "Export Audit logs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Export of audit logs")})
	@RequestMapping(path = "/export" ,method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<Resource> exportAuditLogs(AuditLogFilter filter, Pageable pageable);
	
	@Operation(summary = "List Audit Objects")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of audit Objects") })
	@RequestMapping(path = "/objects" ,method = RequestMethod.GET, produces = {"application/json"})
    public List<String> listObjects();
	
	@Operation(summary = "List Audit Operations")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of audit operations")})
	@RequestMapping(path = "/operations" ,method = RequestMethod.GET, produces = {"application/json"})
    public List<String> listOperations();
	
	@Operation(summary = "List Status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of audit log status")})
	@RequestMapping(path = "/statuses" ,method = RequestMethod.GET, produces = {"application/json"})
    public List<String> listOperationStatuses();
}
