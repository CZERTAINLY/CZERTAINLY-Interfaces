package com.otilm.api.model.client.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Data;

@Data
@Schema(name = "CommentCreateRequestDto", description = "Request to post a comment or a reply on an object. "
        + "Author identity and creation timestamp are populated by the server from the authenticated principal.")
public class CommentCreateRequestDto {

    @NotBlank
    @Size(max = 65536)
    @Schema(description = "Comment body as verbatim Markdown source", requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 65536)
    private String body;

    @Schema(description = "UUID of the thread root this comment replies to. Threads are one level deep: "
            + "the referenced comment must be a thread root on the same object.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID parentUuid;
}
