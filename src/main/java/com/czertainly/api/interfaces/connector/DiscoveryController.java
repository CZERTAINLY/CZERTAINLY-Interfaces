package com.czertainly.api.interfaces.connector;

import java.io.IOException;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.commons.ErrorMessageDto;
import com.czertainly.api.model.connector.discovery.DiscoveryProviderRequestDto;
import com.czertainly.api.model.connector.discovery.GetDiscoveryProviderRequestDto;
import org.springframework.web.bind.annotation.*;

import com.czertainly.api.model.connector.discovery.DiscoveryProviderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/discoveryProvider")
@Tag(
        name = "Discovery API",
        description = "Discovery Provider API. " +
                "Used to schedule and establish certificate discovery process. " +
                "Once the discovery process is started, the progress and the history " +
                "of the certificate discovery can be requested. Discovery provides " +
                "information about discovered certificates and meta data that are specific " +
                "to implementation of the discovery connector."
)
public interface DiscoveryController {

    @PostMapping(
            path = "/discover",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @Operation(
            summary = "Schedule new certificate discovery and get information",
            description = "Once the certificate discovery is scheduled, " +
                    "information about discovered certificates, summary, and meta data" +
                    "are returned."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Functions found"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorMessageDto.class
                                    )
                            )
                    )
            }
    )
    DiscoveryProviderDto discoverCertificate(@RequestBody DiscoveryProviderRequestDto request) throws IOException, NotFoundException;

    @PostMapping(
            path = "/discover/{uuid}",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @Operation(
            summary = "Get the status and details of the discovery",
            description = "Once the certificate discovery is scheduled, " +
                    "information about discovered certificates, summary, and meta data" +
                    "are returned."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Functions found"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorMessageDto.class
                                    )
                            )
                    )
            }
    )
    DiscoveryProviderDto getDiscovery(@PathVariable String uuid, @RequestBody GetDiscoveryProviderRequestDto request) throws IOException, NotFoundException;

}