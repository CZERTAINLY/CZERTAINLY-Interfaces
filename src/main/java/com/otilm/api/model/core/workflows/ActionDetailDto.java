package com.otilm.api.model.core.workflows;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActionDetailDto extends ActionDto {

    @Schema(description = "List of executions", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ExecutionDto> executions = new ArrayList<>();

}
