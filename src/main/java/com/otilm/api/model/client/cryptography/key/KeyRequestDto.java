package com.otilm.api.model.client.cryptography.key;

import com.otilm.api.model.client.attribute.RequestAttribute;
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
public class KeyRequestDto {

    @Schema(description = "Name of the Cryptographic Key", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Description of the Cryptographic Key", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "UUIDs of the groups to associate with key")
    private List<String> groupUuids;

    @Schema(description = "List of Attributes to create a Key", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RequestAttribute> attributes;

    @Schema(description = "Custom Attributes for the key")
    private List<RequestAttribute> customAttributes;

    @Schema(description = "Enabled status of created key. True = Enabled, False = Disabled",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "false")
    private Boolean enabled;

    @Schema(description = """
            Whether the created key may later be exported. Defaults to false, and false is final: a key created
            non-exportable can never become exportable. It can only be requested where the token profile reports key
            export as available for the key type.
            """, requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "false")
    private Boolean exportable;

    /**
     * The signature this class carried before {@code exportable} was added, so a caller that constructs it positionally
     * still compiles. The request then leaves the exportable intent unstated, which the platform reads as false.
     */
    public KeyRequestDto(String name, String description, List<String> groupUuids, List<RequestAttribute> attributes,
            List<RequestAttribute> customAttributes, Boolean enabled) {
        this(name, description, groupUuids, attributes, customAttributes, enabled, null);
    }
}
