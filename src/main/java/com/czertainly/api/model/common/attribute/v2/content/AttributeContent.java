package com.czertainly.api.model.common.attribute.v2.content;

import java.io.Serializable;

public abstract class AttributeContent implements Serializable {

    public abstract <T extends Object> T getData();
}
