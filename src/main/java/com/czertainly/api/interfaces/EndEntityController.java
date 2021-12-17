package com.czertainly.api.interfaces;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.ca.AddEndEntityRequestDto;
import com.czertainly.api.model.ca.EditEndEntityRequestDto;
import com.czertainly.api.model.ca.EndEntityDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/caConnector/authorities/{uuid}/endEntityProfiles/{endEntityProfileName}/endEntities")
@Tag(name = "End Entity Management API", description = "End Entity Management API")
public interface EndEntityController {

    @Operation(
            summary = "List End Entities",
            description = "Method for listing all End Entities managed by CA."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entities retrieved"
                    )
            })
    @RequestMapping(method = RequestMethod.GET)
    List<EndEntityDto> listEndEntities(
            @PathVariable String uuid,
            @PathVariable String endEntityProfileName) throws NotFoundException;

    @Operation(
            summary = "Get End Entity",
            description = "Method for retrieving detail of End Entity managed by CA."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entity retrieved"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/{endEntityName}", method = RequestMethod.GET)
    EndEntityDto getEndEntity(
            @PathVariable String uuid,
            @PathVariable String endEntityProfileName,
            @PathVariable String endEntityName) throws NotFoundException;

    @Operation(
            summary = "Create End Entity",
            description = "Method for creating new End Entity."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entity created"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content
                    )
            })
    @RequestMapping(method = RequestMethod.POST)
    void createEndEntity(
            @PathVariable String uuid,
            @PathVariable String endEntityProfileName,
            @RequestBody AddEndEntityRequestDto request) throws NotFoundException, AlreadyExistException;

    @Operation(
            summary = "Update End Entity",
            description = "Method for updating existing End Entity managed by CA."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entity updated"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/{endEntityName}", method = RequestMethod.POST)
    void updateEndEntity(
            @PathVariable String uuid,
            @PathVariable String endEntityProfileName,
            @PathVariable String endEntityName,
            @RequestBody EditEndEntityRequestDto request) throws NotFoundException;

    @Operation(
            summary = "Remove End Entity",
            description = "Method for removing existing End Entity."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entity removed"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/{endEntityName}", method = RequestMethod.DELETE)
    void removeEndEntity(
            @PathVariable String uuid,
            @PathVariable String endEntityProfileName,
            @PathVariable String endEntityName) throws NotFoundException;


    @Operation(
            summary = "Reset End Entity password",
            description = "Method for resetting password for existing End Entity."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "End Entity password reset"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/{endEntityName}/resetPassword", method = RequestMethod.PUT)
    void resetPassword(
            @PathVariable String uuid,
            @PathVariable String endEntityProfileName,
            @PathVariable String endEntityName) throws NotFoundException;

}