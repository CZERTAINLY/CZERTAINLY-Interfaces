package com.otilm.api.model.client.inspection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otilm.api.model.client.upload.UploadRequestDto;
import com.otilm.api.model.core.logging.Sensitive;
import com.otilm.api.model.core.secret.Passphrase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Body of an inspection: the file to read and the options for reading it.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "InspectionRequestDto", description = "The file to read and the options for reading it")
public class InspectionRequestDto extends UploadRequestDto {

    @ToString.Exclude
    @Sensitive
    @Schema(description = "Passphrase that opens the uploaded file. Absent for a file that carries no protection of "
            + "its own.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Passphrase passphrase;

    @Schema(description = "Token profile the caller intends to import key material into. When given, each entry is "
            + "reported with whether that profile can hold it.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String tokenProfileUuid;
}
