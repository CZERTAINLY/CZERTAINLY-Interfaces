package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.authority.AddEndEntityRequestDto;
import com.czertainly.api.model.core.authority.EditEndEntityRequestDto;
import com.czertainly.api.model.core.authority.EndEntityDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/authorityProvider/authorities/{uuid}/endEntityProfiles/{endEntityProfileName}/endEntities")
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
@Tag(name = "End Entity Management API", description = "End Entity Management API")
public interface EndEntityController {

    @Operation(
            summary = "List End Entities"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entities retrieved"
                    )
            })
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    List<EndEntityDto> listEndEntities(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @Parameter(description = "End Entity Profile Name") @PathVariable String endEntityProfileName) throws NotFoundException;

    @Operation(
            summary = "Get End Entity"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entity retrieved"
                    )
            })
    @RequestMapping(path = "/{endEntityName}", method = RequestMethod.GET, produces = {"application/json"})
    EndEntityDto getEndEntity(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @Parameter(description = "End Entity Profile Name") @PathVariable String endEntityProfileName,
            @Parameter(description = "End Entity Name") @PathVariable String endEntityName) throws NotFoundException;

    @Operation(
            summary = "Create End Entity"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entity created"
                    )
            })
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    void createEndEntity(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @Parameter(description = "End Entity Profile Name") @PathVariable String endEntityProfileName,
            @RequestBody AddEndEntityRequestDto request) throws NotFoundException, AlreadyExistException;

    @Operation(
            summary = "Update End Entity"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entity updated"
                    )
            })
    @RequestMapping(path = "/{endEntityName}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    void updateEndEntity(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @Parameter(description = "End Entity Profile Name") @PathVariable String endEntityProfileName,
            @Parameter(description = "End Entity Name") @PathVariable String endEntityName,
            @RequestBody EditEndEntityRequestDto request) throws NotFoundException;

    @Operation(
            summary = "Remove End Entity"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entity removed"
                    )
            })
    @RequestMapping(path = "/{endEntityName}", method = RequestMethod.DELETE, produces = {"application/json"})
    void removeEndEntity(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @Parameter(description = "End Entity Profile Name") @PathVariable String endEntityProfileName,
            @Parameter(description = "End Entity Name") @PathVariable String endEntityName) throws NotFoundException;


    @Operation(
            summary = "Reset End Entity password"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entity password reset"
                    )
            })
    @RequestMapping(path = "/{endEntityName}/resetPassword", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    void resetPassword(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @Parameter(description = "End Entity Profile Name") @PathVariable String endEntityProfileName,
            @Parameter(description = "End Entity Name") @PathVariable String endEntityName) throws NotFoundException;

}