package com.czertainly.core.util;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndIdDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.common.attribute.v2.CustomAttribute;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallbackMapping;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeValueTarget;
import com.czertainly.api.model.common.attribute.v2.callback.RequestAttributeCallback;
import com.czertainly.api.model.common.attribute.v2.constraint.AttributeConstraintType;
import com.czertainly.api.model.common.attribute.v2.constraint.BaseAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.constraint.data.DateTimeAttributeConstraintData;
import com.czertainly.api.model.common.attribute.v2.constraint.data.RangeAttributeConstraintData;
import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v2.content.data.CredentialAttributeContentData;
import com.czertainly.api.model.common.attribute.v2.properties.CustomAttributeProperties;
import com.czertainly.api.model.common.attribute.v2.properties.DataAttributeProperties;
import com.czertainly.api.model.core.credential.CredentialDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AttributeDefinitionUtils {

    private static final ObjectMapper ATTRIBUTES_OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Logger logger = LoggerFactory.getLogger(AttributeDefinitionUtils.class);

    public static <T extends BaseAttribute<?>> T getAttributeDefinition(String name, List<T> attributes) {
        return attributes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }

    public static <T extends BaseAttribute<?>> boolean containsAttributeDefinition(String name, List<T> attributes) {
        BaseAttribute definition = getAttributeDefinition(name, attributes);
        return definition != null;
    }

    public static <T extends Object> T getRequestAttributes(String name, List<?> attributes) {
        if (attributes.size() == 0) {
            return null;
        }
        if (attributes.get(0) instanceof RequestAttributeDto) {
            List<RequestAttributeDto> reloadedAttributes = (List<RequestAttributeDto>) attributes;
            return (T) reloadedAttributes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
        } else if (attributes.get(0) instanceof BaseAttribute) {
            List<BaseAttribute> reloadedAttributes = (List<BaseAttribute>) attributes;
            return (T) reloadedAttributes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
        } else if (attributes.get(0) instanceof ResponseAttributeDto) {
            List<ResponseAttributeDto> reloadedAttributes = (List<ResponseAttributeDto>) attributes;
            return (T) reloadedAttributes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
        } else {
            throw new IllegalArgumentException("Invalid Object to get Attribute value");
        }

    }

    public static boolean containsRequestAttributes(String name, List<RequestAttributeDto> attributes) {
        RequestAttributeDto definition = getRequestAttributes(name, attributes);
        return definition != null;
    }

    public static <T extends Object> T getAttributeContent(String name, List<?> attributes, Boolean singleItem) {
        if (attributes.size() == 0) {
            return null;
        }
        if (attributes.get(0) instanceof RequestAttributeDto) {
            RequestAttributeDto definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            if (!singleItem) {
                return (T) definition.getContent();
            } else {
                return ((List<T>) definition.getContent()).get(0);
            }
        } else if (attributes.get(0) instanceof BaseAttribute) {
            BaseAttribute definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            if (!singleItem) {
                return (T) definition.getContent();
            } else {
                return ((List<T>) definition.getContent()).get(0);
            }
        } else if (attributes.get(0) instanceof ResponseAttributeDto) {
            ResponseAttributeDto definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            if (!singleItem) {
                return (T) definition.getContent();
            } else {
                return ((List<T>) definition.getContent()).get(0);
            }
        } else {
            throw new IllegalArgumentException("Invalid Object to get Attribute value");
        }
    }

    public static <T extends Object> List<T> getAttributeContent(String name, List<?> attributes, Class<T> clazz) {
        if (attributes == null || attributes.size() == 0) {
            return null;
        }
        if (attributes.get(0) instanceof RequestAttributeDto) {
            RequestAttributeDto definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(definition.getContent(), ATTRIBUTES_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } else if (attributes.get(0) instanceof BaseAttribute) {
            BaseAttribute definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(definition.getContent(), ATTRIBUTES_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } else if (attributes.get(0) instanceof ResponseAttributeDto) {
            ResponseAttributeDto definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(definition.getContent(), ATTRIBUTES_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } else {
            throw new IllegalArgumentException("Invalid Object to get Attribute value");
        }
    }

    public static NameAndIdDto getNameAndIdData(String name, List<RequestAttributeDto> attributes) {
        if (attributes.size() == 0) {
            return null;
        }

        NameAndIdDto converted = getObjectAttributeContentData(name, attributes, NameAndIdDto.class).get(0);
        return converted;
    }

    public static NameAndUuidDto getNameAndUuidData(String name, List<RequestAttributeDto> attributes) {
        if (attributes.size() == 0) {
            return null;
        }

        NameAndUuidDto converted = getObjectAttributeContentData(name, attributes, NameAndUuidDto.class).get(0);
        return converted;
    }

    public static CredentialAttributeContentData getCredentialContent(String name, List<RequestAttributeDto> attributes) {
        List<CredentialAttributeContent> content = AttributeDefinitionUtils.getAttributeContent(name, attributes, CredentialAttributeContent.class);
        if (content != null && !content.isEmpty()) {
            return content.get(0).getData();
        }
        return null;
    }

    public static <T extends BaseAttribute<?>> String serialize(List<T> attributes) {
        if (attributes == null) {
            return null;
        }
        try {
            return ATTRIBUTES_OBJECT_MAPPER.writeValueAsString(attributes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    public static <T extends BaseAttribute<?>> String serialize(T attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return ATTRIBUTES_OBJECT_MAPPER.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String serializeRequestAttributes(List<RequestAttributeDto> attributes) {
        if (attributes == null) {
            return null;
        }
        try {
            return ATTRIBUTES_OBJECT_MAPPER.writeValueAsString(attributes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T extends BaseAttributeContent<?>> String serializeAttributeContent(List<T> attributeContent) {
        if (attributeContent == null) {
            return null;
        }
        try {
            return ATTRIBUTES_OBJECT_MAPPER.writeValueAsString(attributeContent);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T extends BaseAttribute<?>> List<T> deserialize(String attributesJson, Class<T> clazz) {
        if (attributesJson == null) {
            return null;
        }
        try {
            return ATTRIBUTES_OBJECT_MAPPER.readValue(attributesJson, ATTRIBUTES_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T extends BaseAttribute<?>> T deserializeSingleAttribute(String attributeJson, Class<T> clazz) {
        if (attributeJson == null) {
            return null;
        }
        try {
            return ATTRIBUTES_OBJECT_MAPPER.readValue(attributeJson, clazz);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<RequestAttributeDto> deserializeRequestAttributes(String attributesJson) {
        if (attributesJson == null) {
            return null;
        }
        try {
            return ATTRIBUTES_OBJECT_MAPPER.readValue(attributesJson, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T extends BaseAttributeContent<?>> List<T> deserializeAttributeContent(String attributeContentJson, Class<T> clazz) {
        if (attributeContentJson == null) {
            return null;
        }
        try {
            return ATTRIBUTES_OBJECT_MAPPER.readValue(attributeContentJson, ATTRIBUTES_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    public static List<DataAttribute> mergeAttributes(List<BaseAttribute> definitions, List<RequestAttributeDto> attributes) throws ValidationException {
        if (definitions == null || attributes == null) {
            return List.of();
        }

        List<DataAttribute> attributeDefinitions = clientAttributeConverter(attributes);
        return attributeDefinitions.stream()
                .map(a -> {
                    DataAttribute definition = (DataAttribute) getAttributeDefinition(a.getName(), definitions);
                    if (definition == null) {
                        return a;
                    }

                    DataAttribute extended = new DataAttribute(definition);
                    extended.setContent(a.getContent());
                    return extended;
                })
                .collect(Collectors.toList());
    }

    //TODO - Rework
    public static void validateAttributes(List<BaseAttribute> definitions, List<RequestAttributeDto> attributes) throws ValidationException {
        List<ValidationError> errors = new ArrayList<>();

        // When the Group Attribute contains a group of other attributes, we currently do not have the definition of them
        // without executing the same sequence of callbacks or storing the definition in the database. Therefore,
        // we will need to skip the validation of Attributes that are unknown and rely on proper validation by the
        // connector.
        // TODO: Validation of Attributes that has unknown definition
        for (RequestAttributeDto attribute : attributes) {
            if (!containsAttributeDefinition(attribute.getName(), definitions)) {
                // do not throw error in case the definition is not found, warn only
                logger.warn("Cannot validate Attribute '{}' as it has unknown definition", attribute.getName());
                // errors.add(ValidationError.create("Attribute {} not supported.", attribute.getName()));
            }
        }

        for (BaseAttribute definition : definitions) {

            RequestAttributeDto attribute = getRequestAttributes(definition.getName(), attributes);

            Boolean isRequired = false;
            Boolean isReadOnly = false;
            String label = null;
            AttributeContentType contentType;

            if (definition.getType().equals(AttributeType.DATA)) {
                DataAttribute dataAttribute = (DataAttribute) definition;
                contentType = dataAttribute.getContentType();
                if (dataAttribute.getProperties() != null) {
                    isRequired = dataAttribute.getProperties().isRequired();
                    isReadOnly = dataAttribute.getProperties().isReadOnly();
                    label = dataAttribute.getProperties().getLabel();
                }
            } else if (definition.getType().equals(AttributeType.CUSTOM)) {
                CustomAttribute customAttribute = (CustomAttribute) definition;
                contentType = customAttribute.getContentType();
                if (customAttribute.getProperties() != null) {
                    isRequired = customAttribute.getProperties().isRequired();
                    isReadOnly = customAttribute.getProperties().isReadOnly();
                    label = customAttribute.getProperties().getLabel();
                }
            } else {
                logger.warn("Cannot validate " + definition.getType() + " attributes");
                continue;
            }

            if (attribute == null) {
                if (isRequired) {
                    errors.add(ValidationError.create("Required attribute {} not found.", label));
                }
                continue; // skip other validations
            }

            Object attributeContent = null;
            try {
                attributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(attribute.getContent(), ATTRIBUTES_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, contentType.getContentClass()));
            } catch (IllegalArgumentException e) {
                errors.add(ValidationError.create(
                        "Wrong type of value for attribute {}.",
                        label));
                continue;
            }

            if (isRequired && attributeContent == null) {
                errors.add(ValidationError.create("Value of required attribute {} not set.", label));
                continue; // required attribute has no value, skip other validations
            }

            if (isReadOnly) {
                Object definitionContent = definition.getContent();
                if (definitionContent == null || !definitionContent.equals(attributeContent)) {
                    errors.add(ValidationError.create(
                            "Wrong value of read only attribute {}. Definition value = {} and attribute value = {}.",
                            label,
                            definitionContent,
                            attributeContent));
                }
            }

            validateAttributeContent(definition, attribute.getContent(), errors);
            errors.addAll(validateConstraints(definition, attribute.getContent()));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Attributes validation failed.", errors);
        }
    }

    private static List<ValidationError> validateConstraints(BaseAttribute attribute, List<BaseAttributeContent> contents) {
        List<BaseAttributeConstraint> constraints = null;
        AttributeContentType contentType = null;
        String label = null;

        if (attribute.getType().equals(AttributeType.DATA)) {
            DataAttribute dataAttribute = (DataAttribute) attribute;
            constraints = dataAttribute.getConstraints();
            contentType = dataAttribute.getContentType();
            if (dataAttribute.getProperties() != null) label = dataAttribute.getProperties().getLabel();
        } else {
            return new ArrayList<>();
        }
        if (constraints == null) return new ArrayList<>();

        List<ValidationError> errors = new ArrayList<>();
        for (BaseAttributeConstraint constraint : constraints) {
            if (constraint.getType().equals(AttributeConstraintType.REGEXP)) {
                if (!contentType.equals(AttributeContentType.STRING)) {
                    errors.add(ValidationError.create("Invalid Attribute Constraint Type and Attribute Content Type. Regexp can be associated for STRING type only"));
                }
                Pattern pattern;
                try {
                    pattern = Pattern.compile((String) constraint.getData());
                    List<StringAttributeContent> content = ATTRIBUTES_OBJECT_MAPPER.convertValue(contents, ATTRIBUTES_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, StringAttributeContent.class));
                    for (StringAttributeContent value : content) {
                        Matcher matcher = pattern.matcher(value.getData());
                        if (!matcher.matches()) {
                            errors.add(ValidationError.create(
                                    "Value {} of attribute {} doesn't match regex {}",
                                    value.getData(),
                                    label,
                                    constraint.getData()));
                        }
                    }
                } catch (Exception e) {
                    errors.add(ValidationError.create(
                            "Could not validate value of field {} due to error {}",
                            label,
                            ExceptionUtils.getRootCauseMessage(e)));
                }
            } else if (constraint.getType().equals(AttributeConstraintType.DATETIME)) {
                if (!contentType.equals(AttributeContentType.DATETIME)) {
                    errors.add(ValidationError.create("Invalid Attribute Constraint Type and Attribute Content Type. DateTime can be associated for DATETIME type only"));
                }
                try {
                    List<DateTimeAttributeContent> content = ATTRIBUTES_OBJECT_MAPPER.convertValue(contents, ATTRIBUTES_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, DateTimeAttributeContent.class));
                    DateTimeAttributeConstraintData constraintData = (DateTimeAttributeConstraintData) constraint.getData();
                    for (DateTimeAttributeContent value : content) {
                        if (constraintData.getFrom() != null) {
                            if (value.getData().isBefore(ZonedDateTime.from(constraintData.getFrom().atZone(ZoneId.systemDefault())))) {
                                errors.add(ValidationError.create(
                                        "Value {} of attribute {} should be after {}",
                                        value.getData(),
                                        label,
                                        constraintData.getFrom()));
                            }
                        }
                        if (constraintData.getTo() != null) {
                            if (value.getData().isAfter(ZonedDateTime.from(constraintData.getTo().atZone(ZoneId.systemDefault())))) {
                                errors.add(ValidationError.create(
                                        "Value {} of attribute {} should be before {}",
                                        value.getData(),
                                        label,
                                        constraintData.getTo()));
                            }
                        }
                    }
                } catch (Exception e) {
                    errors.add(ValidationError.create(
                            "Could not validate value of field {} due to error {}",
                            label,
                            ExceptionUtils.getRootCauseMessage(e)));
                }
            } else if (constraint.getType().equals(AttributeConstraintType.RANGE)) {
                RangeAttributeConstraintData constraintData = (RangeAttributeConstraintData) constraint.getData();
                if (contentType.equals(AttributeContentType.INTEGER)) {
                    List<IntegerAttributeContent> content = ATTRIBUTES_OBJECT_MAPPER.convertValue(contents, ATTRIBUTES_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, IntegerAttributeContent.class));
                    for (IntegerAttributeContent value : content) {
                        if (constraintData.getFrom() != null) {
                            if (value.getData() < constraintData.getFrom()) {
                                errors.add(ValidationError.create(
                                        "Value {} of attribute {} should be higher than {}",
                                        value.getData(),
                                        label,
                                        constraintData.getFrom()));
                            }
                        }
                        if (constraintData.getTo() != null) {
                            if (value.getData() > constraintData.getTo()) {
                                errors.add(ValidationError.create(
                                        "Value {} of attribute {} should be lower than {}",
                                        value.getData(),
                                        label,
                                        constraintData.getTo()));
                            }
                        }
                    }
                } else if (contentType.equals(AttributeContentType.FLOAT)) {
                    List<FloatAttributeContent> content = ATTRIBUTES_OBJECT_MAPPER.convertValue(contents, ATTRIBUTES_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, FloatAttributeContent.class));
                    for (FloatAttributeContent value : content) {
                        if (constraintData.getFrom() != null) {
                            if (value.getData() < constraintData.getFrom()) {
                                errors.add(ValidationError.create(
                                        "Value {} of attribute {} should be higher than {}",
                                        value.getData(),
                                        label,
                                        constraintData.getFrom()));
                            }
                        }
                        if (constraintData.getTo() != null) {
                            if (value.getData() > constraintData.getTo()) {
                                errors.add(ValidationError.create(
                                        "Value {} of attribute {} should be lower than {}",
                                        value.getData(),
                                        label,
                                        constraintData.getTo()));
                            }
                        }
                    }
                } else {
                    errors.add(ValidationError.create("Invalid Attribute Constraint Type and Attribute Content Type. Range can be validated only for INTEGER and FLOAT"));
                }
            }
        }
        return errors;
    }

    public static void validateAttributeContent(BaseAttribute definition, Object attributeContent, List<ValidationError> errors) {

        if (definition.getType() == null) {
            errors.add(ValidationError.create("Type of attribute definition not set."));
        }

        // TODO: checking all items in the list for the type

        AttributeContentType contentType;
        String label = null;
        if (definition.getType().equals(AttributeType.DATA)) {
            DataAttribute dataAttribute = (DataAttribute) definition;
            contentType = dataAttribute.getContentType();
            if (dataAttribute.getProperties() != null) label = dataAttribute.getProperties().getLabel();
        } else {
            CustomAttribute customAttribute = (CustomAttribute) definition;
            contentType = customAttribute.getContentType();
            if (customAttribute.getProperties() != null) label = customAttribute.getProperties().getLabel();
        }
        boolean wrongValue = false;
        try {
            for (Object baseAttributeContent : (List<Object>) attributeContent) {
                switch (contentType) {
                    case STRING:
                        BaseAttributeContent<?> stringBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, StringAttributeContent.class);
                        if (stringBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case INTEGER:
                        BaseAttributeContent<?> integerBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, IntegerAttributeContent.class);
                        if (integerBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case SECRET:
                        BaseAttributeContent<?> secretBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, SecretAttributeContent.class);
                        if (secretBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case BOOLEAN:
                        BaseAttributeContent<?> boolBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, BooleanAttributeContent.class);
                        if (boolBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case FLOAT:
                        BaseAttributeContent<?> floatBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, FloatAttributeContent.class);
                        if (floatBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case TEXT:
                        BaseAttributeContent<?> textBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, TextAttributeContent.class);
                        if (textBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case FILE:
                        FileAttributeContent fileBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, FileAttributeContent.class);
                        if (fileBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        Base64.getDecoder().decode(fileBaseAttributeContent.getData().getContent());
                        break;
                    case CREDENTIAL:
                        CredentialAttributeContent credentialBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, CredentialAttributeContent.class);
                        if (credentialBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        CredentialDto credentialDto = ATTRIBUTES_OBJECT_MAPPER.convertValue(credentialBaseAttributeContent.getData(), CredentialDto.class);
                        if (credentialDto == null) {
                            errors.add(ValidationError.create("Wrong data of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case DATE:
                        DateAttributeContent dateBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, DateAttributeContent.class);
                        if (dateBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case OBJECT:
                        ObjectAttributeContent jsonBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, ObjectAttributeContent.class);
                        if (jsonBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case TIME:
                        TimeAttributeContent timeBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, TimeAttributeContent.class);
                        if (timeBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case DATETIME:
                        DateTimeAttributeContent dateTimeBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, DateTimeAttributeContent.class);
                        if (dateTimeBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case CODEBLOCK:
                        final CodeBlockAttributeContent codeBlockAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, CodeBlockAttributeContent.class);
                        if (codeBlockAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", label, definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    default:
                        errors.add(ValidationError.create("Unknown type of Attribute definition {} {}.", label, definition.getType()));
                        break;
                }
            }
        } catch (Exception e) {
            wrongValue = true;
        }

        if (wrongValue) {
            errors.add(ValidationError.create("Attribute {} of type {} has wrong value.", label, definition.getType()));
        }
    }

    public static void validateCallback(AttributeCallback callback, RequestAttributeCallback request) {
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
                                if (request.getPathVariable() == null || request.getPathVariable().isEmpty()) {
                                    errors.add(ValidationError.create(
                                            "Callback path variables not set, but mapping require it {}", mapping));
                                    break;
                                }

                                if (request.getPathVariable().get(mapping.getTo()) == null) {
                                    errors.add(ValidationError.create(
                                            "Callback path variable {} not set, but mapping require it {}", mapping.getTo(), mapping));
                                    break;
                                }
                                if (AttributeContentType.CREDENTIAL.equals(mapping.getAttributeContentType())) {
                                    errors.add(ValidationError.create(
                                            "Callback mapping {} invalid. Type {} not allowed for path variable", mapping, mapping.getAttributeType()));
                                    break;
                                }
                                break;
                            case REQUEST_PARAMETER:
                                if (request.getRequestParameter() == null || request.getRequestParameter().isEmpty()) {
                                    errors.add(ValidationError.create(
                                            "Callback query parameters not set, but mapping require it {}", mapping));
                                    break;
                                }

                                if (request.getRequestParameter().get(mapping.getTo()) == null) {
                                    errors.add(ValidationError.create(
                                            "Callback query parameters {} not set, but mapping require it {}", mapping.getTo(), mapping));
                                    break;
                                }
                                if (AttributeContentType.CREDENTIAL.equals(mapping.getAttributeContentType())) {
                                    errors.add(ValidationError.create(
                                            "Callback mapping {} invalid. Type {} not allowed for query parameter", mapping, mapping.getAttributeType()));
                                    break;
                                }
                                break;
                            case BODY:
                                if (request.getBody() == null || request.getBody().isEmpty()) {
                                    errors.add(ValidationError.create(
                                            "Callback request body not set, but mapping require it {}", mapping));
                                    break;
                                }

                                if (request.getBody().get(mapping.getTo()) == null) {
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

    public static List<RequestAttributeDto> createAttributes(String name, List<BaseAttributeContent> content) {
        return createAttributes(UUID.randomUUID().toString(), name, content);
    }

    public static List<RequestAttributeDto> createAttributes(String uuid, String name, List<BaseAttributeContent> content) {
        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setUuid(uuid);
        attribute.setName(name);
        attribute.setContent(content);

        return List.of(attribute);
    }

    public static List<DataAttribute> clientAttributeConverter(List<RequestAttributeDto> attributes) {
        if (attributes == null) {
            return new ArrayList<>();
        }
        List<DataAttribute> convertedDefinition = new ArrayList<>();
        for (RequestAttributeDto clt : attributes) {
            DataAttribute atr = new DataAttribute();
            atr.setContent(clt.getContent());
            atr.setName(clt.getName());
            atr.setUuid(clt.getUuid());
            convertedDefinition.add(atr);
        }
        return convertedDefinition;
    }

    public static List<DataAttribute> responseAttributeConverter(List<ResponseAttributeDto> attributes) {
        if (attributes == null) {
            return new ArrayList<>();
        }
        List<DataAttribute> convertedDefinition = new ArrayList<>();
        for (ResponseAttributeDto clt : attributes) {
            DataAttribute atr = new DataAttribute();
            atr.setContent(clt.getContent());
            atr.setName(clt.getName());
            atr.setUuid(clt.getUuid());
            atr.setContentType(clt.getContentType());
            DataAttributeProperties properties = new DataAttributeProperties();
            properties.setLabel(clt.getLabel());
            atr.setProperties(properties);
            convertedDefinition.add(atr);
        }
        return convertedDefinition;
    }

    public static List<RequestAttributeDto> getClientAttributes(List<?> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return new ArrayList<>();
        }
        List<RequestAttributeDto> convertedDefinition = new ArrayList<>();
        if (attributes.get(0) instanceof BaseAttribute) {
            for (BaseAttribute clt : (List<BaseAttribute>) attributes) {
                if (clt.getType() != AttributeType.DATA) {
                    continue;
                }

                DataAttribute dataAttribute = (DataAttribute) clt;
                RequestAttributeDto atr = new RequestAttributeDto();
                atr.setContent(dataAttribute.getContent());
                atr.setName(clt.getName());
                atr.setUuid(clt.getUuid());
                convertedDefinition.add(atr);
            }
        } else if (attributes.get(0) instanceof ResponseAttributeDto) {
            List<ResponseAttributeDto> itrAttributes = (List<ResponseAttributeDto>) attributes;
            for (ResponseAttributeDto clt : itrAttributes) {
                RequestAttributeDto atr = new RequestAttributeDto();
                atr.setName(clt.getName());
                atr.setUuid(clt.getUuid());
                atr.setContentType(clt.getContentType());
                atr.setContent(clt.getContent());
                convertedDefinition.add(atr);
            }
        } else {
            throw new IllegalArgumentException("Invalid argument provided to get Attributes");
        }
        return convertedDefinition;
    }

    public static List<ResponseAttributeDto> getResponseAttributes(List<?> attributes) {
        if (attributes == null || attributes.size() == 0) {
            return new ArrayList<>();
        }
        List<ResponseAttributeDto> convertedDefinition = new ArrayList<>();
        if (attributes.get(0) instanceof DataAttribute) {
            List<DataAttribute> itrAttributes = (List<DataAttribute>) attributes;
            for (DataAttribute clt : itrAttributes) {
                if (clt.getProperties() == null) {
                    DataAttributeProperties props = new DataAttributeProperties();
                    props.setLabel(clt.getName());
                    clt.setProperties(props);
                }
                ResponseAttributeDto atr = new ResponseAttributeDto();
                atr.setContent(clt.getContent());
                atr.setName(clt.getName());
                atr.setUuid(clt.getUuid());
                atr.setLabel(clt.getProperties().getLabel());
                atr.setType(clt.getType());
                atr.setContentType(clt.getContentType());
                convertedDefinition.add(atr);
            }
        } else if (attributes.get(0) instanceof CustomAttribute) {
            List<CustomAttribute> itrAttributes = (List<CustomAttribute>) attributes;
            for (CustomAttribute clt : itrAttributes) {
                if (clt.getProperties() == null) {
                    CustomAttributeProperties props = new CustomAttributeProperties();
                    props.setLabel(clt.getName());
                    clt.setProperties(props);
                }
                ResponseAttributeDto atr = new ResponseAttributeDto();
                atr.setContent(clt.getContent());
                atr.setName(clt.getName());
                atr.setUuid(clt.getUuid());
                atr.setLabel(clt.getProperties().getLabel());
                atr.setType(clt.getType());
                atr.setContentType(clt.getContentType());
                convertedDefinition.add(atr);
            }
        } else if (attributes.get(0) instanceof RequestAttributeDto) {
            List<RequestAttributeDto> itrAttributes = (List<RequestAttributeDto>) attributes;
            for (RequestAttributeDto clt : itrAttributes) {
                ResponseAttributeDto atr = new ResponseAttributeDto();
                atr.setContent(clt.getContent());
                atr.setName(clt.getName());
                atr.setUuid(clt.getUuid());
                // This branch of setting the label, type and content is for the attributes that do not have the
                // complete definition stored in the database.
                atr.setLabel(clt.getName());
                atr.setType(AttributeType.DATA);
                atr.setContentType(deriveAttributeContentTypeFromContent(clt.getContent()));
                convertedDefinition.add(atr);
            }
        } else {
            throw new IllegalArgumentException("Invalid argument provided to get Attributes");
        }

        return convertedDefinition;
    }

    public static AttributeContentType deriveAttributeContentTypeFromContent(List<BaseAttributeContent> content) {
        if (content == null || content.isEmpty() || content.get(0).getData() instanceof LinkedHashMap) {
            return AttributeContentType.OBJECT;
        }
        try {
            return AttributeContentType.fromClass(content.get(0).getClass());
        } catch (IllegalArgumentException e) {
            logger.warn("Unable to calculate the content type for the content");
            return AttributeContentType.OBJECT;
        }
    }

    public static List<BaseAttributeContent> convertContentItemsFromObject(Object object) {
        return ATTRIBUTES_OBJECT_MAPPER.convertValue(object, new TypeReference<>() {
        });
    }

    public static List<BaseAttributeContent<?>> createAttributeContentFromString(AttributeContentType attributeContentType, List<String> values) {
        if (!attributeContentType.isFilterByData()) {
            return null;
        }

        try {
            List<BaseAttributeContent<?>> contentItems = new ArrayList<>();
            switch (attributeContentType) {
                case STRING -> {
                    values.forEach(v -> contentItems.add(new StringAttributeContent(v)));
                }
                case TEXT -> {
                    values.forEach(v -> contentItems.add(new TextAttributeContent(v)));
                }
                case INTEGER -> {
                    values.forEach(v -> contentItems.add(new IntegerAttributeContent(Integer.valueOf(v))));
                }
                case FLOAT -> {
                    values.forEach(v -> contentItems.add(new FloatAttributeContent(Float.parseFloat(v))));
                }
                case BOOLEAN -> {
                    values.forEach(v -> contentItems.add(new BooleanAttributeContent(Boolean.valueOf(v))));
                }
                case DATE -> {
                    values.forEach(v -> contentItems.add(new DateAttributeContent(LocalDate.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd")))));
                }
                case TIME -> {
                    values.forEach(v -> contentItems.add(new TimeAttributeContent(LocalTime.parse(v, DateTimeFormatter.ofPattern("HH:mm:ss")))));
                }
                case DATETIME -> {
                    values.forEach(v -> contentItems.add(new DateTimeAttributeContent(ZonedDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")))));
                }
                default -> throw new IllegalStateException("Unexpected content type to parse from string: " + attributeContentType);
            }

            return contentItems;
        } catch (Exception e) {
            logger.debug("Cannot create content items of content type {} from string '{}'", attributeContentType, String.join(", ", values));
            return null;
        }
    }

    public static <T> List<T> getAttributeContentValue(String attributeName, List<?> attributes, Class<T> clazz) {
        List<T> content = AttributeDefinitionUtils.getAttributeContent(attributeName, attributes, clazz);
        return content;
    }

    public static <T> T getSingleItemAttributeContentValue(String attributeName, List<?> attributes, Class<T> clazz) {
        List<T> content = AttributeDefinitionUtils.getAttributeContent(attributeName, attributes, clazz);
        if (content != null && !content.isEmpty()) {
            return content.get(0);
        }
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> getObjectAttributeContentData(String attributeName, List<?> attributes, Class<T> clazz) {
        List<ObjectAttributeContent> content = AttributeDefinitionUtils.getAttributeContent(attributeName, attributes, ObjectAttributeContent.class);
        List<T> response = new ArrayList<>();
        if (content != null) {
            for (ObjectAttributeContent intContent : content) {
                response.add(ATTRIBUTES_OBJECT_MAPPER.convertValue(intContent.getData(), clazz));
            }
            return response;
        }
        return null;
    }

    public static <T> List<T> getAttributeContentValueList(String attributeName, List<?> attributes, Class<?> clazz) {
        // TODO: validation that the attribute is multiSelect, if it make sense, because the request attribute can be without this flag
        List<?> list = getAttributeContent(attributeName, attributes, false);
        if (list != null) {
            List<T> listContent = new ArrayList<>();
            for (Object item : list) {
                BaseAttributeContent ac = (BaseAttributeContent) ATTRIBUTES_OBJECT_MAPPER.convertValue(item, clazz);
                listContent.add((T) ac.getData());
            }
            return listContent;
        }
        return null;
    }

    public static <T> List<T> getObjectAttributeContentDataList(String attributeName, List<?> attributes, Class<?> clazz) {
        // TODO: validation that the attribute is multiSelect, if it make sense, because the request attribute can be without this flag
        List<?> list = getAttributeContent(attributeName, attributes, false);
        if (list != null) {
            List<T> listContent = new ArrayList<>();
            for (Object item : list) {
                ObjectAttributeContent ac = ATTRIBUTES_OBJECT_MAPPER.convertValue(item, ObjectAttributeContent.class);
                listContent.add((T) ATTRIBUTES_OBJECT_MAPPER.convertValue(ac.getData(), clazz));
            }
            return listContent;
        }
        return null;
    }

    /**
     * Function return true if the attributes are equal. And returns false if the attribute are not equal
     *
     * @param requestAttributes List of request attribute DTOs
     * @param attributes        List of attribute definitions
     * @return True if attribute is equal and false if attribute is not equal
     */
    public static boolean checkAttributeEquality(List<RequestAttributeDto> requestAttributes, List<DataAttribute> attributes) {
        for (RequestAttributeDto requestAttribute : requestAttributes) {
            DataAttribute attribute = getAttributeDefinition(requestAttribute.getName(), attributes);
            if (attribute == null) return false;

            var attributeContent = getAttributeContent(requestAttribute.getName(), requestAttributes, attribute.getContentType().getContentClass());
            if (attributeContent == null) return false;

            if (!attributeContent.equals(getAttributeContent(requestAttribute.getName(), attributes, attribute.getContentType().getContentClass()))) {
                return false;
            }
        }
        return true;
    }
}
