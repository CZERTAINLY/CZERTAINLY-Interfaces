package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.client.certificate.SearchRequestDto;
import com.czertainly.api.model.core.oid.OidEntryListResponseDto;
import com.czertainly.api.model.core.oid.OidEntryResponseDto;
import com.czertainly.api.model.core.oid.OidEntryRequestDto;
import com.czertainly.api.model.core.oid.OidEntryUpdateRequestDto;
import com.czertainly.api.model.core.search.SearchFieldDataByGroupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/oids")
@Tag(name = "OID Management", description = "OID Management API")
public interface OidEntryController extends AuthProtectedController {

    @Operation(summary = "Create a new OID entry")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OID entry created")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    OidEntryResponseDto createOidEntry(@Valid @RequestBody OidEntryRequestDto requestDto);

    @Operation(summary = "Get OID entry by OID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OID entry retrieved")})
    @GetMapping(path = "/{oid}", produces = MediaType.APPLICATION_JSON_VALUE)
    OidEntryResponseDto getOidEntry(@Parameter(description = "OID identifier") @PathVariable String oid) throws NotFoundException;

    @Operation(summary = "Edit an existing OID entry")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OID entry updated")})
    @PutMapping(path = "/{oid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    OidEntryResponseDto editOidEntry(
            @Parameter(description = "OID identifier") @PathVariable String oid,
            @Valid @RequestBody OidEntryUpdateRequestDto updateDto) throws NotFoundException;

    @Operation(summary = "Delete an OID entry by OID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "OID entry deleted")})
    @DeleteMapping("/{oid}")
    void deleteOidEntry(@Parameter(description = "OID identifier") @PathVariable String oid) throws NotFoundException;

    @Operation(summary = "Bulk delete OID entries")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "OID entries deleted")})
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    void bulkDeleteOidEntry(@RequestBody List<String> oids);

    @Operation(summary = "List OID entries with filtering and pagination")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OID entries retrieved")})
    @PostMapping(path = "/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    OidEntryListResponseDto listOidEntries(@RequestBody SearchRequestDto searchRequestDto);

    @Operation(summary = "Get searchable filter fields for OID entries")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Searchable fields retrieved")})
    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    List<SearchFieldDataByGroupDto> getSearchableInformation();
}
