package com.otilm.api.model.client.notification;

import com.otilm.api.model.core.scheduler.PaginationResponseDto;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationProfileResponseDto extends PaginationResponseDto {

    private List<NotificationProfileDto> notificationProfiles;

}
