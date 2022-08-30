package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.auth.EditAuthProfileDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.auth.AuthProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Authenticate a user")})
	@RequestMapping(path = "/profile", method = RequestMethod.GET, produces = {"application/json"})
	public AuthProfileDto profile() throws NotFoundException;

	@Operation(summary = "Get user permission")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Permission of the user fetched")})
	@RequestMapping(path = "/permission", method = RequestMethod.GET, produces = {"application/json"})
	public Object getPermission() throws NotFoundException;

}
