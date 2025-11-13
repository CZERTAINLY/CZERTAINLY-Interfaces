package com.czertainly.core.interfaces;

import com.czertainly.api.model.common.attribute.v2.BaseAttributeV2;

import java.util.List;

public interface Connector {

    List<BaseAttributeV2> getAttributes();
}
