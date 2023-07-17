
package com.czertainly.api.model.connector.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;

@Data
public class UserNotificationRecipientDto extends NotificationRecipientDto {
    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Group name of the user")
    private String groupName;
}
