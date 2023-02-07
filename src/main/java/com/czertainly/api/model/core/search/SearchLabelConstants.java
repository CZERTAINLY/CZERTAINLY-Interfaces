package com.czertainly.api.model.core.search;

import com.czertainly.api.model.core.certificate.CertificateStatus;
import com.czertainly.api.model.core.compliance.ComplianceStatus;

import java.util.List;

public final class SearchLabelConstants {
    private static final String COMMON_NAME_LABEL = "Common Name";
    private static final String SERIAL_NUMBER_LABEL = "Serial Number";
    private static final String RA_PROFILE_NAME_LABEL = "RA Profile";
    private static final String ENTITY_NAME_LABEL = "Entity";
    private static final String STATUS_LABEL = "Status";
    private static final String GROUP_NAME_LABEL = "Group";
    private static final String OWNER_LABEL = "Owner";
    private static final String ISSUER_COMMON_NAME_LABEL = "Issuer Common Name";
    private static final String SIGNATURE_ALGORITHM_LABEL = "Signature Algorithm";
    private static final String FINGERPRINT_LABEL = "Fingerprint";
    private static final String EXPIRES_LABEL = "Expires At";
    private static final String NOT_BEFORE_LABEL = "Valid From";
    private static final String PUBLIC_KEY_ALGORITHM_LABEL = "Public Key Algorithm";
    private static final String KEY_SIZE_LABEL = "Key Size";
    private static final String KEY_USAGE_LABEL = "Key Usage";
    private static final String BASIC_CONSTRAINTS_LABEL = "Basic Constraints";
    private static final String META_DATA_LABEL = "Meta Data";
    private static final String SUBJECT_ALTERNATIVE_NAME_LABEL = "Subject Alternative Name";
    private static final String SUBJECT_DN_LABEL = "Subject DN";
    private static final String ISSUER_DN_LABEL = "Issuer DN";
    private static final String ISSUER_SERIAL_NUMBER_LABEL = "Issuer Serial Number";
    private static final String OCSP_VALIDATION_LABEL = "OCSP Validation";
    private static final String CRL_VALIDATION_LABEL = "CRL Validation";
    private static final String SIGNATURE_VALIDATION_LABEL = "Signature Validation";
    private static final String COMPLIANCE_STATUS = "Compliance Status";

    private static final String NAME_LABEL = "Name";
    private static final String KEY_TYPE_LABEL = "Key type";
    private static final String KEY_FORMAT_LABEL = "Key format";
    private static final String KEY_STATE_LABEL = "State";
    private static final String KEY_CRYPTOGRAPHIC_ALGORITHM_LABEL = "Cryptographic algorithm";
    private static final String KEY_TOKEN_PROFILE_LABEL = "Token profile";
    private static final String KEY_TOKEN_INSTANCE_LABEL = "Token instance";

    public static final SearchFieldDataDto COMMON_NAME_FILTER = getSearchField(SearchableFields.COMMON_NAME,
            COMMON_NAME_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.CONTAINS,
                    SearchCondition.NOT_CONTAINS,
                    SearchCondition.EQUALS,
                    SearchCondition.NOT_EQUALS,
                    SearchCondition.EMPTY,
                    SearchCondition.NOT_EMPTY,
                    SearchCondition.STARTS_WITH,
                    SearchCondition.ENDS_WITH
            )
    );

    public static final SearchFieldDataDto SERIAL_NUMBER_FILTER = getSearchField(SearchableFields.SERIAL_NUMBER,
            SERIAL_NUMBER_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.CONTAINS,
                    SearchCondition.NOT_CONTAINS,
                    SearchCondition.EQUALS,
                    SearchCondition.NOT_EQUALS,
                    SearchCondition.EMPTY,
                    SearchCondition.NOT_EMPTY,
                    SearchCondition.STARTS_WITH,
                    SearchCondition.ENDS_WITH
            )
    );
    public static final SearchFieldDataDto ISSUER_SERIAL_NUMBER_FILTER = getSearchField(SearchableFields.ISSUER_SERIAL_NUMBER,
            ISSUER_SERIAL_NUMBER_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.CONTAINS,
                    SearchCondition.NOT_CONTAINS,
                    SearchCondition.EQUALS,
                    SearchCondition.NOT_EQUALS,
                    SearchCondition.EMPTY,
                    SearchCondition.NOT_EMPTY,
                    SearchCondition.STARTS_WITH,
                    SearchCondition.ENDS_WITH
            )
    );
    public static final SearchFieldDataDto RA_PROFILE_NAME_FILTER = getSearchField(SearchableFields.RA_PROFILE_NAME,
            RA_PROFILE_NAME_LABEL,
            true,
            null,
            SearchableFieldType.LIST,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS, SearchCondition.EMPTY, SearchCondition.NOT_EMPTY)
    );
    public static final SearchFieldDataDto ENTITY_NAME_FILTER = getSearchField(SearchableFields.ENTITY_NAME,
            ENTITY_NAME_LABEL,
            true,
            null,
            SearchableFieldType.LIST,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS, SearchCondition.EMPTY, SearchCondition.NOT_EMPTY)
    );
    public static final SearchFieldDataDto OWNER_FILTER = getSearchField(SearchableFields.OWNER,
            OWNER_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.CONTAINS,
                    SearchCondition.NOT_CONTAINS,
                    SearchCondition.EQUALS,
                    SearchCondition.NOT_EQUALS,
                    SearchCondition.EMPTY,
                    SearchCondition.NOT_EMPTY,
                    SearchCondition.STARTS_WITH,
                    SearchCondition.ENDS_WITH)
    );
    public static final SearchFieldDataDto STATUS_FILTER = getSearchField(SearchableFields.STATUS,
            STATUS_LABEL,
            true,
            List.of(CertificateStatus.REVOKED.toString(),
                    CertificateStatus.EXPIRED.toString(),
                    CertificateStatus.EXPIRING.toString(),
                    CertificateStatus.VALID.toString(),
                    CertificateStatus.INVALID.toString(),
                    CertificateStatus.NEW.toString(),
                    CertificateStatus.UNKNOWN.toString()),
            SearchableFieldType.LIST,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS)
    );

    public static final SearchFieldDataDto COMPLIANCE_STATUS_FILTER = getSearchField(SearchableFields.COMPLIANCE_STATUS,
            COMPLIANCE_STATUS,
            true,
            List.of(ComplianceStatus.OK.toString(), ComplianceStatus.NOK.toString(), ComplianceStatus.NA.toString()),
            SearchableFieldType.LIST,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS)
    );

    public static final SearchFieldDataDto GROUP_NAME_FILTER = getSearchField(SearchableFields.GROUP_NAME,
            GROUP_NAME_LABEL,
            true,
            null,
            SearchableFieldType.LIST,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS, SearchCondition.EMPTY, SearchCondition.NOT_EMPTY)
    );
    public static final SearchFieldDataDto ISSUER_COMMON_NAME_FILTER = getSearchField(SearchableFields.ISSUER_COMMON_NAME,
            ISSUER_COMMON_NAME_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.CONTAINS,
                    SearchCondition.NOT_CONTAINS,
                    SearchCondition.EQUALS,
                    SearchCondition.NOT_EQUALS,
                    SearchCondition.EMPTY,
                    SearchCondition.NOT_EMPTY,
                    SearchCondition.STARTS_WITH,
                    SearchCondition.ENDS_WITH
            )
    );
    public static final SearchFieldDataDto FINGERPRINT_FILTER = getSearchField(SearchableFields.FINGERPRINT,
            FINGERPRINT_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.CONTAINS,
                    SearchCondition.NOT_CONTAINS,
                    SearchCondition.EQUALS,
                    SearchCondition.NOT_EQUALS,
                    SearchCondition.EMPTY,
                    SearchCondition.NOT_EMPTY,
                    SearchCondition.STARTS_WITH,
                    SearchCondition.ENDS_WITH
            )
    );
    public static final SearchFieldDataDto SIGNATURE_ALGORITHM_FILTER = getSearchField(SearchableFields.SIGNATURE_ALGORITHM,
            SIGNATURE_ALGORITHM_LABEL,
            true,
            null,
            SearchableFieldType.LIST,
            List.of(SearchCondition.EQUALS,
                    SearchCondition.NOT_EQUALS
            )
    );
    public static final SearchFieldDataDto NOT_AFTER_FILTER = getSearchField(SearchableFields.NOT_AFTER,
            EXPIRES_LABEL,
            false,
            null,
            SearchableFieldType.DATE,
            List.of(SearchCondition.GREATER,
                    SearchCondition.LESSER
            )
    );
    public static final SearchFieldDataDto NOT_BEFORE_FILTER = getSearchField(SearchableFields.NOT_BEFORE,
            NOT_BEFORE_LABEL,
            false,
            null,
            SearchableFieldType.DATE,
            List.of(SearchCondition.GREATER,
                    SearchCondition.LESSER
            )
    );
    public static final SearchFieldDataDto SUBJECTDN_FILTER = getSearchField(SearchableFields.SUBJECTDN,
            SUBJECT_DN_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.CONTAINS,
                    SearchCondition.NOT_CONTAINS,
                    SearchCondition.EQUALS,
                    SearchCondition.NOT_EQUALS,
                    SearchCondition.EMPTY,
                    SearchCondition.NOT_EMPTY,
                    SearchCondition.STARTS_WITH,
                    SearchCondition.ENDS_WITH
            )
    );
    public static final SearchFieldDataDto ISSUERDN_FILTER = getSearchField(SearchableFields.ISSUERDN,
            ISSUER_DN_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.CONTAINS,
                    SearchCondition.NOT_CONTAINS,
                    SearchCondition.EQUALS,
                    SearchCondition.NOT_EQUALS,
                    SearchCondition.EMPTY,
                    SearchCondition.NOT_EMPTY,
                    SearchCondition.STARTS_WITH,
                    SearchCondition.ENDS_WITH
            )
    );
    public static final SearchFieldDataDto META_FILTER = getSearchField(SearchableFields.META,
            META_DATA_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.CONTAINS,
                    SearchCondition.NOT_CONTAINS,
                    SearchCondition.EMPTY,
                    SearchCondition.NOT_EMPTY
            )
    );
    public static final SearchFieldDataDto SUBJECT_ALTERNATIVE_NAMES_FILTER = getSearchField(SearchableFields.SUBJECT_ALTERNATIVE_NAMES,
            SUBJECT_ALTERNATIVE_NAME_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.CONTAINS,
                    SearchCondition.NOT_CONTAINS,
                    SearchCondition.EMPTY,
                    SearchCondition.NOT_EMPTY
            )
    );
    public static final SearchFieldDataDto PUBLIC_KEY_ALGORITHM_FILTER = getSearchField(SearchableFields.PUBLIC_KEY_ALGORITHM,
            PUBLIC_KEY_ALGORITHM_LABEL,
            true,
            null,
            SearchableFieldType.LIST,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS)
    );
    public static final SearchFieldDataDto KEY_SIZE_FILTER = getSearchField(SearchableFields.KEY_SIZE,
            KEY_SIZE_LABEL,
            true,
            null,
            SearchableFieldType.LIST,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS)
    );
    public static final SearchFieldDataDto BASIC_CONSTRAINTS_FILTER = getSearchField(SearchableFields.BASIC_CONSTRAINTS,
            BASIC_CONSTRAINTS_LABEL,
            true,
            null,
            SearchableFieldType.LIST,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS)
    );
    public static final SearchFieldDataDto KEY_USAGE_FILTER = getSearchField(SearchableFields.KEY_USAGE,
            KEY_USAGE_LABEL,
            false,
            null,
            SearchableFieldType.LIST,
            List.of(SearchCondition.CONTAINS, SearchCondition.NOT_CONTAINS)
    );

    public static final SearchFieldDataDto OCSP_VALIDATION_FILTER = getSearchField(SearchableFields.OCSP_VALIDATION,
            OCSP_VALIDATION_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.SUCCESS, SearchCondition.FAILED, SearchCondition.UNKNOWN, SearchCondition.EMPTY)
    );

    public static final SearchFieldDataDto CRL_VALIDATION_FILTER = getSearchField(SearchableFields.CRL_VALIDATION,
            CRL_VALIDATION_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.SUCCESS, SearchCondition.FAILED, SearchCondition.UNKNOWN, SearchCondition.EMPTY)
    );

    public static final SearchFieldDataDto SIGNATURE_VALIDATION_FILTER = getSearchField(SearchableFields.SIGNATURE_VALIDATION,
            SIGNATURE_VALIDATION_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.SUCCESS, SearchCondition.FAILED, SearchCondition.UNKNOWN)
    );

    private static SearchFieldDataDto getSearchField(SearchableFields field, String label, Boolean multiValue, List<Object> values, SearchableFieldType fieldType, List<SearchCondition> conditions) {
        SearchFieldDataDto dto = new SearchFieldDataDto();
        dto.setField(field);
        dto.setLabel(label);
        dto.setMultiValue(multiValue);
        dto.setValue(values);
        dto.setType(fieldType);
        dto.setConditions(conditions);
        return dto;
    }

//    --------- CRYPTOGRAPHIC KEY SEARCH ---------

    public static final SearchFieldDataDto CK_NAME_FILTER = getSearchField(SearchableFields.CK_NAME,
            NAME_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS, SearchCondition.CONTAINS, SearchCondition.NOT_CONTAINS)
    );

    public static final SearchFieldDataDto CK_GROUP_FILTER = getSearchField(SearchableFields.CK_GROUP,
            GROUP_NAME_LABEL,
            true,
            null,
            SearchableFieldType.LIST, // OBJECT GROUP
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS, SearchCondition.EMPTY, SearchCondition.NOT_EMPTY)
    );

    public static final SearchFieldDataDto CK_OWNER_FILTER = getSearchField(SearchableFields.CK_OWNER,
            OWNER_LABEL,
            false,
            null,
            SearchableFieldType.STRING,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS, SearchCondition.CONTAINS, SearchCondition.NOT_CONTAINS, SearchCondition.EMPTY, SearchCondition.NOT_EMPTY)
    );

    public static final SearchFieldDataDto CK_KEY_USAGE_FILTER = getSearchField(SearchableFields.CKI_USAGE,
            KEY_USAGE_LABEL,
            true,
            null,
            SearchableFieldType.LIST,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS)
    );

    public static final SearchFieldDataDto CK_KEY_LENGTH = getSearchField(SearchableFields.CKI_LENGTH,
            KEY_SIZE_LABEL,
            false,
            null,
            SearchableFieldType.NUMBER,
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS, SearchCondition.GREATER, SearchCondition.LESSER)
    );

    public static final SearchFieldDataDto CK_TYPE_FILTER = getSearchField(SearchableFields.CKI_TYPE,
            KEY_TYPE_LABEL,
            true,
            null,
            SearchableFieldType.LIST, // OBJECT GROUP
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS, SearchCondition.EMPTY, SearchCondition.NOT_EMPTY)
    );

    public static final SearchFieldDataDto CK_FORMAT_FILTER = getSearchField(SearchableFields.CKI_FORMAT,
            KEY_FORMAT_LABEL,
            true,
            null,
            SearchableFieldType.LIST, // OBJECT GROUP
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS, SearchCondition.EMPTY, SearchCondition.NOT_EMPTY)
    );

    public static final SearchFieldDataDto CK_STATE_FILTER = getSearchField(SearchableFields.CKI_STATE,
            KEY_STATE_LABEL,
            true,
            null,
            SearchableFieldType.LIST, // OBJECT GROUP
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS)
    );

    public static final SearchFieldDataDto CK_ALGORITHM_FILTER = getSearchField(SearchableFields.CKI_CRYPTOGRAPHIC_ALGORITHM,
            KEY_CRYPTOGRAPHIC_ALGORITHM_LABEL,
            true,
            null,
            SearchableFieldType.LIST, // OBJECT GROUP
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS, SearchCondition.EMPTY, SearchCondition.NOT_EMPTY)
    );

    public static final SearchFieldDataDto CK_TOKEN_PROFILE_FILTER = getSearchField(SearchableFields.CK_TOKEN_PROFILE,
            KEY_TOKEN_PROFILE_LABEL,
            true,
            null,
            SearchableFieldType.LIST, // OBJECT GROUP
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS, SearchCondition.EMPTY, SearchCondition.NOT_EMPTY)
    );

    public static final SearchFieldDataDto CK_TOKEN_INSTANCE_FILTER = getSearchField(SearchableFields.CK_TOKEN_INSTANCE,
            KEY_TOKEN_INSTANCE_LABEL,
            true,
            null,
            SearchableFieldType.LIST, // OBJECT GROUP
            List.of(SearchCondition.EQUALS, SearchCondition.NOT_EQUALS)
    );


}
