package com.czertainly.api.interfaces.core.local;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.admin.AddAdminRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.cert.CertificateException;

@RestController
@RequestMapping("/v1/local")
@Tag(name = "Local API", description = "API only accessible from localhost")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content
                )
        })
public interface LocalController {

    @Operation(summary = "Create Administrator")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Admin created")})
    @RequestMapping(path = "/admins", method = RequestMethod.POST, produces = {"application/json"})
    ResponseEntity<?> addAdmin(
            @RequestBody AddAdminRequestDto request)
            throws CertificateException, NotFoundException, AlreadyExistException;
}
