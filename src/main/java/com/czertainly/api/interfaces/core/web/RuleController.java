package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.workflows.*;
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
@RequestMapping("/v1/workflows")
@Tag(name = "Workflow Rules Management", description = "Workflow Rules Management API")
@ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))), @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())), @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))), @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
public interface RuleController {

    // CONDITIONS

    @Operation(summary = "List Conditions")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of conditions fetched")})
    @RequestMapping(path = "/conditions", method = RequestMethod.GET, produces = {"application/json"})
    List<ConditionDto> listConditions(@RequestParam(required = false) Resource resource);

    @Operation(summary = "Create Condition")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Condition created")})
    @RequestMapping(path = "/conditions", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ConditionDto createCondition(@RequestBody ConditionRequestDto request) throws AlreadyExistException;

    @Operation(summary = "Get Condition details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Condition details retrieved")})
    @RequestMapping(path = "/conditions/{conditionUuid}", method = RequestMethod.GET, produces = {"application/json"})
    ConditionDto getCondition(@Parameter(description = "Condition UUID") @PathVariable String conditionUuid) throws NotFoundException;

    @Operation(summary = "Update Condition")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Condition updated")})
    @RequestMapping(path = "/conditions/{conditionUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    ConditionDto updateCondition(@Parameter(description = "Condition UUID") @PathVariable String conditionUuid, @RequestBody UpdateConditionRequestDto request) throws NotFoundException;

    @Operation(summary = "Delete Condition")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Condition deleted")})
    @RequestMapping(path = "/conditions/{conditionUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCondition(@Parameter(description = "Condition UUID") @PathVariable String conditionUuid) throws NotFoundException;

    // RULES

    @Operation(summary = "List Rules")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of rules fetched")})
    @RequestMapping(path = "/rules", method = RequestMethod.GET, produces = {"application/json"})
    List<RuleDto> listRules(@RequestParam(required = false) Resource resource);

    @Operation(summary = "Create Rule")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Rule created")})
    @RequestMapping(path = "/rules", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    RuleDetailDto createRule(@RequestBody RuleRequestDto request) throws NotFoundException, AlreadyExistException;

    @Operation(summary = "Get Rule details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Rule details retrieved")})
    @RequestMapping(path = "/rules/{ruleUuid}", method = RequestMethod.GET, produces = {"application/json"})
    RuleDetailDto getRule(@Parameter(description = "Rule UUID") @PathVariable String ruleUuid) throws NotFoundException;

    @Operation(summary = "Update Rule")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Rule details updated")})
    @RequestMapping(path = "/rules/{ruleUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    RuleDetailDto updateRule(@Parameter(description = "Rule UUID") @PathVariable String ruleUuid, @RequestBody UpdateRuleRequestDto request) throws NotFoundException;

    @Operation(summary = "Delete Rule")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Rule deleted")})
    @RequestMapping(path = "/rules/{ruleUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRule(@Parameter(description = "Rule UUID") @PathVariable String ruleUuid) throws NotFoundException;

}
