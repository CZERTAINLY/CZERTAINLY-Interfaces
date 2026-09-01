package com.otilm.api.interfaces.core.web;

import com.otilm.api.exception.AlreadyExistException;
import com.otilm.api.exception.CbomRepositoryException;
import com.otilm.api.exception.NotFoundException;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.interfaces.AuthProtectedController;
import com.otilm.api.model.client.certificate.SearchRequestDto;
import com.otilm.api.model.common.BulkActionMessageDto;
import com.otilm.api.model.common.ErrorMessageDto;
import com.otilm.api.model.common.PaginationResponseDto;
import com.otilm.api.model.core.cbom.CbomDetailDto;
import com.otilm.api.model.core.cbom.CbomDto;
import com.otilm.api.model.core.cbom.CbomUploadRequestDto;
import com.otilm.api.model.core.search.ConfigurableColumnsDocs;
import com.otilm.api.model.core.search.SearchFieldDataByGroupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/v1/cboms")
@Tag(name = "CBOM management", description = "CBOM management API")
public interface CbomController extends AuthProtectedController {

    @Operation(summary = "List CBOMs",
            description = ConfigurableColumnsDocs.SORT_AND_COLUMNS + ConfigurableColumnsDocs.ATTRIBUTE_PROJECTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available CBOMs"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    PaginationResponseDto<CbomDto> listCboms(@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            schema = @Schema(implementation = SearchRequestDto.class),
            examples = {@ExampleObject(name = "With ordering and columns", value = """
                    {
                      "pageNumber": 1,
                      "itemsPerPage": 10,
                      "filters": [],
                      "sort": {"fieldSource": "property", "fieldIdentifier": "CBOM_TIMESTAMP", "direction": "desc"},
                      "columns": [
                        {"fieldSource": "property", "fieldIdentifier": "CBOM_SERIAL_NUMBER"},
                        {"fieldSource": "property", "fieldIdentifier": "CBOM_VERSION"},
                        {"fieldSource": "property", "fieldIdentifier": "CBOM_TIMESTAMP"}
                      ]
                    }""")})) @Valid @RequestBody SearchRequestDto request);

    @Operation(summary = "CBOM detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CBOM details retrieved"),
            @ApiResponse(responseCode = "404", description = "CBOM not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @GetMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    CbomDetailDto getCbomDetail(@Parameter(description = "CBOM entry UUID") @PathVariable UUID uuid)
            throws NotFoundException, CbomRepositoryException;

    @Operation(summary = "List CBOM versions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of CBOM versions retrieved"),
            @ApiResponse(responseCode = "404", description = "CBOM not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @GetMapping(path = "/{uuid}/versions", produces = {MediaType.APPLICATION_JSON_VALUE})
    List<CbomDto> listCbomVersions(@Parameter(description = "CBOM entry UUID") @PathVariable UUID uuid)
            throws NotFoundException;

    @Operation(summary = "Upload CBOM")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CBOM uploaded"),
            @ApiResponse(responseCode = "400", description = "Invalid CBOM content",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @PostMapping(path = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    CbomDto uploadCbom(@RequestBody CbomUploadRequestDto request)
            throws ValidationException, AlreadyExistException, CbomRepositoryException;

    @Operation(summary = "Delete CBOM entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "CBOM entry deleted"),
            @ApiResponse(responseCode = "404", description = "CBOM entry not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    void deleteCbom(@Parameter(description = "CBOM entry UUID") @PathVariable UUID uuid) throws NotFoundException;

    @Operation(summary = "Delete multiple CBOM entries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CBOM entries deleted"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    List<BulkActionMessageDto> bulkDeleteCbom(@RequestBody List<UUID> uuids);

    @Operation(summary = "Sync CBOMs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "CBOMs synced"),
            @ApiResponse(responseCode = "500", description = "Internal problem with repository",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @PostMapping(path = "/sync")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void sync() throws CbomRepositoryException;

    @Operation(operationId = "getCbomSearchableFields", summary = "Get Cbom searchable fields information",
            description = ConfigurableColumnsDocs.CATALOGUE_FLAGS)
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Cbom searchable field information retrieved")})
    @GetMapping(path = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    List<SearchFieldDataByGroupDto> getSearchableFieldInformation();
}
