package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.auth.UpdateUserRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.auth.ResourceDetailDto;
import com.czertainly.api.model.core.auth.UserDetailDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication Management API", description = "Authentication Management API")
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
public interface AuthController {

    @Operation(summary = "Profile Authorization")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Authenticate a user")})
    @RequestMapping(path = "/profile", method = RequestMethod.GET, produces = {"application/json"})
    UserDetailDto profile() throws NotFoundException, JsonProcessingException;

    @Operation(summary = "Update User Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Authenticate a user")})
    @RequestMapping(path = "/profile", method = RequestMethod.PUT, produces = {"application/json"})
    UserDetailDto updateUserProfile(@RequestBody UpdateUserRequestDto request) throws NotFoundException, JsonProcessingException, CertificateException;

    @Operation(summary = "Get all Resources")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Resources retrieved successfully")})
    @RequestMapping(path = "/resources", method = RequestMethod.GET, produces = {"application/json"})
    List<ResourceDetailDto> getAllResources() throws NotFoundException;
}
