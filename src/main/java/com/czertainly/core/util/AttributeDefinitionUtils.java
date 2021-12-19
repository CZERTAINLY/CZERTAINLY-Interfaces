package com.czertainly.core.util;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.*;
import com.czertainly.api.model.credential.CredentialDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AttributeDefinitionUtils {

    private static final ObjectMapper ATTRIBUTES_OBJECT_MAPPER = new ObjectMapper();

    public static AttributeDefinition getAttributeDefinition(String name, List<AttributeDefinition> attributes) {
        return attributes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }

    public static boolean containsAttributeDefinition(String name, List<AttributeDefinition> attributes) {
        AttributeDefinition definition = getAttributeDefinition(name, attributes);
        return definition != null;
    }

    public static <T extends Object> T getAttributeValue(String name, List<AttributeDefinition> attributes) {
        AttributeDefinition definition = getAttributeDefinition(name, attributes);

        if (definition == null || definition.getValue() == null) {
            return null;
        }

        return (T) definition.getValue();
    }

    public static NameAndIdDto getNameAndIdValue(String name, List<AttributeDefinition> attributes) {
        Serializable value = getAttributeValue(name, attributes);

        if (!(value instanceof Map)) {
            throw new IllegalArgumentException("Could not get NameAndId value. Attribute has wrong value: " + value);
        }

        Map<String, ?> valueMap = (Map) value;
        if (valueMap.containsKey("id") && valueMap.containsKey("name")) {
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(value, NameAndIdDto.class);
        } else {
            throw new IllegalArgumentException("Could not get NameAndId value. Attribute has wrong value: " + value);
        }
    }

    public static NameAndUuidDto getNameAndUuidValue(String name, List<AttributeDefinition> attributes) {
        Serializable value = getAttributeValue(name, attributes);

        if (!(value instanceof Map)) {
            throw new IllegalArgumentException("Could not get NameAndUuid value. Attribute has wrong value: " + value);
        }

        Map<String, ?> valueMap = (Map) value;
        if (valueMap.containsKey("uuid") && valueMap.containsKey("name")) {
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(value, NameAndUuidDto.class);
        } else {
            throw new IllegalArgumentException("Could not get NameAndUuid value. Attribute has wrong value: " + value);
        }
    }

    public static CredentialDto getCredentialValue(String name, List<AttributeDefinition> attributes) {
        Serializable value = getAttributeValue(name, attributes);

        if (!(value instanceof Map)) {
            throw new IllegalArgumentException("Could not get Credential value. Attribute has wrong value: " + value);
        }

        try {
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(value, CredentialDto.class);
        } catch (Exception e ) {
            throw new IllegalArgumentException("Could not get Credential value. Attribute has wrong value: " + value, e);
        }
    }

    public static String serialize(List<AttributeDefinition> attributes) {
        if (attributes == null) {
            return null;
        }
        try {
            return ATTRIBUTES_OBJECT_MAPPER.writeValueAsString(attributes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<AttributeDefinition> deserialize(String attributesJson) {
        if (attributesJson == null) {
            return null;
        }
        try {
            return ATTRIBUTES_OBJECT_MAPPER.readValue(attributesJson, new TypeReference<List<AttributeDefinition>>() {
            });
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<AttributeDefinition> mergeAttributes(List<AttributeDefinition> definitions, List<AttributeDefinition> attributes) throws ValidationException {
        if (definitions == null || attributes == null) {
            return List.of();
        }

        return attributes.stream()
                .map(a -> {
                    AttributeDefinition definition = getAttributeDefinition(a.getName(), definitions);
                    if (definition == null) {
                        return a;
                    }

                    AttributeDefinition extended = new AttributeDefinition(definition);
                    extended.setValue(a.getValue());
                    return extended;
                })
                .collect(Collectors.toList());
    }

    public static void validateAttributes(List<AttributeDefinition> definitions, List<AttributeDefinition> attributes) throws ValidationException {
        List<ValidationError> errors = new ArrayList<>();

        // If attribute identified by id not in definitions - throw error
        for (AttributeDefinition attribute : attributes) {
            if (!containsAttributeDefinition(attribute.getName(), definitions)) {
                errors.add(ValidationError.create("Attribute {} not supported.", attribute.getName()));
            }
        }

        for (AttributeDefinition definition : definitions) {
            AttributeDefinition attribute = getAttributeDefinition(definition.getName(), attributes);

            if (attribute == null) {
                if (definition.isRequired()) {
                    errors.add(ValidationError.create("Required attribute {} not found.", definition.getName()));
                }
                continue; // skip other validations
            }

            Serializable attributeValue = attribute.getValue();

            if (definition.isRequired() && attributeValue == null) {
                errors.add(ValidationError.create("Value of required attribute {} not set.", definition.getName()));
                continue; // required attribute has no value, skip other validations
            }

            if (definition.isReadOnly()) {
                Serializable definitionValue = definition.getValue();
                if (definitionValue == null || !definitionValue.equals(attributeValue)) {
                    errors.add(ValidationError.create(
                            "Wrong value of read only attribute {}. Definition value = {} and attribute value = {}.",
                            definition.getName(),
                            definitionValue,
                            attributeValue));
                }
            }

            validateAttributeValue(definition, attribute, errors);

            if (BaseAttributeDefinitionTypes.STRING.equals(definition.getType())
                    && definition.getValidationRegex() != null) {
                Pattern pattern;
                try {
                    pattern = Pattern.compile(definition.getValidationRegex());
                    Matcher matcher = pattern.matcher((String) attributeValue);
                    if (!matcher.matches()) {
                        errors.add(ValidationError.create(
                                "Value {} of attribute {} doesn't match regex {}",
                                attributeValue,
                                definition.getName(),
                                definition.getValidationRegex()));
                    }
                } catch (Exception e) {
                    errors.add(ValidationError.create(
                            "Could not validate value of field {} due to error {}",
                            definition.getName(),
                            ExceptionUtils.getRootCauseMessage(e)));
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Attributes validation failed.", errors);
        }
    }

    private static void validateAttributeValue(AttributeDefinition definition, AttributeDefinition attribute, List<ValidationError> errors) {

        if (definition.getType() == null) {
            errors.add(ValidationError.create("Type of attribute definition {} not set.", definition.getName()));
        }

        boolean wrongValue = false;
        switch (definition.getType()) {
            case STRING:
                wrongValue = !(attribute.getValue() instanceof String);
                break;
            case SECRET:
                // no type validation for secrets
                break;
            case FILE:
                boolean isString = attribute.getValue() instanceof String;
                if (isString) {
                    try {
                        Base64.getDecoder().decode(String.valueOf(attribute.getValue()));
                    } catch (Exception e) {
                        wrongValue = true;
                    }
                } else {
                    wrongValue = true;
                }
                break;
            case BOOLEAN:
                wrongValue = !(attribute.getValue() instanceof Boolean);
                break;
            case LIST:
                wrongValue = !((Collection) definition.getValue()).contains(attribute.getValue());
                break;
            case CREDENTIAL:
                wrongValue = !(attribute.getValue() instanceof CredentialDto) || !(attribute.getValue() instanceof Map);
                break;
            default:
                errors.add(ValidationError.create("Unknown type of attribute definition {} {}.", definition.getName(), definition.getType()));
                break;
        }

        if (wrongValue) {
            errors.add(ValidationError.create("Attribute {} of type {} has wrong value {}.", definition.getName(), definition.getType(), attribute.getValue()));
        }
    }

    public static void validateCallback(AttributeCallback callback) {
        List<ValidationError> errors = new ArrayList<>();

        if (StringUtils.isBlank(callback.getCallbackContext())) {
            errors.add(ValidationError.create("Callback context not set"));
        }

        if (StringUtils.isBlank(callback.getCallbackMethod())) {
            errors.add(ValidationError.create("Callback method not set"));
        }

        try {
            RequestMethod.valueOf(callback.getCallbackMethod());
        } catch (IllegalArgumentException e) {
            errors.add(ValidationError.create("Callback method invalid, because of {}", e.getMessage()));
        }

        if (callback.getMappings() != null) {
            for (AttributeCallbackMapping mapping : callback.getMappings()) {

                if (StringUtils.isBlank(mapping.getFrom()) && mapping.getValue() == null) {
                    errors.add(ValidationError.create(
                            "Callback mapping invalid - 'from' not set and value in null"));
                }

                if (StringUtils.isBlank(mapping.getTo())) {
                    errors.add(ValidationError.create("Callback mapping invalid - 'to' not set"));
                }

                if (mapping.getTargets() == null || mapping.getTargets().isEmpty()) {
                    errors.add(ValidationError.create("Callback mapping invalid - 'targets' not set"));
                }

                if (mapping.getTargets() != null) {
                    for (AttributeValueTarget target : mapping.getTargets()) {
                        switch (target) {
                            case PATH_VARIABLE:
                                if (callback.getPathVariables() == null || callback.getPathVariables().isEmpty()) {
                                    errors.add(ValidationError.create(
                                            "Callback path variables not set, but mapping require it {}", mapping));
                                    break;
                                }

                                if (callback.getPathVariables().get(mapping.getTo()) == null) {
                                    errors.add(ValidationError.create(
                                            "Callback path variable {} not set, but mapping require it {}", mapping.getTo(), mapping));
                                    break;
                                }
                                if (BaseAttributeDefinitionTypes.CREDENTIAL.equals(mapping.getAttributeType())) {
                                    errors.add(ValidationError.create(
                                            "Callback mapping {} invalid. Type {} not allowed for path variable", mapping, mapping.getAttributeType()));
                                    break;
                                }
                                break;
                            case REQUEST_PARAMETER:
                                if (callback.getQueryParameters() == null || callback.getQueryParameters().isEmpty()) {
                                    errors.add(ValidationError.create(
                                            "Callback query parameters not set, but mapping require it {}", mapping));
                                    break;
                                }

                                if (callback.getQueryParameters().get(mapping.getTo()) == null) {
                                    errors.add(ValidationError.create(
                                            "Callback query parameters {} not set, but mapping require it {}", mapping.getTo(), mapping));
                                    break;
                                }
                                if (BaseAttributeDefinitionTypes.CREDENTIAL.equals(mapping.getAttributeType())) {
                                    errors.add(ValidationError.create(
                                            "Callback mapping {} invalid. Type {} not allowed for query parameter", mapping, mapping.getAttributeType()));
                                    break;
                                }
                                break;
                            case BODY:
                                if (callback.getRequestBody() == null || callback.getRequestBody().isEmpty()) {
                                    errors.add(ValidationError.create(
                                            "Callback request body not set, but mapping require it {}", mapping));
                                    break;
                                }

                                if (callback.getRequestBody().get(mapping.getTo()) == null) {
                                    errors.add(ValidationError.create(
                                            "Callback request body key {} not set, but mapping require it {}", mapping.getTo(), mapping));
                                    break;
                                }
                        }
                    }
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Attribute callback validation failed.", errors);
        }
    }

    public static List<AttributeDefinition> createAttributes(String name, Serializable value) {
        AttributeDefinition attribute = new AttributeDefinition();
        attribute.setId(UUID.randomUUID().toString());
        attribute.setName(name);
        attribute.setType(BaseAttributeDefinitionTypes.STRING);
        attribute.setValue(value);
        return createAttributes(attribute);
    }

    public static List<AttributeDefinition> createAttributes(AttributeDefinition attribute) {
        List<AttributeDefinition> attributes = createAttributes();
        attributes.add(attribute);

        return attributes;
    }

    public static List<AttributeDefinition> createAttributes() {
        return new ArrayList<>();
    }
}
