package com.czertainly.api.model.connector.notification;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttributeV2;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotificationProviderInstanceDto extends NameAndUuidDto {

    @Schema(description = "List of Notification instance Attributes",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<BaseAttributeV2> attributes;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("attributes", attributes)
                .toString();
    }
}
