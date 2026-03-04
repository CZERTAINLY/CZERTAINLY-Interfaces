package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.model.core.auth.LoginProviderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Tag(name = "Login Management", description = "Login Management API")
public interface LoginController {
    @Operation(summary = "Get available login providers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available login providers"),
            @ApiResponse(responseCode = "400", description = "Error during authentication")
    })
    @GetMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    List<LoginProviderDto> login(@RequestParam(value = "error", required = false) String error);

    @Operation(summary = "Login with provider")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login with provider successful"),
            @ApiResponse(responseCode = "400", description = "Error during authentication")
    })
    @GetMapping("/oauth2/authorization/{provider}/prepare")
    void loginWithProvider(@PathVariable String provider, @RequestParam(value = "redirect", required = false) String redirect) throws IOException;

    @Operation(summary = "Get JWK Set of a provider")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWK Set retrieved"),
            @ApiResponse(responseCode = "422", description = "Unable to retrieve JWK Set for the given provider")
    })
    @GetMapping(value = "/oauth2/{provider}/jwkSet", produces = {MediaType.APPLICATION_JSON_VALUE})
    String getJwkSet(@PathVariable String provider);
}
