package com.otilm.api.interfaces.connector.discovery.v2;

import com.otilm.api.interfaces.connector.common.v2.AuthProtectedConnectorController;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.discovery.v2.DiscoverySupportedResourceDto;
import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v2/discoveryProvider")
@Tag(name = "Discovery Metadata v2",
        description = "Stateless discovery v2 metadata surface: which resource types and capabilities "
                + "this connector supports, and the attribute schema for configuring a run.")
public interface DiscoveryMetadataController extends AuthProtectedConnectorController {

    @Operation(summary = "List supported resources",
            description = "Returns the resource types this connector can discover, and which capabilities "
                    + "it supports for each.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Supported resources and per-resource capabilities retrieved"))
    @GetMapping(path = "/resources", produces = MediaType.APPLICATION_JSON_VALUE)
    List<DiscoverySupportedResourceDto> listSupportedResources();

    @Operation(summary = "List run-level attributes",
            description = "Returns the attribute schema that configures a discovery run as a whole and "
                    + "applies to every resource type targeted by that run (for example, target addresses "
                    + "or address ranges). Values collected against this schema are replayed back to the "
                    + "connector as the run-level attributes on every discovery lifecycle call.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Run-level attribute schema retrieved"))
    @GetMapping(path = "/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listRunAttributes();

    /**
     * The {@code resource} path segment binds by the {@link Resource} wire code (e.g.
     * {@code "certificates"}, {@code "keys"}), not the Java enum member name — the same
     * converter requirement carried by the platform's other kind-in-path controllers (see the
     * secrets v1 precedent, {@code SecretController#getSecretAttributes}). Register
     * {@code com.otilm.api.config.converter.IPlatformEnumConverterFactory} (for example via
     * {@code WebMvcConfigurer#addFormatters}) to bind this and every other
     * {@code IPlatformEnum}-typed path variable by code without writing a converter by hand.
     */
    @Operation(summary = "List per-resource attributes",
            description = "Returns the attribute schema that refines discovery of a single resource type. "
                    + "Values collected against this schema are replayed back to the connector as the "
                    + "per-resource attributes, keyed by resource, on every discovery lifecycle call.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Per-resource attribute schema retrieved"))
    @GetMapping(path = "/{resource}/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listResourceAttributes(
            @Parameter(description = "Resource type, identified by its wire code (e.g. \"certificates\", \"keys\")")
            @PathVariable Resource resource);
}
