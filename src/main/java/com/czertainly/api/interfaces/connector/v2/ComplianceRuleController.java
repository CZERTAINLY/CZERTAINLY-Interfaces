package com.czertainly.api.interfaces.connector.v2;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.AuthProtectedConnectorController;
import com.czertainly.api.model.connector.compliance.v2.ComplianceGroupResponseDto;
import com.czertainly.api.model.connector.compliance.v2.ComplianceRuleResponseDto;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/v2/complianceProvider/{kind}")
@Tag(
        name = "Compliance Rules",
        description = "Compliance Provider rules API. " +
                "Used to get the list of rules provided by the connector. " +
                "These rules will be made available for the users to choose from the list." +
                "To check for the compliance of a certificate, the Connector accepts " +
                "certificate content and the list of rule references. Once the values are received, compliance is " +
                "checked based on the rules."
)
public interface ComplianceRuleController extends AuthProtectedConnectorController {
    @GetMapping(
            path = "/rules",
            produces = {"application/json"}
    )
    @Operation(
            summary = "Get list of rules"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rules retrieved successfully"
                    )
            }
    )
    List<ComplianceRuleResponseDto> getRules(
            @Parameter(description = "Connector kind") @PathVariable String kind,
            @RequestParam(required = false) Resource resource,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String format
    ) throws IOException, NotFoundException;

    @GetMapping(
            path = "/groups",
            produces = {"application/json"}
    )
    @Operation(
            summary = "Get list of groups"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Groups retrieved successfully"
                    )
            }
    )
    List<ComplianceGroupResponseDto> getGroups(
            @Parameter(description = "Connector kind") @PathVariable String kind,
            @RequestParam(required = false) Resource resource
    ) throws IOException, NotFoundException;

    @GetMapping(
            path = "/groups/{uuid}",
            produces = {"application/json"}
    )
    @Operation(
            summary = "Get list of rules for a group"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rules retrieved successfully"
                    )
            }
    )
    List<ComplianceRuleResponseDto> getGroupRules(@Parameter(description = "Connector kind") @PathVariable String kind,
                                                   @Parameter(description = "Group kind") @PathVariable UUID uuid)
            throws IOException, NotFoundException;
}