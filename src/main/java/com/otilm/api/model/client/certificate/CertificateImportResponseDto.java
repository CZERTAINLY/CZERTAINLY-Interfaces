package com.otilm.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * What became of each entry a caller selected.
 */
@Getter
@Setter
@ToString
@Schema(name = "CertificateImportResponseDto", description = "Outcome of importing the selected entries")
public class CertificateImportResponseDto {

    @Schema(description = "One outcome per selected entry, in the order they were selected",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CertificateImportResultDto> results;
}
