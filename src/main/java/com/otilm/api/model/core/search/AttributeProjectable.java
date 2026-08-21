package com.otilm.api.model.core.search;

import com.otilm.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import java.util.List;
import java.util.Map;

/**
 * A listing entry that can carry the values of attribute-sourced columns a request projected.
 *
 * <p>
 * Property columns render from the entry's own fields, but a column drawn from a custom, metadata or data attribute has
 * no field to arrive in, so it lands in this map instead. The map nests by field source and then by field identifier,
 * because an identifier is unique only within its source.
 *
 * <p>
 * Every listing that supports configurable columns implements this, so the projection can populate any of them through
 * one type rather than a branch per resource.
 */
public interface AttributeProjectable {

    /**
     * Shared wording for the {@code attributeValues} schema. Declared once so seven listing DTOs cannot drift apart in
     * how they describe the same field.
     */
    String ATTRIBUTE_VALUES_DESCRIPTION = "Values of the attribute-sourced fields requested as columns, keyed by field "
            + "source and then by field identifier. Present only when the listing request asked for attribute-sourced "
            + "columns; a field the object has no value for is absent rather than empty.";

    Map<FilterFieldSource, Map<String, List<BaseAttributeContentV3<?>>>> getAttributeValues();

    void setAttributeValues(Map<FilterFieldSource, Map<String, List<BaseAttributeContentV3<?>>>> attributeValues);
}
