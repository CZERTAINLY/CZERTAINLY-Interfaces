package com.otilm.api.model.core.notification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * Category of event object data a notification profile can include in external notifications. Categories are opt-in per
 * profile and resource-neutral: a category that the event's subject object does not support simply yields no data.
 *
 * <p>
 * Deserialization is strict, matching every platform enum: an unrecognized code fails with a validation error rather
 * than being skipped. A client on an older version of this artifact must upgrade before it can read profiles that
 * enable categories added later.
 */
@Schema(enumAsRef = true)
public enum NotificationDataCategory implements IPlatformEnum {

    CUSTOM_ATTRIBUTES("customAttributes", "Custom attributes", "Include the event object's custom attribute values"),
    METADATA("metadata", "Metadata", "Include connector-provided metadata, grouped by connector and source object"),
    ASSOCIATIONS("associations", "Associations", "Include owner, groups, and RA profile references"),
    OBJECT_CONTENT("objectContent", "Object content",
            "Include the object's content when its type provides one, e.g. certificates as Base64 DER");

    private static final NotificationDataCategory[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    NotificationDataCategory(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @JsonCreator
    public static NotificationDataCategory findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new ValidationException(
                        ValidationError.create("Unknown notification data category {}", code)));
    }
}
