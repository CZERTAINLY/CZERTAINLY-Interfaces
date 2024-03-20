package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.core.auth.Resource;
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
@RequestMapping("/v1/rules")
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
public interface RuleController {

    @Operation(summary = "List Rules")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of rules fetched")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    List<RuleDto> listRules(@RequestParam(required = false) Resource resource);

    @Operation(summary = "Create Rule")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Rule created")})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    RuleDetailDto createRule(@RequestBody RuleRequestDto request);

    @Operation(summary = "Get Rule details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Rule details retrieved")})
    @RequestMapping(path = "/{ruleUuid}", method = RequestMethod.GET, produces = {"application/json"})
    RuleDetailDto getRule(@Parameter(description = "Rule UUID") @PathVariable String ruleUuid) throws NotFoundException;

    @Operation(summary = "Update Rule")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Rule details updated")})
    @RequestMapping(path = "/{ruleUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    RuleDetailDto updateRule(@Parameter(description = "Rule UUID") @PathVariable String ruleUuid, @RequestBody UpdateRuleRequestDto request) throws NotFoundException;

    @Operation(summary = "Delete Rule")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Rule deleted")})
    @RequestMapping(path = "/{ruleUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRule(@Parameter(description = "Rule UUID") @PathVariable String ruleUuid) throws NotFoundException;

    @Operation(summary = "List Condition Groups")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of condition groups fetched")})
    @RequestMapping(path = "/conditionGroups", method = RequestMethod.GET, produces = {"application/json"})
    List<RuleConditionGroupDto> listConditionGroups(@RequestParam(required = false) Resource resource);

    @Operation(summary = "Create Condition Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Condition group created")})
    @RequestMapping(path = "/conditionGroups", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    RuleConditionGroupDetailDto createConditionGroup(@RequestBody RuleConditionGroupRequestDto request);

    @Operation(summary = "Get Condition Group details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Condition group details retrieved")})
    @RequestMapping(path = "/conditionGroups/{conditionGroupUuid}", method = RequestMethod.GET, produces = {"application/json"})
    RuleConditionGroupDetailDto getConditionGroup(@Parameter(description = "Condition Group UUID") @PathVariable String conditionGroupUuid) throws NotFoundException;

    @Operation(summary = "Update Condition Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Condition group updated")})
    @RequestMapping(path = "/conditionGroups/{conditionGroupUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    RuleConditionGroupDetailDto updateConditionGroup(@Parameter(description = "Condition Group UUID") @PathVariable String conditionGroupUuid, @RequestBody UpdateRuleConditionGroupRequestDto request) throws NotFoundException;

    @Operation(summary = "Delete Condition Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Condition group deleted")})
    @RequestMapping(path = "/conditionGroups/{conditionGroupUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteConditionGroup(@Parameter(description = "Condition Group UUID") @PathVariable String conditionGroupUuid) throws NotFoundException;

    @Operation(summary = "List Action Groups")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of action groups fetched")})
    @RequestMapping(path = "/actionGroups", method = RequestMethod.GET, produces = {"application/json"})
    List<RuleActionGroupDto> listActionGroups(@RequestParam(required = false) Resource resource);

    @Operation(summary = "Create Action Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Action group created")})
    @RequestMapping(path = "/actionGroups", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    RuleActionGroupDetailDto createActionGroup(@RequestBody RuleActionGroupRequestDto request);

    @Operation(summary = "Get Action Group Details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Action group details retrieved")})
    @RequestMapping(path = "/actionGroups/{actionGroupUuid}", method = RequestMethod.GET, produces = {"application/json"})
    RuleActionGroupDetailDto getActionGroup(@Parameter(description = "Action Group UUID") @PathVariable String actionGroupUuid) throws NotFoundException;

    @Operation(summary = "Update Action Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Action group updated")})
    @RequestMapping(path = "/actionGroups/{actionGroupUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    RuleActionGroupDetailDto updateActionGroup(@Parameter(description = "Action Group UUID") @PathVariable String actionGroupUuid, @RequestBody UpdateRuleActionGroupRequestDto request) throws NotFoundException;

    @Operation(summary = "Delete Action Group")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Action group deleted")})
    @RequestMapping(path = "/actionGroups/{actionGroupUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteActionGroup(@Parameter(description = "Action Group UUID") @PathVariable String actionGroupUuid) throws NotFoundException;

    @Operation(summary = "List Triggers")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of triggers")})
    @RequestMapping(path = "/triggers", method = RequestMethod.GET, produces = {"application/json"})
    List<RuleTriggerDto> listTriggers(@RequestParam(required = false) Resource resource, @RequestParam(required = false) Resource triggerResource);

    @Operation(summary = "Create Trigger")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Trigger created")})
    @RequestMapping(path = "/triggers", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    RuleTriggerDetailDto createTrigger(@RequestBody RuleTriggerRequestDto request);

    @Operation(summary = "Get Trigger details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Trigger details retrieved")})
    @RequestMapping(path = "/triggers/{triggerUuid}", method = RequestMethod.GET, produces = {"application/json"})
    RuleTriggerDetailDto getTrigger(@Parameter(description = "Trigger UUID") @PathVariable String triggerUuid) throws NotFoundException;

    @Operation(summary = "Update Trigger")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Trigger updated")})
    @RequestMapping(path = "/triggers/{triggerUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    RuleTriggerDetailDto updateTrigger(@Parameter(description = "Trigger UUID") @PathVariable String triggerUuid, @RequestBody UpdateRuleTriggerRequestDto request) throws NotFoundException;

    @Operation(summary = "Delete Trigger")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Trigger deleted")})
    @RequestMapping(path = "/triggers/{triggerUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTrigger(@Parameter(description = "Trigger UUID") @PathVariable String triggerUuid) throws NotFoundException;

}
