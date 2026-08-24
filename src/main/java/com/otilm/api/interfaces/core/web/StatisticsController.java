package com.otilm.api.interfaces.core.web;

import com.otilm.api.interfaces.AuthProtectedController;
import com.otilm.api.model.client.dashboard.CryptographicAssetStatisticsDto;
import com.otilm.api.model.client.dashboard.SigningRecordStatisticsDto;
import com.otilm.api.model.client.dashboard.SigningRecordStatisticsPeriod;
import com.otilm.api.model.client.dashboard.StatisticsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/statistics")
@Tag(name = "Statistics/Dashboard", description = "Statistics/Dashboard API")
public interface StatisticsController extends AuthProtectedController {

    @Operation(summary = "Get Dashboard/Statistics Details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Details retrieved")})
    @GetMapping(produces = {"application/json"})
    StatisticsDto getStatistics(
            @Parameter(description = "Include archived certificates in the statistics") @RequestParam(
                    value = "includeArchived", required = false, defaultValue = "false") boolean includeArchived);

    @Operation(operationId = "getSigningRecordStatistics", summary = "Get Signing Records dashboard statistics")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Signing Records statistics retrieved")})
    @GetMapping(path = "/signingRecords", produces = {"application/json"})
    SigningRecordStatisticsDto getSigningRecordStatistics(@Parameter(
            description = "Time window for the volume-over-time series (the count badges and breakdowns are window-independent)") @RequestParam(
                    value = "period", required = false,
                    defaultValue = SigningRecordStatisticsPeriod.Codes.LAST_7D) SigningRecordStatisticsPeriod period);

    /**
     * Gated by {@code ResourceAction.LIST} on {@code Resource.CBOM_ASSET}, as ratified for the inventory: the dashboard
     * deliberately shares the list permission rather than a gate of its own. Note the sync-completeness block
     * additionally reveals document-level sync counts — documents whose assets are not yet listed — which the ruling
     * accepts.
     */
    @Operation(operationId = "getCryptographicAssetStatistics",
            summary = "Get Cryptographic Asset Inventory dashboard statistics",
            description = "Returns count badges, distribution maps and the sync-completeness block for the "
                    + "cross-CBOM cryptographic asset inventory. Counts are computed over deduplicated assets, so an "
                    + "asset referenced by several CBOM documents counts once.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Cryptographic asset statistics retrieved")})
    @GetMapping(path = "/cbomAssets", produces = {"application/json"})
    CryptographicAssetStatisticsDto getCryptographicAssetStatistics();
}
