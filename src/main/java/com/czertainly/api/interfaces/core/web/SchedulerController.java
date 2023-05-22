package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.core.scheduler.PaginationRequestDto;
import com.czertainly.api.model.core.scheduler.ScheduledJobHistoryResponseDto;
import com.czertainly.api.model.core.scheduler.ScheduledJobsResponseDto;
import com.czertainly.api.model.core.scheduler.ScheduledJobDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/scheduler/jobs")
@Tag(name = "Scheduler Management", description = "Scheduler Management API")
public interface SchedulerController {

    @Operation(summary = "List of scheduled jobs")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of scheduled jobs fetched")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    ScheduledJobsResponseDto listScheduledJobs(@RequestBody PaginationRequestDto pagination);

    @Operation(summary = "Scheduled job detail")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Scheduled job detail retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    ScheduledJobDetailDto getScheduledJobDetail(@Parameter(description = "Scheduled job UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Delete Scheduled job")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Scheduled job deleted")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteScheduledJob(@Parameter(description = "Scheduled job UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Scheduled job history")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Scheduled job history retrieved")})
    @RequestMapping(path = "/{uuid}/history", method = RequestMethod.GET, produces = {"application/json"})
    ScheduledJobHistoryResponseDto getScheduledJobHistory(@RequestBody PaginationRequestDto pagination, @Parameter(description = "Scheduled job UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Enabling of Scheduled job")
    @ApiResponses(value = { @ApiResponse(responseCode = "206", description = "Scheduled job enabled")})
    @RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PATCH)
    ResponseEntity<?> enableScheduledJob(@Parameter(description = "Scheduled job UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Disabling of Scheduled job")
    @ApiResponses(value = { @ApiResponse(responseCode = "206", description = "Scheduled job disabled")})
    @RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PATCH)
    ResponseEntity<?> disableScheduledJob(@Parameter(description = "Scheduled job UUID") @PathVariable String uuid) throws NotFoundException;

}
