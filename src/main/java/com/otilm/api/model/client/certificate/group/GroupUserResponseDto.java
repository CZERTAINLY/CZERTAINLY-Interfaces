package com.otilm.api.model.client.certificate.group;

import com.otilm.api.model.core.auth.UserDto;
import com.otilm.api.model.core.scheduler.PaginationResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GroupUserResponseDto extends PaginationResponseDto {

    @Schema(description = "Users assigned to the Group", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<UserDto> users;

}
