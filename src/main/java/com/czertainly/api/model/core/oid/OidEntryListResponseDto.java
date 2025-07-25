package com.czertainly.api.model.core.oid;

import com.czertainly.api.model.core.scheduler.PaginationResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OidEntryListResponseDto extends PaginationResponseDto {

    @Schema(description = "List of OID entries")
    private List<OidEntryResponseDto> oidEntries;
}
