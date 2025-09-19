package com.czertainly.api.model.common.enums;

import java.io.Serializable;

public interface IPlatformEnum extends Serializable {

    String name();

    String getCode();

    String getLabel();

    String getDescription();

}
