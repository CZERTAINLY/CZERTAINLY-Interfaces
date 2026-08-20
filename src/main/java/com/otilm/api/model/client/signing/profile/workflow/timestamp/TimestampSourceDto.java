package com.otilm.api.model.client.signing.profile.workflow.timestamp;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * Where a content-signing profile gets the timestamps for its TIMESTAMPED, LONG_TERM and ARCHIVAL levels. The reference
 * is discriminated so an external RFC 3161 source can be added without reshaping the contract.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({@Type(value = InternalTimestampSourceDto.class, name = TimestampSourceType.Codes.INTERNAL)})
@Schema(name = "TimestampSourceDto",
        description = "Source of the timestamps a content-signing profile embeds, as currently configured on the "
                + "profile. The required type property is the discriminator selecting the source.",
        type = "object", discriminatorProperty = "type",
        discriminatorMapping = {
                @DiscriminatorMapping(value = TimestampSourceType.Codes.INTERNAL,
                        schema = InternalTimestampSourceDto.class)},
        oneOf = {InternalTimestampSourceDto.class})
public sealed interface TimestampSourceDto extends Serializable permits InternalTimestampSourceDto {

    @Schema(description = "Kind of timestamp source, and the discriminator selecting the fields that accompany it",
            requiredMode = Schema.RequiredMode.REQUIRED, examples = {TimestampSourceType.Codes.INTERNAL})
    TimestampSourceType getType();
}
