package com.otilm.api.model.client.signing.profile.workflow.timestamp;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * Where a content-signing profile gets its timestamps, as supplied on create and update. The reference is discriminated
 * so an external RFC 3161 source can be added without reshaping the contract.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({@Type(value = InternalTimestampSourceRequestDto.class, name = TimestampSourceType.Codes.INTERNAL)})
@Schema(name = "TimestampSourceRequestDto",
        description = "Source of the timestamps a content-signing profile embeds, as supplied on create and update. "
                + "The required type property is the discriminator selecting the source.",
        type = "object", discriminatorProperty = "type",
        discriminatorMapping = {
                @DiscriminatorMapping(value = TimestampSourceType.Codes.INTERNAL,
                        schema = InternalTimestampSourceRequestDto.class)},
        oneOf = {InternalTimestampSourceRequestDto.class})
public sealed interface TimestampSourceRequestDto extends Serializable permits InternalTimestampSourceRequestDto {

    @Schema(description = "Kind of timestamp source, and the discriminator selecting the fields that accompany it",
            requiredMode = Schema.RequiredMode.REQUIRED, examples = {TimestampSourceType.Codes.INTERNAL})
    TimestampSourceType getType();
}
