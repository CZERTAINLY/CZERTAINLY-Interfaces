package com.czertainly.api.model.core.audit;

public enum EventTypeEnum {

    SENSITIVE_DATA_ACCESS,
    ROOT_ACTION,
    INVALID_ACCESS_ATTEMPT,

    // Audit log events
    AUDIT_LOG_ACCESS,
    AUDIT_LOG_INITIALIZATION,
    AUDIT_LOG_STOPPING,
    AUDIT_LOG_PAUSING,

    // System level object events
    SYSTEM_LEVEL_OBJECT_CREATION,
    SYSTEM_LEVEL_OBJECT_DELETION,
    ;
}
