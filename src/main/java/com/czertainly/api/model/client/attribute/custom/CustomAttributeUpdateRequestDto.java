package com.czertainly.api.model.client.attribute.custom;

import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContent;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomAttributeUpdateRequestDto {

    /**
     * Description of the Attribute
     */
    @Schema(description = "Attribute description", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    /**
     * Friendly name of the Attribute
     **/
    @Schema(
            description = "Friendly name of the the Attribute",
            example = "Attribute Name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String label;

    /**
     * Boolean determining if the Attribute is visible and can be displayed, otherwise it should be hidden to the user.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is visible and can be displayed, otherwise it should be hidden to the user.",
            defaultValue = "true"
    )
    private boolean visible = true;


    /**
     * Group of the Attribute, used for the logical grouping of the Attribute
     **/
    @Schema(
            description = "Group of the Attribute, used for the logical grouping of the Attribute",
            example = "requiredAttributes"
    )
    private String group;

    /**
     * Boolean determining if the Attribute is required. If true, the Attribute must be provided.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is required. If true, the Attribute must be provided.",
            defaultValue = "false"
    )
    private boolean required = false;

    /**
     * Boolean determining if the Attribute is read only. If true, the Attribute content cannot be changed.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is read only. If true, the Attribute content cannot be changed.",
            defaultValue = "false"
    )
    private boolean readOnly = false;


    /**
     * Boolean determining if the Attribute contains list of values in the content
     **/
    @Schema(
            description = "Boolean determining if the Attribute contains list of values in the content",
            defaultValue = "false"
    )
    private boolean list = false;

    /**
     * Boolean determining if the Attribute can have multiple values
     **/
    @Schema(
            description = "Boolean determining if the Attribute can have multiple values",
            defaultValue = "false"
    )
    private boolean multiSelect = false;

    /**
     * Attribute Content
     */
    @Schema(
            description = "Predefined content for the attribute if needed. The content of the Attribute must satisfy the type"
    )
    private List<BaseAttributeContent> content;

    /**
     * List of resources to be associated to the attribute
     */
    @Schema(
            description = "List of resource to be associated with the custom attribute"
    )
    private List<Resource> resources;
}
