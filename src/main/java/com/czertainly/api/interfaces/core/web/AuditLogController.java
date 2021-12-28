package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.model.core.audit.AuditLogFilter;
import com.czertainly.api.model.core.audit.AuditLogResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping("/v1/logs")
@Tag(name = "Audit Log API", description = "Audit Log API")
public interface AuditLogController {
	
	@Operation(summary = "List Audit logs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of audit logs"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    public AuditLogResponseDto listAuditLogs(AuditLogFilter filter, Pageable pageable);
	
	@Operation(summary = "Export Audit logs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Export of audit logs"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/export" ,method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<Resource> exportAuditLogs(AuditLogFilter filter, Pageable pageable);
	
	@Operation(summary = "List Audit Objects")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of audit Objects"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/objects" ,method = RequestMethod.GET, produces = {"application/json"})
    public List<String> listObjects();
	
	@Operation(summary = "List Audit Operations")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of audit operations"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/operations" ,method = RequestMethod.GET, produces = {"application/json"})
    public List<String> listOperations();
	
	@Operation(summary = "List Status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of audit log status"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/statuses" ,method = RequestMethod.GET, produces = {"application/json"})
    public List<String> listOperationStatuses();
}
