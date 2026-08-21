package com.otilm.api.model.core.signing.signingrecord;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.client.signing.profile.SigningProfileListDto;
import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import com.otilm.api.model.core.search.AttributeProjectable;
import com.otilm.api.model.core.search.FilterFieldSource;
import com.otilm.api.model.core.signing.SigningProtocol;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(name = "SigningRecordListDto", description = "Signing Record details for listing")
public class SigningRecordListDto extends NameAndUuidDto implements AttributeProjectable {

    @Schema(description = "Signing Profile used to produce this Signing Record",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private SigningProfileListDto signingProfile;

    @Schema(description = "Signing protocol used to produce this Signing Record",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private SigningProtocol protocol;

    @Schema(description = "Claimed signing time embedded in the signature structure by the signing operation.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant signingTime;

    @Schema(description = "Server time at which the Signing Record was created in the system. "
            + "This timestamp is set by the platform and is independent of the cryptographic signing time.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = AttributeProjectable.ATTRIBUTE_VALUES_DESCRIPTION,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<FilterFieldSource, Map<String, List<BaseAttributeContentV3<?>>>> attributeValues;
}
