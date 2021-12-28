package com.czertainly.api.interfaces.core.connector;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.core.connectors.ConnectorDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/connector")
@Tag(name = "Connector Registration API", description = "Connector Registration API")
public interface ConnectorRegistrationController {
    @Operation(summary = "Register a connector")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Connector registration initiated"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @RequestMapping(path = "/register", method = RequestMethod.POST, produces = {"application/json"})
    public ConnectorDto register(@RequestBody ConnectorDto request) throws NotFoundException, AlreadyExistException;
}
