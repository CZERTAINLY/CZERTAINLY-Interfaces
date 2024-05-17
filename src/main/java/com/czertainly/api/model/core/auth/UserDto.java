package com.czertainly.api.model.core.auth;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

    @Schema(description = "UUID of the User", requiredMode = Schema.RequiredMode.REQUIRED, example = "5b5f0784-2519-11ed-861d-0242ac120002")
    private String uuid;

    @Schema(description = "Username of the user", requiredMode = Schema.RequiredMode.REQUIRED, example = "user1")
    private String username;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Email of the user")
    private String email;

    @Schema(description = "Description of the user")
    private String description;

    @Schema(description = "Groups of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<NameAndUuidDto> groups = new ArrayList<>();

    @Schema(description = "Status of the user. True = Enabled, False = Disabled", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean enabled;

    @Schema(description = "Is System user. True = Yes, False = No", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean systemUser;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("username", username)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("email", email)
                .append("description", description)
                .append("groups", groups)
                .append("enabled", enabled)
                .append("systemUser", systemUser)
                .toString();
    }
}
