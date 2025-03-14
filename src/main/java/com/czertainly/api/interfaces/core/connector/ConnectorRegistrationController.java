package com.czertainly.api.interfaces.core.connector;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.AttributeException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.NoAuthController;
import com.czertainly.api.model.client.connector.ConnectorRequestDto;
import com.czertainly.api.model.common.UuidDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/connector")
@Tag(name = "Connector Registration", description = "Connector Registration API")
public interface ConnectorRegistrationController extends NoAuthController {
    @Operation(summary = "Register a Connector")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Connector registration initiated")})
    @PostMapping(path = "/register", consumes = {"application/json"}, produces = {"application/json"})
    UuidDto register(@RequestBody ConnectorRequestDto request) throws ConnectorException, AlreadyExistException, AttributeException, NotFoundException;
}
