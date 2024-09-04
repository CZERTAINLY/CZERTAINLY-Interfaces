package com.czertainly.api.interfaces.core.cmp;

import com.czertainly.api.interfaces.core.cmp.error.CmpBaseException;
import com.czertainly.api.model.core.acme.ProblemDocument;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/v1/protocols/cmp/{cmpProfileName}"})
@Tag(
        name = "CMP operations",
        description = "Interfaces used by CMP clients to request CMP related operations. CMP Profile defines the behaviour for the specific CMP configuration. When the CMP Profile contains default RA Profile, it can be used by the CMP clients to request operations on their specific URL."
)
@ApiResponses({@ApiResponse(
        responseCode = "400",
        description = "Bad Request",
        content = {@Content(
                mediaType = "application/problem+json",
                schema = @Schema(
                        implementation = ProblemDocument.class
                )
        )}
), @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content = {@Content(
                mediaType = "application/problem+json",
                schema = @Schema(
                        implementation = ProblemDocument.class
                )
        )}
), @ApiResponse(
        responseCode = "403",
        description = "Forbidden",
        content = {@Content(
                mediaType = "application/problem+json",
                schema = @Schema(
                        implementation = ProblemDocument.class
                )
        )}
), @ApiResponse(
        responseCode = "500",
        description = "Internal Server Error",
        content = {@Content}
)})
public interface CmpController {

    @Operation(summary = "CMP Get Operations")
    @ApiResponses(value = {@ApiResponse(
            responseCode = "500",
            description = "Operation is not allowed"
    )})
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<byte[]> doGet(
            @PathVariable String cmpProfileName,
            @RequestParam(required = false) @Schema(
                    description = "DER encoded CMP data",type = "string",format = "binary") byte[] message
    ) throws CmpBaseException;

    @Operation(
            summary = "CMP Post Operation",
            externalDocs = @ExternalDocumentation(
                    description = "RFC 4210",
                    url = "https://www.rfc-editor.org/rfc/rfc4210"
            )
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "Operation executed",
            content = {@Content(
                    schema = @Schema(
                            description = "Response structure(s) defined in RFC 4210, section 5.3",
                            externalDocs = @ExternalDocumentation(
                                    description = "RFC 4210",
                                    url = "https://www.rfc-editor.org/rfc/rfc4210#section-5.3"
                            ),
                            type = "string",
                            format = "binary"
                    )
            )}
    )})
    @RequestMapping(
            method = {RequestMethod.POST},
            consumes = {"application/pkixcmp"},
            produces = {"application/pkixcmp"}
    )
    ResponseEntity<byte[]> doPost(
            @PathVariable String cmpProfileName,
            @RequestBody @Schema(description = "Binary CMP data",type = "string",format = "binary") byte[] request
    ) throws CmpBaseException;
}

