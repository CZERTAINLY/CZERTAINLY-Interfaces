package com.otilm.api.model.client.cryptography.tokenprofile;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BulkTokenProfileKeyUsageRequestDto extends TokenProfileKeyUsageRequestDto {

    @Schema(description = "Token Profile UUIDs", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<UUID> uuids;
}
