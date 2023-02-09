package com.czertainly.api.model.client.cryptography.tokenprofile;

import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TokenProfileKeyUsageRequestDto {

    @Schema(
            description = "Usages for the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<KeyUsage> usage;
}
