package com.czertainly.api.model.core.logging;

import java.io.Serializable;

public interface Loggable {
    Serializable toLogData();
}
