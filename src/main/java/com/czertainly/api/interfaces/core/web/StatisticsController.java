package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.client.dashboard.StatisticsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/v1/statistics")
@Tag(name = "Statistics/Dashboard", description = "Statistics/Dashboard API")
public interface StatisticsController extends AuthProtectedController {
	@Operation(summary = "Get Dashboard/Statistics Details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Details retrieved")})
	@GetMapping(produces = {"application/json"})
	public StatisticsDto getStatistics();
}
