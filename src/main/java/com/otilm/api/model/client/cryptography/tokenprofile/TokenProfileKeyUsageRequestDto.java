package com.otilm.api.model.client.cryptography.tokenprofile;

import com.otilm.api.model.core.cryptography.key.KeyUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
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
public class TokenProfileKeyUsageRequestDto {

    @Schema(description = "Usages for the Key", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<KeyUsage> usage;
}
