package com.otilm.api.model.common.signature.parameters;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.common.signature.SignatureParameterGroup;
import jakarta.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Guards the {@link RequestParameterGroup} annotations, which are the single authority on group membership: a parameter
 * field added without one would reach production unenforced, because Core's permission check reads these annotations
 * and nothing else.
 */
class SignatureParametersGroupContractTest {

    private static final String PARAMETERS_PACKAGE = "com.otilm.api.model.common.signature.parameters";

    private static final String DISCRIMINATOR = "family";

    @Test
    void theUnionRegistersAtLeastOneSubtype() {
        assertFalse(registeredSubtypes().isEmpty());
    }

    @Test
    void everyLeafParameterFieldDeclaresAGroupThatAppliesToItsFamily() throws Exception {
        List<String> problems = new ArrayList<>();
        for (Class<? extends SignatureParametersDto> subtype : registeredSubtypes()) {
            SignatureFamily family = subtype.getDeclaredConstructor().newInstance().getFamily();
            classify(subtype, family, subtype.getSimpleName(), problems, EnumSet.noneOf(SignatureParameterGroup.class));
        }
        assertTrue(problems.isEmpty(), String.join("\n", problems));
    }

    @Test
    void everyGroupThatAppliesToAFamilyIsPopulatedByThatFamilysParameters() throws Exception {
        List<String> problems = new ArrayList<>();
        for (Class<? extends SignatureParametersDto> subtype : registeredSubtypes()) {
            SignatureFamily family = subtype.getDeclaredConstructor().newInstance().getFamily();
            Set<SignatureParameterGroup> used = EnumSet.noneOf(SignatureParameterGroup.class);
            classify(subtype, family, subtype.getSimpleName(), problems, used);
            for (SignatureParameterGroup group : SignatureParameterGroup.values()) {
                if (group.appliesTo(family) && !used.contains(group)) {
                    problems
                            .add(group + " applies to " + family + " but no field of " + subtype.getSimpleName()
                                    + " declares it");
                }
            }
        }
        assertTrue(problems.isEmpty(), String.join("\n", problems));
    }

    @Test
    void theDiscriminatorItselfCarriesNoGroup() throws Exception {
        Field family = SignatureParametersDto.class.getDeclaredField(DISCRIMINATOR);
        assertNull(family.getAnnotation(RequestParameterGroup.class));
    }

    /** Proves the sweep above actually fails on an unclassified field, rather than passing over it silently. */
    @Test
    void theSweepCatchesAFieldThatForgotItsGroup() {
        List<String> problems = new ArrayList<>();
        classify(UnclassifiedFixture.class, SignatureFamily.PADES, "fixture", problems,
                EnumSet.noneOf(SignatureParameterGroup.class));
        assertEquals(1, problems.size(), String.join("\n", problems));
        assertTrue(problems.get(0).contains("forgottenParameter"), problems.get(0));
    }

    /** A collection classifies as java.util, so without element resolution its whole element type goes unvisited. */
    @Test
    void theSweepSeesThroughACollectionOfParameterObjects() {
        List<String> problems = new ArrayList<>();
        classify(CollectionOfObjectsFixture.class, SignatureFamily.PADES, "fixture", problems,
                EnumSet.noneOf(SignatureParameterGroup.class));
        assertEquals(1, problems.size(), String.join("\n", problems));
        assertTrue(problems.get(0).contains("forgottenNestedParameter"), problems.get(0));
    }

    /** An array reports its element's package, so without component resolution it recurses into no fields at all. */
    @Test
    void theSweepSeesThroughAnArrayOfParameterObjects() {
        List<String> problems = new ArrayList<>();
        classify(ArrayOfObjectsFixture.class, SignatureFamily.PADES, "fixture", problems,
                EnumSet.noneOf(SignatureParameterGroup.class));
        assertEquals(1, problems.size(), String.join("\n", problems));
        assertTrue(problems.get(0).contains("forgottenNestedParameter"), problems.get(0));
    }

    /** The over-correction guard: a collection of values is still one parameter, so the group stays on the field. */
    @Test
    void theSweepStillTreatsACollectionOfValuesAsALeaf() {
        List<String> problems = new ArrayList<>();
        classify(CollectionOfValuesFixture.class, SignatureFamily.PADES, "fixture", problems,
                EnumSet.noneOf(SignatureParameterGroup.class));
        assertEquals(1, problems.size(), String.join("\n", problems));
        assertEquals("fixture.values is a parameter field and must declare a @RequestParameterGroup", problems.get(0));
    }

    private static void classify(Class<?> type, SignatureFamily family, String path, List<String> problems,
            Set<SignatureParameterGroup> used) {
        classify(type, family, path, problems, used, new HashSet<>());
    }

    private static void classify(Class<?> type, SignatureFamily family, String path, List<String> problems,
            Set<SignatureParameterGroup> used, Set<Class<?>> classified) {
        if (!classified.add(type)) {
            return;
        }
        for (Field field : instanceFields(type)) {
            String location = path + "." + field.getName();
            RequestParameterGroup annotation = field.getAnnotation(RequestParameterGroup.class);
            if (DISCRIMINATOR.equals(field.getName())) {
                if (annotation != null) {
                    problems.add(location + " is the discriminator and must carry no group");
                }
                continue;
            }
            Class<?> element = elementType(field);
            if (isContainer(element)) {
                if (annotation != null) {
                    problems.add(location + " is a container and must carry no group; annotate its leaf fields");
                }
                if (field.getAnnotation(Valid.class) == null) {
                    problems.add(location + " is a container and must carry @Valid, or its constraints go unchecked");
                }
                classify(element, family, location, problems, used, classified);
                continue;
            }
            if (annotation == null) {
                problems.add(location + " is a parameter field and must declare a @RequestParameterGroup");
                continue;
            }
            if (!annotation.value().appliesTo(family)) {
                problems.add(location + " declares " + annotation.value() + ", which does not apply to " + family);
            }
            used.add(annotation.value());
        }
    }

    /**
     * A nested parameter object, as opposed to a value: nesting is a readability device, so containers hold no group of
     * their own. Enums are values even though they live in the same package.
     */
    private static boolean isContainer(Class<?> type) {
        String packageName = type.getPackageName();
        return !type.isEnum()
                && (packageName.equals(PARAMETERS_PACKAGE) || packageName.startsWith(PARAMETERS_PACKAGE + "."));
    }

    /**
     * The type whose fields the sweep must visit through a field: the component type of an array, the parameter-object
     * type argument of a collection or map, or the field's own type. A collection of values resolves to the collection
     * itself, which keeps it a leaf that needs its own group.
     */
    private static Class<?> elementType(Field field) {
        Class<?> type = field.getType();
        if (type.isArray()) {
            return type.getComponentType();
        }
        if (Collection.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type)) {
            Class<?> nested = nestedTypeArgument(field.getGenericType());
            return nested != null ? nested : type;
        }
        return type;
    }

    private static Class<?> nestedTypeArgument(Type genericType) {
        if (!(genericType instanceof ParameterizedType parameterized)) {
            return null;
        }
        for (Type argument : parameterized.getActualTypeArguments()) {
            if (argument instanceof Class<?> candidate && isContainer(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private static List<Field> instanceFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
            Arrays
                    .stream(current.getDeclaredFields())
                    .filter(field -> !field.isSynthetic() && !Modifier.isStatic(field.getModifiers()))
                    .forEach(fields::add);
        }
        return fields;
    }

    private static List<Class<? extends SignatureParametersDto>> registeredSubtypes() {
        JsonSubTypes subTypes = SignatureParametersDto.class.getAnnotation(JsonSubTypes.class);
        assertNotNull(subTypes, "SignatureParametersDto must register its subtypes with @JsonSubTypes");
        return Arrays
                .stream(subTypes.value())
                .map(JsonSubTypes.Type::value)
                .<Class<? extends SignatureParametersDto>>map(type -> type.asSubclass(SignatureParametersDto.class))
                .toList();
    }

    /**
     * Default access on purpose throughout the fixtures: a private field would trip Sonar's unused-private-field rule
     * (java:S1068) instead of exercising the sweep. The container fields carry {@code @Valid} so the missing-@Valid
     * branch does not add a second problem and blur the assertions.
     */
    private static final class UnclassifiedFixture {

        String forgottenParameter;
    }

    private static final class NestedFixture {

        String forgottenNestedParameter;
    }

    private static final class CollectionOfObjectsFixture {

        @Valid
        List<NestedFixture> nested;
    }

    private static final class ArrayOfObjectsFixture {

        @Valid
        NestedFixture[] nested;
    }

    private static final class CollectionOfValuesFixture {

        List<String> values;
    }
}
