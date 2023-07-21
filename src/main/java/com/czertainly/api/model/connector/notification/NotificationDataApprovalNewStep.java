package com.czertainly.api.model.connector.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDataApprovalNewStep {

    private String resource;

    private String resourceUuid;


}
