package com.otilm.api.model.client.comment;

import com.otilm.api.model.core.scheduler.PaginationResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentResponseDto extends PaginationResponseDto {

    @Schema(description = "Thread roots on the requested page, each carrying its replies",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CommentDto> comments;
}
