package com.otilm.api.model.client.cryptography.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.client.upload.UploadRequestDto;
import com.otilm.api.model.core.logging.Sensitive;
import com.otilm.api.model.core.secret.Passphrase;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Body of a key import: the key file and the metadata of the key it becomes.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "KeyImportRequestDto", description = """
        The key file to import and the metadata of the key it becomes. The key type is taken from the request path.
        """)
public class KeyImportRequestDto extends UploadRequestDto {

    @Schema(description = "Name of the imported key", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "name is required")
    private String name;

    @Schema(description = "Description of the imported key", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @ToString.Exclude
    @Sensitive
    @Schema(description = """
            Passphrase that opens the uploaded file. Absent for a file that carries no protection of its own, such as
            an unencrypted PEM private key. It opens only the uploaded file: the platform re-protects the material
            before it reaches a provider, so this value never leaves the platform.
            """, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Passphrase inputPassphrase;

    @Schema(description = """
            Whether the imported key may later be exported. Defaults to false, and false is final. It can only be
            requested where the token profile reports key export as available for the key type.
            """, requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "false")
    private Boolean exportable;

    @Schema(description = "Attributes required by the provider to import a key, from the import attribute schema",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttribute> importAttributes;

    @Schema(description = "Custom attributes for the imported key", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttribute> customAttributes;

    @Schema(description = "UUIDs of the groups to associate with the imported key",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<String> groupUuids;
}
