package com.otilm.api.model.common.events.data;

import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentEventData implements EventData {

    @Schema(description = "Comment UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID commentUuid;

    @Schema(description = "UUID of the thread root when the comment is a reply; null on thread roots",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID parentUuid;

    @Schema(description = "Host resource the commented object belongs to", requiredMode = Schema.RequiredMode.REQUIRED)
    private Resource resource;

    @Schema(description = "UUID of the commented object", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID objectUuid;

    @Schema(description = "Name of the commented object", requiredMode = Schema.RequiredMode.REQUIRED)
    private String objectName;

    @Schema(description = "UUID of the comment author", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID authorUuid;

    @Schema(description = "Username of the comment author", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorUsername;

    @Schema(description = "Comment creation timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime createdAt;

    @Schema(description = "Verbatim Markdown source of the comment body; delivered as plain text, never rendered",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String body;

    @Schema(description = "Whether the thread is resolved after the change; populated by the comment_resolved event "
            + "to distinguish resolving from reopening", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean resolved;

    @Schema(description = "UUID of the user who changed the resolution state; populated by the comment_resolved "
            + "event only", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID resolvedByUuid;

    @Schema(description = "Username of the user who changed the resolution state; populated by the comment_resolved "
            + "event only", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String resolvedByUsername;

    @Schema(description = "Timestamp of the resolution state change; populated by the comment_resolved event only",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private OffsetDateTime resolvedAt;
}
