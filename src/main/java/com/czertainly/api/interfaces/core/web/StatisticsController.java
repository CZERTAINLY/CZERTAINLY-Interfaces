package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.model.client.dashboard.StatisticsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/statistics")
@Tag(name = "Statistics/Dashboard API", description = "Statistics/Dashboard API")

public interface StatisticsController {
	@Operation(summary = "Get Dashboard/Statictics Details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Details retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public StatisticsDto getStatistics();
}
