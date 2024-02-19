package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.core.rules.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/rules")
@Tag(name = "Rules Management", description = "Rules Management API")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = @Content(schema = @Schema())
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content
                )
        })
public interface RulesController {

    @Operation(summary = "List Rules")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of rules fetched")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    List<RuleDto> listRules();

    @Operation(summary = "Create Rule")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Rule created")})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    RuleDto createRule(@RequestBody RuleRequestDto request);

    @Operation(summary = "Update Rule")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Rule details updated")})
    @RequestMapping(path = "/{ruleUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    RuleDto updateRule(@Parameter(description = "Rule UUID") @PathVariable String ruleUuid, @RequestBody RuleRequestDto request);

    @Operation(summary = "Delete Rule")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Rule deleted")})
    @RequestMapping(path = "/{ruleUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRule(@Parameter(description = "Rule UUID") @PathVariable String ruleUuid);

    @Operation(summary = "List Condition Groups")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of condition groups fetched")})
    @RequestMapping(path = "/conditionGroups", method = RequestMethod.GET, produces = {"application/json"})
    List<RuleConditionGroupDto> listConditionGroups();

    @Operation(summary = "Create Condition Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Condition group created")})
    @RequestMapping(path = "/conditionGroups", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    RuleConditionGroupDto createConditionGroup(@RequestBody RuleConditionGroupRequestDto request);

    @Operation(summary = "Update Condition Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Condition group updated")})
    @RequestMapping(path = "/conditionGroups/{conditionGroupUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    RuleConditionGroupDto updateConditionGroup(@Parameter(description = "Condition Group UUID") @PathVariable String conditionGroupUuid, @RequestBody RuleConditionGroupRequestDto request);

    @Operation(summary = "Delete Condition Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Condition group deleted")})
    @RequestMapping(path = "/conditionGroups/{conditionGroupUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteConditionGroup(@Parameter(description = "Condition Group UUID") @PathVariable String conditionGroupUuid);

    @Operation(summary = "List Action Groups")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of action groups fetched")})
    @RequestMapping(path = "/actionGroups", method = RequestMethod.GET, produces = {"application/json"})
    List<RuleActionGroupDto> listActionGroups();

    @Operation(summary = "Create Action Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Action group created")})
    @RequestMapping(path = "/actionGroups", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    RuleActionGroupDto createActionGroup(@RequestBody RuleActionGroupRequestDto request);

    @Operation(summary = "Update Action Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Action group updated")})
    @RequestMapping(path = "/actionGroups/{actionGroupUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    RuleActionGroupDto updateActionGroup(@Parameter(description = "Action Group UUID") @PathVariable String actionGroupUuid, @RequestBody RuleActionGroupRequestDto request);

    @Operation(summary = "Delete Action Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Action group deleted")})
    @RequestMapping(path = "/actionGroups/{actionGroupUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteActionGroup(@Parameter(description = "Action Group UUID") @PathVariable String actionGroupUuid);

    @Operation(summary = "List Triggers")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of triggers")})
    @RequestMapping(path = "/triggers", method = RequestMethod.GET, produces = {"application/json"})
    List<RuleTriggerDto> listTriggers();

    @Operation(summary = "Create Trigger")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Trigger created")})
    @RequestMapping(path = "/triggers", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    RuleTriggerDto createTrigger(@RequestBody RuleTriggerRequestDto request);

    @Operation(summary = "Update Trigger")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Trigger updated")})
    @RequestMapping(path = "/triggers/{triggerUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    RuleTriggerDto updateTrigger(@Parameter(description = "Trigger UUID") @PathVariable String triggerUuid, @RequestBody RuleTriggerRequestDto request);

    @Operation(summary = "Delete Trigger")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Trigger deleted")})
    @RequestMapping(path = "/triggers/{triggerUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTrigger(@Parameter(description = "Trigger UUID") @PathVariable String triggerUuid);

}
