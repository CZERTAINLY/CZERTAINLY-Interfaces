package com.otilm.api.model.connector.signatures.contentsigning.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@code signatureAlgorithm} is declared twice — once on the {@code computeDtbs} base and once on the embed request —
 * because no shared ancestor of the two is signing-specific. The two declarations must stay the same type under the
 * same constraints: a relaxed {@code @NotNull} or a narrowed type on one half would compile and surface only as drift
 * at runtime. Modelled on {@code TimestampingDtoFieldParityTest}, which guards the equivalent pair.
 */
class ContentSigningDtoFieldParityTest {

    private static final Set<String> PAIRED_FIELDS = Set.of("signatureAlgorithm");

    @Test
    void pairedFieldsHaveIdenticalFieldTypes() {
        for (String fieldName : PAIRED_FIELDS) {
            assertEquals(fieldType(ComputeDtbsRequestDto.class, fieldName),
                    fieldType(EmbedSignatureValueRequestDto.class, fieldName),
                    "Field type mismatch on paired field '" + fieldName + "'");
        }
    }

    @Test
    void pairedFieldsHaveIdenticalAnnotationTypes() {
        for (String fieldName : PAIRED_FIELDS) {
            assertEquals(annotationTypes(ComputeDtbsRequestDto.class, fieldName),
                    annotationTypes(EmbedSignatureValueRequestDto.class, fieldName),
                    "Annotation type mismatch on paired field '" + fieldName + "'");
        }
    }

    @Test
    void bothRequestsDeclareEveryPairedField() {
        assertEquals(PAIRED_FIELDS, intersection(declaredFieldNames(ComputeDtbsRequestDto.class), PAIRED_FIELDS),
                "ComputeDtbsRequestDto is missing paired fields");
        assertEquals(PAIRED_FIELDS,
                intersection(declaredFieldNames(EmbedSignatureValueRequestDto.class), PAIRED_FIELDS),
                "EmbedSignatureValueRequestDto is missing paired fields");
    }

    private static Class<?> fieldType(Class<?> clazz, String fieldName) {
        return declaredField(clazz, fieldName).getType();
    }

    private static Set<Class<? extends Annotation>> annotationTypes(Class<?> clazz, String fieldName) {
        return Arrays
                .stream(declaredField(clazz, fieldName).getAnnotations())
                .map(Annotation::annotationType)
                .collect(Collectors.toSet());
    }

    private static Field declaredField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new AssertionError("Field '" + fieldName + "' not found in " + clazz.getSimpleName(), e);
        }
    }

    private static Set<String> declaredFieldNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toSet());
    }

    private static Set<String> intersection(Set<String> left, Set<String> right) {
        return left.stream().filter(right::contains).collect(Collectors.toSet());
    }
}
