package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.common.attribute.v2.GroupAttribute;
import com.czertainly.api.model.common.attribute.v2.InfoAttribute;
import com.czertainly.api.model.core.connector.FunctionGroupCode;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributesMapDto extends HashMap<FunctionGroupCode, AttributesMapByKindDto> {
    @Hidden
    public boolean isEmpty() {
        return super.isEmpty();
    }
}

class AttributesMapByKindDto extends HashMap<String, AttributesListDto> {
    @Hidden
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
