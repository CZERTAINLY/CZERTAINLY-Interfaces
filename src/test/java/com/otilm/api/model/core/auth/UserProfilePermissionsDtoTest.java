package com.otilm.api.model.core.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.testsupport.OpenApiSchemaTestSupport;
import com.otilm.core.model.auth.ResourceAction;
import io.swagger.v3.oas.models.media.Schema;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserProfilePermissionsDtoTest {

    private static final String PROFILE_SCHEMA = "UserProfilePermissionsDto";
    private static final String ACTIONS_SCHEMA = "ResourceActionsDto";

    private final ObjectMapper mapper = new ObjectMapper();

    private static Map<String, Schema> schemas() {
        return OpenApiSchemaTestSupport.openApi31Schemas(UserProfilePermissionsDto.class);
    }

    private static Schema<?> property(String schemaName, String propertyName) {
        Schema<?> schema = schemas().get(schemaName);

        assertTrue(schema != null, schemaName + " is not published at all");
        Schema<?> property = (Schema<?>) schema.getProperties().get(propertyName);

        assertTrue(property != null, schemaName + "." + propertyName + " is not published");
        return property;
    }

    private static UserProfilePermissionsDto settingsEditor() {
        return new UserProfilePermissionsDto(List.of(Resource.SETTINGS),
                List
                        .of(new ResourceActionsDto(Resource.SETTINGS,
                                List.of(ResourceAction.UPDATE, ResourceAction.UPDATE_BRANDING))));
    }

    /**
     * The wire codes, not the Java constant names, are what a generated client switches on. {@code @JsonValue} on both
     * enums is the only reason {@code UPDATE_BRANDING} arrives as {@code updateBranding}; losing it would rename every
     * member of a published enum without changing a single declaration.
     */
    @Test
    void serializesResourcesAndActionsAsTheirWireCodes() throws Exception {
        assertEquals("{\"allowedListings\":[\"settings\"],"
                + "\"allowedActions\":[{\"resource\":\"settings\",\"actions\":[\"update\",\"updateBranding\"]}]}",
                mapper.writeValueAsString(settingsEditor()));
    }

    /** A client gating a control on an absent field would fail open, so neither list may be optional. */
    @Test
    void publishesBothPermissionListsAsRequired() {
        assertEquals(Set.of("allowedListings", "allowedActions"),
                Set.copyOf(schemas().get(PROFILE_SCHEMA).getRequired()));
    }

    @Test
    void publishesBothResourceActionFieldsAsRequired() {
        assertEquals(Set.of("resource", "actions"), Set.copyOf(schemas().get(ACTIONS_SCHEMA).getRequired()));
    }

    /**
     * The nesting is three deep, and every level has to keep its typed reference. Resolved to a bare array of objects
     * or of strings at any level, a generated client gets untyped maps and loose strings where it should get the
     * {@code Resource} and {@code ResourceAction} enums - which is precisely how a caller ends up comparing a
     * misspelled action code that never matches.
     */
    @Test
    void publishesTypedReferencesThroughEveryNestedList() {
        assertEquals("#/components/schemas/Resource", property(PROFILE_SCHEMA, "allowedListings").getItems().get$ref());
        assertEquals("#/components/schemas/" + ACTIONS_SCHEMA,
                property(PROFILE_SCHEMA, "allowedActions").getItems().get$ref());
        assertEquals("#/components/schemas/Resource", property(ACTIONS_SCHEMA, "resource").get$ref());
        assertEquals("#/components/schemas/ResourceAction", property(ACTIONS_SCHEMA, "actions").getItems().get$ref());

        assertTrue(schemas().get("ResourceAction").getEnum().contains(ResourceAction.UPDATE_BRANDING.getCode()),
                "the referenced action enum does not publish the code clients gate branding on");
    }

    /**
     * The constraints on this field are the part a client gets wrong, and Javadoc does not reach the published
     * specification - only the {@code @Schema} description does. Asserted on the prose deliberately: rewording it is
     * allowed, but dropping one of these three constraints should require saying so here.
     */
    @Test
    void documentsTheConstraintsAClientCannotInferFromTheTypes() {
        String description = property(PROFILE_SCHEMA, "allowedActions").getDescription();

        for (String constraint : List
                .of("resource-level grants only", "Object-scoped grants are excluded",
                        "omitted rather than reported with an empty list", "not derivable from allowedListings")) {
            assertTrue(description.contains(constraint),
                    "the published description no longer states: " + constraint + "\ngot: " + description);
        }
    }
}
