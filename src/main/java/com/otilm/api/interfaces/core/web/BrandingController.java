package com.otilm.api.interfaces.core.web;

import com.otilm.api.interfaces.NoAuthController;
import com.otilm.api.model.core.branding.PublicBrandingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Serves branding to callers that have not authenticated yet, which is what lets the login page render in the
 * operator's colors instead of flashing the platform default first. Reading branding is therefore unauthenticated,
 * while changing it requires {@code ResourceAction.UPDATE_BRANDING} through the settings API.
 */
@RequestMapping("/v1/branding")
@Tag(name = "Branding", description = "Public branding API")
public interface BrandingController extends NoAuthController {

    @Operation(summary = "Get branding available to unauthenticated clients")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Branding retrieved")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    PublicBrandingDto getBranding();
}
