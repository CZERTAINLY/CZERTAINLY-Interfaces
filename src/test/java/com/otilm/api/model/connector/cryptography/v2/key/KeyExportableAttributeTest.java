package com.otilm.api.model.connector.cryptography.v2.key;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.client.attribute.RequestAttributeV2;
import com.otilm.api.model.common.attribute.common.AttributeType;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.v2.DataAttributeV2;
import com.otilm.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.BooleanAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.StringAttributeContentV2;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyExportableAttributeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void definition_isTheReservedBooleanDataAttribute() {
        // given
        // when
        DataAttributeV2 definition = KeyExportableAttribute.definition();

        // then
        assertEquals(KeyExportableAttribute.NAME, definition.getName());
        assertEquals(AttributeType.DATA, definition.getType());
        assertEquals(AttributeContentType.BOOLEAN, definition.getContentType());
        assertEquals(2, definition.getVersion());
    }

    @Test
    void definition_defaultsToNotExportableAndAcceptsExactlyOneValue() {
        // given
        // when
        DataAttributeV2 definition = KeyExportableAttribute.definition();

        // then
        assertEquals(List.of(new BooleanAttributeContentV2(Boolean.FALSE)), definition.getContent());
        assertTrue(definition.getProperties().isRequired());
        assertFalse(definition.getProperties().isList());
        assertFalse(definition.getProperties().isMultiSelect());
        assertFalse(definition.getProperties().isReadOnly());
    }

    @Test
    void definition_isANewInstanceEachTime() {
        // given
        DataAttributeV2 first = KeyExportableAttribute.definition();

        // when
        DataAttributeV2 second = KeyExportableAttribute.definition();

        // then
        assertNotSame(first, second);
        assertNotSame(first.getProperties(), second.getProperties());
        assertEquals(first.getUuid(), second.getUuid());
    }

    @Test
    void isRequested_failsClosed_whenTheAttributeIsAbsent() {
        // given
        List<RequestAttribute> attributes = List.of(otherAttribute());

        // when
        boolean exportable = KeyExportableAttribute.isRequested(attributes);

        // then
        assertFalse(exportable);
    }

    @Test
    void isRequested_failsClosed_whenNoAttributesAreSupplied() {
        // given
        // when
        boolean exportable = KeyExportableAttribute.isRequested(null);

        // then
        assertFalse(exportable);
    }

    @Test
    void isRequested_readsTheRequestedValue() {
        // given
        List<RequestAttribute> requested = List.of(keyExportable(new BooleanAttributeContentV2(Boolean.TRUE)));
        List<RequestAttribute> declined = List.of(keyExportable(new BooleanAttributeContentV2(Boolean.FALSE)));

        // when
        // then
        assertTrue(KeyExportableAttribute.isRequested(requested));
        assertFalse(KeyExportableAttribute.isRequested(declined));
    }

    @Test
    void isRequested_rejectsDuplicateDeclarations() {
        // given
        List<RequestAttribute> attributes = List
                .of(keyExportable(new BooleanAttributeContentV2(Boolean.TRUE)),
                        keyExportable(new BooleanAttributeContentV2(Boolean.FALSE)));

        // when
        IllegalArgumentException failure = assertThrows(IllegalArgumentException.class,
                () -> KeyExportableAttribute.isRequested(attributes));

        // then
        assertEquals("keyExportable must be supplied at most once", failure.getMessage());
    }

    @Test
    void isRequested_rejectsContentThatIsNotASingleItem() {
        // given
        List<RequestAttribute> empty = List.of(keyExportable());
        List<RequestAttribute> two = List
                .of(keyExportable(new BooleanAttributeContentV2(Boolean.TRUE),
                        new BooleanAttributeContentV2(Boolean.FALSE)));

        // when
        // then
        assertEquals("keyExportable must carry exactly one content item",
                assertThrows(IllegalArgumentException.class, () -> KeyExportableAttribute.isRequested(empty))
                        .getMessage());
        assertEquals("keyExportable must carry exactly one content item",
                assertThrows(IllegalArgumentException.class, () -> KeyExportableAttribute.isRequested(two))
                        .getMessage());
    }

    @Test
    void isRequested_rejectsContentThatIsNotBoolean() {
        // given
        List<RequestAttribute> attributes = List.of(keyExportable(new StringAttributeContentV2("true")));

        // when
        IllegalArgumentException failure = assertThrows(IllegalArgumentException.class,
                () -> KeyExportableAttribute.isRequested(attributes));

        // then
        assertEquals("keyExportable must carry boolean content", failure.getMessage());
    }

    /**
     * A request that arrived as JSON carries generic attribute content: nothing in the document names a content class,
     * so the intent must be read from the value.
     */
    @Test
    void isRequested_readsContentThatArrivedAsJson() throws Exception {
        // given
        String requested = wireRequest("true");
        String declined = wireRequest("false");

        // when
        // then
        assertTrue(KeyExportableAttribute.isRequested(parse(requested)));
        assertFalse(KeyExportableAttribute.isRequested(parse(declined)));
    }

    @Test
    void isRequested_rejectsJsonContentThatIsNotABoolean() throws Exception {
        // given
        List<RequestAttribute> attributes = parse(wireRequest("\"true\""));

        // when
        IllegalArgumentException failure = assertThrows(IllegalArgumentException.class,
                () -> KeyExportableAttribute.isRequested(attributes));

        // then
        assertEquals("keyExportable must carry boolean content", failure.getMessage());
    }

    @Test
    void isRequested_rejectsAContentTypeOtherThanBoolean() {
        // given
        RequestAttributeV2 attribute = new RequestAttributeV2();
        attribute.setName(KeyExportableAttribute.NAME);
        attribute.setContentType(AttributeContentType.STRING);
        attribute.setContent(List.of(new BooleanAttributeContentV2(Boolean.TRUE)));
        List<RequestAttribute> attributes = List.of(attribute);

        // when
        IllegalArgumentException failure = assertThrows(IllegalArgumentException.class,
                () -> KeyExportableAttribute.isRequested(attributes));

        // then
        assertEquals("keyExportable must carry boolean content", failure.getMessage());
    }

    @Test
    void isRequested_rejectsBooleanContentWithoutAValue() {
        // given
        List<RequestAttribute> attributes = List.of(keyExportable(new BooleanAttributeContentV2()));

        // when
        IllegalArgumentException failure = assertThrows(IllegalArgumentException.class,
                () -> KeyExportableAttribute.isRequested(attributes));

        // then
        assertEquals("keyExportable must carry boolean content", failure.getMessage());
    }

    private static String wireRequest(String data) {
        return "[{\"uuid\":\"9d3f1a26-5d4e-4b6c-8f0a-6c1f2d7e4b83\",\"name\":\"keyExportable\","
                + "\"contentType\":\"boolean\",\"content\":[{\"data\":" + data + "}]}]";
    }

    private static List<RequestAttribute> parse(String json) throws Exception {
        return MAPPER.readValue(json, new TypeReference<List<RequestAttribute>>() {
        });
    }

    private static RequestAttribute keyExportable(BaseAttributeContentV2<?>... content) {
        RequestAttributeV2 attribute = new RequestAttributeV2();
        attribute.setName(KeyExportableAttribute.NAME);
        attribute.setContentType(AttributeContentType.BOOLEAN);
        attribute.setContent(List.of(content));
        return attribute;
    }

    private static RequestAttribute otherAttribute() {
        RequestAttributeV2 attribute = new RequestAttributeV2();
        attribute.setName("keyAlias");
        attribute.setContentType(AttributeContentType.STRING);
        attribute.setContent(List.of(new StringAttributeContentV2("server-key")));
        return attribute;
    }
}
