package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.model.core.auth.AuthProfileDto;
import com.czertainly.api.model.client.auth.EditAuthProfileDto;
import com.czertainly.api.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication Management API", description = "Authentication Management API")
public interface AuthController {

	@Operation(summary = "Profile Authorization")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Authenticate a user"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/profile", method = RequestMethod.GET, produces = {"application/json"})
	public AuthProfileDto profile() throws NotFoundException;

	@Operation(summary = "Edit Profile Information")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Edit Profile"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/profile", method = RequestMethod.PUT, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void editProfile(@RequestBody EditAuthProfileDto authProfileDTO) throws NotFoundException;
}
