package com.otilm.api.model.connector.discovery.v2;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Guards the one thing that makes a mislabelled payload detectable at all.
 *
 * <p>
 * Payload subtypes tolerate unknown properties, so connectors can add a field without a lock-step release. The cost is
 * that Jackson cannot tell a wrong-shaped payload from a right-shaped one carrying a new field: a body whose
 * {@code resource} says {@code keys} but whose fields are a certificate's deserializes into {@code DiscoveredKeyDto}
 * with every one of its own fields left null. Nothing about the resource resolution rejects it. What rejects it is that
 * the resulting instance then violates its own required-field constraints.
 *
 * <p>
 * That makes those constraints load-bearing rather than decorative, and it makes their absence invisible: a future
 * payload subtype with no required field would accept any mislabelled body silently, and every existing test would stay
 * green because none of them mentions the new type. This test enumerates the registered subtypes instead of naming
 * them, so adding one without a constraint fails the build.
 */
class DiscoveredItemPayloadConstraintTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final Validator VALIDATOR = VALIDATORS.validator();

    @Test
    void everyRegisteredPayloadSubtypeRejectsAnEmptyInstance() throws ReflectiveOperationException {
        List<Class<?>> subtypes = registeredSubtypes();

        // A guard that enumerates nothing would pass forever.
        assertFalse(subtypes.isEmpty(), "expected @JsonSubTypes on DiscoveredItemPayloadDto to register subtypes");

        for (Class<?> subtype : subtypes) {
            Object empty = subtype.getDeclaredConstructor().newInstance();
            Set<ConstraintViolation<Object>> violations = VALIDATOR.validate(empty);

            assertFalse(violations.isEmpty(),
                    subtype.getSimpleName() + " accepts an instance with nothing set. Because payload subtypes "
                            + "tolerate unknown properties, that means a body whose resource names this type while "
                            + "carrying another type's fields would validate cleanly instead of being rejected. Give "
                            + "this type at least one constraint that an empty instance violates.");

            // The discriminator is fixed per subtype, so it can never be the violated field — if it
            // were the only one, the guard above would pass while proving nothing about the shape.
            assertTrue(violations.stream().anyMatch(v -> !"resource".equals(v.getPropertyPath().toString())),
                    subtype.getSimpleName() + " must constrain something other than the discriminator, which is "
                            + "fixed to a constant and cannot distinguish a mislabelled payload");
        }
    }

    private static List<Class<?>> registeredSubtypes() {
        JsonSubTypes annotation = DiscoveredItemPayloadDto.class.getAnnotation(JsonSubTypes.class);
        assertNotNull(annotation, "DiscoveredItemPayloadDto must declare @JsonSubTypes");

        List<Class<?>> subtypes = new ArrayList<>();
        for (JsonSubTypes.Type type : annotation.value()) {
            subtypes.add(type.value());
        }
        return subtypes;
    }
}
