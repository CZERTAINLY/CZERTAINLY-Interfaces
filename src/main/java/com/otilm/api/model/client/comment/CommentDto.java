package com.otilm.api.model.client.comment;

import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;

@Data
@Schema(name = "CommentDto", description = "A comment on an object. Thread roots carry resolution state and a reply "
        + "count; replies carry neither.")
public class CommentDto {

    @Schema(description = "Comment UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID uuid;

    @Schema(description = "Host resource the commented object belongs to", requiredMode = Schema.RequiredMode.REQUIRED)
    private Resource resource;

    @Schema(description = "UUID of the commented object", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID objectUuid;

    @Schema(description = "UUID and username of the author; server-populated",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private NameAndUuidDto author;

    @Schema(description = "Creation timestamp; server-populated", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime createdAt;

    @Schema(description = "Comment body as verbatim Markdown source", requiredMode = Schema.RequiredMode.REQUIRED)
    private String body;

    @Schema(description = "UUID of the thread root when this comment is a reply; null on thread roots",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID parentUuid;

    @Schema(description = "Whether the thread is resolved; thread roots only",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean resolved;

    @Schema(description = "UUID and username of the resolving user; resolved thread roots only",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private NameAndUuidDto resolvedBy;

    @Schema(description = "Resolution timestamp; resolved thread roots only",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private OffsetDateTime resolvedAt;

    @Schema(description = "Number of replies in the thread; thread roots only. The replies themselves are paged "
            + "separately.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long replyCount;
}
