package com.czertainly.api.model.common.collection;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContent;
import com.czertainly.api.model.common.attribute.v2.content.StringAttributeContent;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RsaSignatureScheme {
    PKCS1V15(1, "PKCS#1 v1.5", "Deterministic RSA signature scheme"),
    PSS(2, "PSS", "Probabilistic RSA signature scheme");

    private static final RsaSignatureScheme[] VALUES;

    static {
        VALUES = values();
    }

    private final int id;
    private final String name;
    private final String description;

    RsaSignatureScheme(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static RsaSignatureScheme valueOf(int id) {
        RsaSignatureScheme item = resolve(id);
        if (item == null) {
            throw new IllegalArgumentException("No matching constant for [" + id + "]");
        }
        return item;
    }

    @Nullable
    public static RsaSignatureScheme resolve(int id) {
        // Use cached VALUES instead of values() to prevent array allocation.
        for (RsaSignatureScheme item : VALUES) {
            if (item.id == id) {
                return item;
            }
        }
        return null;
    }

    @JsonCreator
    public static RsaSignatureScheme findByCode(String code) {
        return Arrays.stream(RsaSignatureScheme.values())
                .filter(k -> k.name.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown code {}", code)));
    }

    public static List<BaseAttributeContent> asStringAttributeContentList() {
        return List.of(values()).stream()
                .map(item -> new StringAttributeContent(item.name(), item.getName()))
                .collect(Collectors.toList());
    }

    public static List<BaseAttributeContent> asStringAttributeContentWithIdReferenceList() {
        return List.of(values()).stream()
                .map(item -> new StringAttributeContent(Integer.toString(item.getId()), item.getName()))
                .collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name();
    }
}
