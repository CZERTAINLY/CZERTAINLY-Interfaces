package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.AttributeException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.client.attribute.custom.CustomAttributeCreateRequestDto;
import com.czertainly.api.model.client.attribute.custom.CustomAttributeDefinitionDetailDto;
import com.czertainly.api.model.client.attribute.custom.CustomAttributeDefinitionDto;
import com.czertainly.api.model.client.attribute.custom.CustomAttributeUpdateRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.common.attribute.v2.CustomAttribute;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContent;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/attributes/custom")
@Tag(name = "Custom Attributes", description = "Custom Attributes API")
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

public interface CustomAttributeController {
    @Operation(summary = "List Custom Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "list of available Custom Attributes")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    List<CustomAttributeDefinitionDto> listCustomAttributes(@RequestParam(required = false) AttributeContentType attributeContentType);

    @Operation(summary = "Custom Attribute details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Custom Attribute details retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    CustomAttributeDefinitionDetailDto getCustomAttribute(@PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Create Custom Attribute")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Custom Attribute created", content = @Content(schema = @Schema(implementation = UuidDto.class)))})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<CustomAttributeDefinitionDetailDto> createCustomAttribute(@RequestBody CustomAttributeCreateRequestDto request)
            throws AlreadyExistException, NotFoundException, AttributeException;

    @Operation(summary = "Edit Custom Attribute")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Custom Attribute updated")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    CustomAttributeDefinitionDetailDto editCustomAttribute(@Parameter(description = "Attribute UUID") @PathVariable String uuid, @RequestBody CustomAttributeUpdateRequestDto request)
            throws NotFoundException, AttributeException;

    @Operation(summary = "Delete Custom Attribute")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Custom Attribute deleted")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomAttribute(@Parameter(description = "Custom Attribute UUID") @PathVariable String uuid) throws NotFoundException;


    @Operation(summary = "Enable Custom Attribute")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Custom Attribute enabled")})
    @RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableCustomAttribute(@Parameter(description = "Custom Attribute UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Disable Custom Attribute")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Custom Attribute disabled")})
    @RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableCustomAttribute(@Parameter(description = "Custom Attribute UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Delete multiple Custom Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Custom Attributes deleted")})
    @RequestMapping(method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDeleteCustomAttributes(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Attribute UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                              @RequestBody List<String> attributeUuids);

    @Operation(summary = "Enable multiple Custom Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Custom Attributes enabled")})
    @RequestMapping(method = RequestMethod.PATCH, path = "/enable", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkEnableCustomAttributes(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Attribute UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                              @RequestBody List<String> attributeUuids);

    @Operation(summary = "Disable multiple Custom Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Custom Attributes disabled")})
    @RequestMapping(method = RequestMethod.PATCH, path = "/disable", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDisableCustomAttributes(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Attribute UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                               @RequestBody List<String> attributeUuids);

    @Operation(summary = "Associate Custom Attribute to Resource")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Custom Attribute associated to the resources")})
    @RequestMapping(path = "/{uuid}/resources", method = RequestMethod.PATCH, produces = {"application/json"}, consumes = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateResources(@Parameter(description = "Custom Attribute UUID") @PathVariable String uuid, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "List of Resources", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"raProfiles\",\"authorities\"]")}))
    @RequestBody List<Resource> resources) throws NotFoundException;

    @Operation(summary = "Get Custom Attributes for a resource")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Custom Attribute retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomAttribute.class))))})
    @RequestMapping(path = "/resources/{resource}", method = RequestMethod.GET, produces = {"application/json"})
    List<BaseAttribute> getResourceCustomAttributes(@Parameter(description = "Resource Name") @PathVariable Resource resource);

    @Operation(summary = "Get available resources for Custom Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Custom Attribute retrieved")})
    @RequestMapping(path = "/resources", method = RequestMethod.GET, produces = {"application/json"})
    List<Resource> getResources();

    @Operation(summary = "Update Value of a Custom Attribute for a Resource")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Custom Attribute value updated")})
    @RequestMapping(
            path = "/resources/{resourceName}/objects/{objectUuid}/{attributeUuid}",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<ResponseAttributeDto> updateAttributeContentForResource(
            @Parameter(description = "Resource Type") @PathVariable Resource resourceName,
            @Parameter(description = "Object UUID") @PathVariable String objectUuid,
            @Parameter(description = "Custom Attribute UUID") @PathVariable String attributeUuid,
            @RequestBody List<BaseAttributeContent> request
            ) throws NotFoundException, AttributeException;

    @Operation(summary = "Delete Value of a Custom Attribute for a Resource")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Custom Attribute value deleted")})
    @RequestMapping(
            path = "/resources/{resourceName}/objects/{objectUuid}/{attributeUuid}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<ResponseAttributeDto> deleteAttributeContentForResource(
            @Parameter(description = "Resource Type") @PathVariable Resource resourceName,
            @Parameter(description = "Object UUID") @PathVariable String objectUuid,
            @Parameter(description = "Custom Attribute UUID") @PathVariable String attributeUuid
    ) throws NotFoundException, AttributeException;
}
