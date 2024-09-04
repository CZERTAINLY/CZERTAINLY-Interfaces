package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.SchedulerException;
import com.czertainly.api.model.core.scheduler.PaginationRequestDto;
import com.czertainly.api.model.core.scheduler.ScheduledJobDetailDto;
import com.czertainly.api.model.core.scheduler.ScheduledJobHistoryResponseDto;
import com.czertainly.api.model.core.scheduler.ScheduledJobsResponseDto;
import com.czertainly.api.model.scheduler.UpdateScheduledJob;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/scheduler/jobs")
@Tag(name = "Scheduled Jobs Management", description = "Scheduled Jobs Management API")
public interface SchedulerController {

    @Operation(summary = "List of scheduled jobs")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of scheduled jobs fetched")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    ScheduledJobsResponseDto listScheduledJobs(@Parameter(in = ParameterIn.QUERY) PaginationRequestDto pagination);

    @Operation(summary = "Scheduled job detail")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Scheduled job detail retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    ScheduledJobDetailDto getScheduledJobDetail(@Parameter(description = "Scheduled job UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Edit Scheduled job")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Scheduled job updated")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.PUT, consumes = {"application/json"},produces = {"application/json"})
    ScheduledJobDetailDto updateScheduledJob(@Parameter(description = "Scheduled job UUID") @PathVariable String uuid, @RequestBody UpdateScheduledJob request) throws NotFoundException, SchedulerException;

    @Operation(summary = "Delete Scheduled job")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Scheduled job deleted")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteScheduledJob(@Parameter(description = "Scheduled job UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Scheduled job history")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Scheduled job history retrieved")})
    @RequestMapping(path = "/{uuid}/history", method = RequestMethod.GET, produces = {"application/json"})
    ScheduledJobHistoryResponseDto getScheduledJobHistory(@Parameter(in = ParameterIn.QUERY) PaginationRequestDto pagination, @Parameter(description = "Scheduled job UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Enabling of Scheduled job")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Scheduled job enabled")})
    @RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableScheduledJob(@Parameter(description = "Scheduled job UUID") @PathVariable String uuid) throws NotFoundException, SchedulerException;

    @Operation(summary = "Disabling of Scheduled job")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Scheduled job disabled")})
    @RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableScheduledJob(@Parameter(description = "Scheduled job UUID") @PathVariable String uuid) throws NotFoundException, SchedulerException;

}
