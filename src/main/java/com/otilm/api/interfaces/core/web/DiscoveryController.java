package com.otilm.api.interfaces.core.web;

import com.otilm.api.exception.AlreadyExistException;
import com.otilm.api.exception.AttributeException;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.exception.NotFoundException;
import com.otilm.api.exception.SchedulerException;
import com.otilm.api.interfaces.AuthProtectedController;
import com.otilm.api.model.client.certificate.DiscoveryResponseDto;
import com.otilm.api.model.client.certificate.SearchRequestDto;
import com.otilm.api.model.client.discovery.DiscoveryCertificateResponseDto;
import com.otilm.api.model.client.discovery.DiscoveryDetailDto;
import com.otilm.api.model.client.discovery.DiscoveryDto;
import com.otilm.api.model.common.ErrorMessageDto;
import com.otilm.api.model.common.PaginationResponseDto;
import com.otilm.api.model.common.UuidDto;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.discovery.v2.DiscoverySupportedResourceDto;
import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.discovery.DiscoveryItemDto;
import com.otilm.api.model.core.scheduler.ScheduleDiscoveryDto;
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
import java.security.cert.CertificateException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/v1/discoveries")
@Tag(name = "Discovery Management", description = "Discovery Management API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "502", description = "Connector Error",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
        @ApiResponse(responseCode = "503", description = "Connector Communication Error",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})

public interface DiscoveryController extends AuthProtectedController {

    @Operation(summary = "List Discovery")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of available Discoveries")})
    @PostMapping(path = "/list", produces = {"application/json"})
    DiscoveryResponseDto listDiscoveries(@RequestBody SearchRequestDto request);

    @Operation(summary = "Discovery Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discovery details retrieved"),
            @ApiResponse(responseCode = "404", description = "Discovery not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @GetMapping(path = "/{uuid}", produces = {"application/json"})
    DiscoveryDetailDto getDiscovery(@Parameter(description = "Discovery UUID") @PathVariable String uuid)
            throws NotFoundException;

    @Operation(summary = "Discovery Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discovery details retrieved"),
            @ApiResponse(responseCode = "404", description = "Discovery not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @GetMapping(path = "/{uuid}/certificates", produces = {"application/json"})
    DiscoveryCertificateResponseDto getDiscoveryCertificates(
            @Parameter(description = "Discovery UUID") @PathVariable String uuid,
            @RequestParam(required = false) Boolean newlyDiscovered,
            @RequestParam(required = false, defaultValue = "10") int itemsPerPage,
            @RequestParam(required = false, defaultValue = "0") int pageNumber) throws NotFoundException;

    /**
     * Lists everything a run staged, whatever its resource type — the resource-agnostic companion to
     * {@link #getDiscoveryCertificates}, which keeps its certificate-specific columns; both exist on purpose.
     *
     * <p>
     * <b>Response:</b> {@link PaginationResponseDto} of {@link DiscoveryItemDto} — the shared paging envelope, and
     * Core's own view of a staged item rather than the connector's, because processing outcome is Core's to know.
     *
     * <p>
     * <b>Filters:</b> {@code resource} narrows to one resource type by wire code; {@code newlyDiscovered} behaves
     * exactly as on the certificate listing. Both optional; omitted means no filter.
     *
     * <p>
     * <b>Binding:</b> {@code resource} binds by wire code, never by Java enum member name — Core registers
     * {@code IPlatformEnumConverterFactory} in its web configuration for every {@code IPlatformEnum} parameter.
     */
    @Operation(summary = "List Discovered Items",
            description = "Returns one page of the items this Discovery staged, ordered by their run "
                    + "sequence, optionally narrowed to a single resource type. Certificates are included: a run "
                    + "against a v1 Discovery Provider stages certificates only, so its unfiltered listing contains "
                    + "exactly what the certificate listing contains, with sequence and uniqueRef synthesized by the "
                    + "platform (staging order and certificate fingerprint) because a v1 provider never numbered its "
                    + "items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discovered items retrieved"),
            @ApiResponse(responseCode = "404", description = "Discovery not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @GetMapping(path = "/{uuid}/items", produces = {"application/json"})
    PaginationResponseDto<DiscoveryItemDto> getDiscoveryItems(
            @Parameter(description = "Discovery UUID") @PathVariable String uuid,
            @Parameter(
                    description = "Resource type to list, identified by its wire code (e.g. \"certificates\", \"keys\"); omit to list every resource type this run discovered") @RequestParam(
                            required = false) Resource resource,
            @RequestParam(required = false) Boolean newlyDiscovered,
            @RequestParam(required = false, defaultValue = "10") int itemsPerPage,
            @RequestParam(required = false, defaultValue = "0") int pageNumber) throws NotFoundException;

    @Operation(summary = "Create Discovery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Discovery Created",
                    content = @Content(schema = @Schema(implementation = UuidDto.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),
            @ApiResponse(responseCode = "404", description = "Connector not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<?> createDiscovery(@RequestBody DiscoveryDto request) throws AlreadyExistException,
            NotFoundException, CertificateException, InterruptedException, ConnectorException, AttributeException;

    @Operation(summary = "Delete Discovery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Discovery deleted"),
            @ApiResponse(responseCode = "404", description = "Discovery not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @DeleteMapping(path = "/{uuid}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteDiscovery(@Parameter(description = "Discovery UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Delete Multiple Discoveries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Discoveries deleted"),
            @ApiResponse(responseCode = "404", description = "Discovery not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @DeleteMapping(produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDeleteDiscovery(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Discovery UUIDs",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {
                    @ExampleObject(
                            value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<String> discoveryUuids)
            throws NotFoundException;

    @Operation(operationId = "getDiscoverySearchableFields", summary = "Get Discovery searchable fields information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discovery searchable field information retrieved")})
    @GetMapping(path = "/search", produces = {"application/json"})
    List<SearchFieldDataByGroupDto> getSearchableFieldInformation();

    @Operation(summary = "Schedule Discovery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Discovery Scheduled",
                    content = @Content(schema = @Schema(implementation = UuidDto.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),
            @ApiResponse(responseCode = "404", description = "Connector not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @PostMapping(path = "/schedule", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<?> scheduleDiscovery(@RequestBody ScheduleDiscoveryDto scheduleDiscoveryDto)
            throws AlreadyExistException, CertificateException, InterruptedException, ConnectorException,
            SchedulerException, AttributeException, NotFoundException;

    /**
     * The three endpoints below are keyed by <strong>connector</strong> UUID, not by the run UUID every other endpoint
     * on this controller takes — they describe a Discovery Provider before any run exists. They live here rather than
     * on {@code ConnectorController} because they are specific to one provider interface, while that controller's
     * attribute endpoints are generic across function groups and cannot express a resource dimension; the same split
     * {@code AuthorityInstanceController} already uses. Core authorizes all three against the {@code CONNECTOR}
     * resource — object-level, the way {@code listAuthorityInstanceAttributes} is gated — never against
     * {@code DISCOVERY}, which has no object access and would silently skip per-connector ACLs.
     */
    @Operation(summary = "Get discoverable resources of a Discovery Provider",
            description = "Returns the resource types this Connector's discovery interface advertises, as "
                    + "synced from the Connector. Empty for a Connector implementing only the v1 discovery "
                    + "interface, which has no resource-type concept and always discovers certificates.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discoverable resources retrieved"),
            @ApiResponse(responseCode = "404", description = "Connector not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @GetMapping(path = "/{connectorUuid}/resources", produces = {"application/json"})
    List<DiscoverySupportedResourceDto> listDiscoveryResources(
            @Parameter(description = "Discovery Provider Connector UUID") @PathVariable String connectorUuid)
            throws NotFoundException;

    @Operation(summary = "Get run-level Discovery Attributes from a Discovery Provider",
            description = "Relays the run-level attribute definitions from the Connector's discovery "
                    + "interface: the schema that configures a discovery run as a whole and applies to "
                    + "every resource type the run targets.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Run-level Discovery Attributes received"),
            @ApiResponse(responseCode = "404", description = "Connector not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @GetMapping(path = "/{connectorUuid}/attributes", produces = {"application/json"})
    List<BaseAttribute> getDiscoveryAttributes(
            @Parameter(description = "Discovery Provider Connector UUID") @PathVariable String connectorUuid)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Get per-resource Discovery Attributes from a Discovery Provider",
            description = "Relays the attribute definitions that refine discovery of one resource type "
                    + "from the Connector's discovery interface.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Per-resource Discovery Attributes received"),
            @ApiResponse(responseCode = "404", description = "Connector not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "422", description = "Resource is not discoverable by this Connector",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @GetMapping(path = "/{connectorUuid}/{resource}/attributes", produces = {"application/json"})
    List<BaseAttribute> getDiscoveryResourceAttributes(
            @Parameter(description = "Discovery Provider Connector UUID") @PathVariable String connectorUuid,
            @Parameter(
                    description = "Resource type, identified by its wire code (e.g. \"certificates\", \"keys\")") @PathVariable Resource resource)
            throws NotFoundException, ConnectorException;

    /**
     * Stop, resume and cancel below share one legality matrix, stated once here rather than three times in prose. Read
     * {@code inProgress} as covering both the scanning and the incremental download phases, which interleave in
     * discovery v2.
     *
     * <pre>
     *            inProgress   stopped   processing   terminal
     *   stop        legal       409        409         409
     *   resume       409       legal       409         409
     *   cancel      legal      legal       409         409
     * </pre>
     *
     * <p>
     * Once a run reaches {@code processing} the Discovery Provider owns nothing and the remaining work is not
     * abortable, which is why all three are refused there as well as in a terminal status.
     *
     * <p>
     * Every one of them requires a Discovery Provider implementing the v2 discovery interface. A run created against a
     * v1 Provider answers 422, not 409: the request is not illegal for the run's status, it is unsupported by the
     * Provider the run belongs to.
     */
    @Operation(summary = "Stop Discovery",
            description = "Asks the Discovery Provider to suspend an in-progress run, keeping everything "
                    + "already staged and the Provider-side checkpoint needed to resume it. Legal only "
                    + "while the run is in progress; see the legality matrix on this interface.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Discovery stopped"),
            @ApiResponse(responseCode = "404", description = "Discovery not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "409", description = "Discovery is not in progress, so it cannot be stopped",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "422", description = "Discovery Provider does not support stopping a run",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PatchMapping(path = "/{uuid}/stop", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void stopDiscovery(@Parameter(description = "Discovery UUID") @PathVariable String uuid)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Resume Discovery",
            description = "Asks the Discovery Provider to continue a stopped run from its checkpoint. "
                    + "Legal only from the stopped status; see the legality matrix on this interface. A "
                    + "Provider that has since lost the checkpoint fails the run instead of restarting "
                    + "it, so a resume is never silently a fresh scan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Discovery resumed"),
            @ApiResponse(responseCode = "404", description = "Discovery not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "409", description = "Discovery is not stopped, so it cannot be resumed",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "422", description = "Discovery Provider does not support resuming a run",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PatchMapping(path = "/{uuid}/resume", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void resumeDiscovery(@Parameter(description = "Discovery UUID") @PathVariable String uuid)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Cancel Discovery",
            description = "Abandons a run for good: the Discovery Provider releases it and the items "
                    + "already staged are never processed. Legal while the run is in progress or "
                    + "stopped; see the legality matrix on this interface. This is irreversible - a "
                    + "cancelled run cannot be resumed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Discovery cancelled"),
            @ApiResponse(responseCode = "404", description = "Discovery not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "Discovery is neither in progress nor stopped, so it cannot be cancelled",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "422", description = "Discovery Provider does not support cancelling a run",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PatchMapping(path = "/{uuid}/cancel", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancelDiscovery(@Parameter(description = "Discovery UUID") @PathVariable String uuid)
            throws NotFoundException, ConnectorException;

}
