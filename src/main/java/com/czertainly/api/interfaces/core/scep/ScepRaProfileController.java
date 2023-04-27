package com.czertainly.api.interfaces.core.scep;

import com.czertainly.api.exception.ScepException;
import com.czertainly.api.model.core.acme.ProblemDocument;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * List of end points supported for the SCEP implementation in CZERTAINLY
 */
@RestController
@RequestMapping("/v1/protocols/scep/raProfile/{raProfileName}/pkiclient.exe")
@Tag(name = "SCEP RA Profile", description = "Interfaces used by SCEP clients to request SCEP related operations " +
        "on top of RA Profile. SCEP Profile defines the behaviour for the specific SCEP configuration. SCEP Profile is " +
        "bound with specific RA Profile and it can be used by the SCEP clients to request operations on their specific " +
        "URL. These operations are always specific only for the RA Profile.")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(mediaType = "application/problem+json",
                                schema = @Schema(implementation = ProblemDocument.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = @Content(mediaType = "application/problem+json",
                                schema = @Schema(implementation = ProblemDocument.class))
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden",
                        content = @Content(mediaType = "application/problem+json",
                                schema = @Schema(implementation = ProblemDocument.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content
                )
        })
public interface ScepRaProfileController {

    @Operation(summary = "SCEP Get Operations")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operation executed")})
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<Object> doGet(
            @PathVariable String raProfileName,
            @RequestParam String operation,
            @RequestParam(required = false) String message
    ) throws ScepException;

    @Operation(summary = "SCEP Post Operations")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operation executed")})
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    ResponseEntity<Object> doPost(
            @PathVariable String raProfileName,
            @RequestParam String operation,
            @RequestBody byte[] request
    ) throws ScepException;
}
