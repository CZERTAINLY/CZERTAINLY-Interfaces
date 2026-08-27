package com.otilm.api.model.common.validation;

/** OID pattern. */
public final class OidFormat {

    /** ASN.1 OID: first arc 0/1 requires second arc in 0..39; first arc 2 is unrestricted. */
    public static final String REGEX = "^([01]\\.(\\d|[1-3]\\d)|2\\.(0|[1-9]\\d*))(\\.(0|[1-9]\\d*)){0,49}$";
    public static final String MESSAGE = "Invalid OID format";

    private OidFormat() {
    }
}
