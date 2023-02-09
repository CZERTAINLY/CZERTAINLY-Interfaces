package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.model.client.dashboard.StatisticsDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/statistics")
@Tag(name = "Statistics/Dashboard", description = "Statistics/Dashboard API")
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

public interface StatisticsController {
	@Operation(summary = "Get Dashboard/Statistics Details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Details retrieved")})
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public StatisticsDto getStatistics();
}
