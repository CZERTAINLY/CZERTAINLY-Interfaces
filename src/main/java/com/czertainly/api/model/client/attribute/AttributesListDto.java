package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.common.attribute.v2.GroupAttribute;
import com.czertainly.api.model.common.attribute.v2.InfoAttribute;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(
        description = "List of attributes",
        required = true,
        oneOf = {
                DataAttribute.class,
                InfoAttribute.class,
                GroupAttribute.class
        }
)
public class AttributesListDto extends ArrayList<BaseAttribute> {
        @Hidden
        public boolean isEmpty() {
                return super.isEmpty();
        }
}