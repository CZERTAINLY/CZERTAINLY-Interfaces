package com.czertainly.core.interfaces;

import com.czertainly.api.model.common.attribute.BaseAttribute;

import java.util.List;

public interface Connector {

    List<BaseAttribute> getAttributes();
}
