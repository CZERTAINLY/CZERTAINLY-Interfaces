package com.czertainly.api.model.client.cryptography.tokenprofile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BulkTokenProfileKeyUsageRequestDto extends TokenProfileKeyUsageRequestDto {

    @Schema(
            description = "Token Profile UUIDs",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<UUID> uuids;
}
