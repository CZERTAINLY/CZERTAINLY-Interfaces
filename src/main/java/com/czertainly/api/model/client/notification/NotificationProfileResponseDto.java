package com.czertainly.api.model.client.notification;

import com.czertainly.api.model.core.scheduler.PaginationResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationProfileResponseDto extends PaginationResponseDto {

    private List<NotificationProfileDto> notificationProfiles;

}
