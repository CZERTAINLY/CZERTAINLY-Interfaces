package com.czertainly.api.interfaces.connector.common.v2;

import com.czertainly.api.model.common.error.ProblemDetailExtended;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/metrics")
@Tag(
        name = "Metrics API",
        description = "Connector metrics API. " +
                "Connector metrics API is exposing standard Prometheus/OpenMetrics metrics that can be scraped by observability solutions and collectors."
)
public interface MetricsController extends AuthProtectedConnectorController {

    @GetMapping
    @Operation(
            summary = "Get metrics"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Metrics retrieved successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/openmetrics-text; version=1.0.0; charset=utf-8",
                                            schema = @Schema(
                                                    type = "string",
                                                    description = "OpenMetrics v1.0.0 format with exemplars, units"
                                            ),
                                            examples = {@ExampleObject("# TYPE http_requests_total counter\nhttp_requests_total{method=\"GET\"} 42")}
                                    ),
                                    @Content(
                                            mediaType = "text/plain; version=0.0.4; charset=utf-8",
                                            schema = @Schema(
                                                    type = "string",
                                                    description = "Legacy Prometheus text 0.0.4 format (browser fallback)"
                                            ),
                                            examples = {@ExampleObject("# HELP http_requests_total Total requests\n# TYPE http_requests_total counter\nhttp_requests_total{method=\"GET\"} 42")}
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "501",
                            description = "Not Implemented",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetailExtended.class)
                            )
                    )
            })
    String getMetrics();

}