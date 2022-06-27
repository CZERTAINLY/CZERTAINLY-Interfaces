package com.czertainly.core.util;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.*;
import com.czertainly.api.model.common.attribute.*;
import com.czertainly.api.model.common.attribute.content.*;
import com.czertainly.api.model.core.credential.CredentialDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AttributeDefinitionUtils {

    private static final ObjectMapper ATTRIBUTES_OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static AttributeDefinition getAttributeDefinition(String name, List<AttributeDefinition> attributes) {
        return attributes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }

    public static boolean containsAttributeDefinition(String name, List<AttributeDefinition> attributes) {
        AttributeDefinition definition = getAttributeDefinition(name, attributes);
        return definition != null;
    }

    public static <T extends Object> T getRequestAttributes(String name, List<?> attributes) {
        if (attributes.size() == 0) {
            return null;
        }
        if (attributes.get(0) instanceof RequestAttributeDto) {
            List<RequestAttributeDto> reloadedAttributes = (List<RequestAttributeDto>) attributes;
            return (T) reloadedAttributes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
        } else if (attributes.get(0) instanceof AttributeDefinition) {
            List<AttributeDefinition> reloadedAttributes = (List<AttributeDefinition>) attributes;
            return (T) reloadedAttributes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
        }else if (attributes.get(0) instanceof ResponseAttributeDto) {
            List<ResponseAttributeDto> reloadedAttributes = (List<ResponseAttributeDto>) attributes;
            return (T) reloadedAttributes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
        }
        else {
            throw new IllegalArgumentException("Invalid Object to get Attribute value");
        }

    }

    public static boolean containsRequestAttributes(String name, List<RequestAttributeDto> attributes) {
        RequestAttributeDto definition = getRequestAttributes(name, attributes);
        return definition != null;
    }

    public static <T extends Object> T getAttributeContent(String name, List<?> attributes) {
        if (attributes.size() == 0) {
            return null;
        }
        if (attributes.get(0) instanceof RequestAttributeDto) {
            RequestAttributeDto definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            return (T) definition.getContent();
        } else if (attributes.get(0) instanceof AttributeDefinition) {
            AttributeDefinition definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            return (T) definition.getContent();
        } else if (attributes.get(0) instanceof ResponseAttributeDto) {
            ResponseAttributeDto definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            return (T) definition.getContent();
        }else {
            throw new IllegalArgumentException("Invalid Object to get Attribute value");
        }
    }

    public static <T extends Object> T getAttributeContent(String name, List<?> attributes, Class<T> clazz) {
        if (attributes.size() == 0) {
            return null;
        }
        if (attributes.get(0) instanceof RequestAttributeDto) {
            RequestAttributeDto definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(definition.getContent(), clazz);
        } else if (attributes.get(0) instanceof AttributeDefinition) {
            AttributeDefinition definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(definition.getContent(), clazz);
        } else if (attributes.get(0) instanceof ResponseAttributeDto) {
            ResponseAttributeDto definition = getRequestAttributes(name, attributes);
            if (definition == null || definition.getContent() == null) {
                return null;
            }
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(definition.getContent(), clazz);
        }else {
            throw new IllegalArgumentException("Invalid Object to get Attribute value");
        }
    }

    public static NameAndIdDto getNameAndIdData(String name, List<RequestAttributeDto> attributes) {
        Object content = getAttributeContent(name, attributes);

        if (!(content instanceof Map)) {
            throw new IllegalArgumentException("Could not get NameAndId value. Attribute has wrong value: " + content);
        }

        Map<?, ?> valueMap = (Map<?, ?>) content;
        if (valueMap.containsKey("value") && valueMap.containsKey("data")) {
            Object nameAndIdData = valueMap.get("data");
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(nameAndIdData, NameAndIdDto.class);
        } else {
            throw new IllegalArgumentException("Could not get NameAndId value. Attribute has wrong value: " + content);
        }
    }

    public static NameAndUuidDto getNameAndUuidData(String name, List<RequestAttributeDto> attributes) {
        Object content = getAttributeContent(name, attributes);

        if (!(content instanceof Map)) {
            throw new IllegalArgumentException("Could not get NameAndUuid value. Attribute has wrong value: " + content);
        }

        Map<?, ?> valueMap = (Map<?, ?>) content;
        if (valueMap.containsKey("value") && valueMap.containsKey("data")) {
            Object nameAndUuidData = valueMap.get("data");
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(nameAndUuidData, NameAndUuidDto.class);
        } else {
            throw new IllegalArgumentException("Could not get NameAndUuid value. Attribute has wrong value: " + content);
        }
    }

    public static CredentialDto getCredentialContent(String name, List<RequestAttributeDto> attributes) {
        Object content = getAttributeContent(name, attributes);

        if (!(content instanceof Map)) {
            throw new IllegalArgumentException("Could not get Credential value. Attribute has wrong value: " + content);
        }

        Map<?, ?> valueMap = (Map<?, ?>) content;
        if (valueMap.containsKey("value") && valueMap.containsKey("data")) {
            Object credentialData = valueMap.get("data");
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(credentialData, CredentialDto.class);
        } else {
            throw new IllegalArgumentException("Could not get Credential value. Attribute has wrong value: " + content);
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

    public static List<AttributeDefinition> deserialize(String attributesJson) {
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

    public static List<AttributeDefinition> mergeAttributes(List<AttributeDefinition> definitions, List<RequestAttributeDto> attributes) throws ValidationException {
        if (definitions == null || attributes == null) {
            return List.of();
        }

        List<AttributeDefinition> attributeDefinitions = clientAttributeConverter(attributes);
        return attributeDefinitions.stream()
                .map(a -> {
                    AttributeDefinition definition = getAttributeDefinition(a.getName(), definitions);
                    if (definition == null) {
                        return a;
                    }

                    AttributeDefinition extended = new AttributeDefinition(definition);
                    extended.setContent(a.getContent());
                    return extended;
                })
                .collect(Collectors.toList());
    }

    public static void validateAttributes(List<AttributeDefinition> definitions, List<RequestAttributeDto> attributes) throws ValidationException {
        List<ValidationError> errors = new ArrayList<>();

        // If attribute identified by id not in definitions - throw error
        for (RequestAttributeDto attribute : attributes) {
            if (!containsAttributeDefinition(attribute.getName(), definitions)) {
                errors.add(ValidationError.create("Attribute {} not supported.", attribute.getName()));
            }
        }

        for (AttributeDefinition definition : definitions) {
            RequestAttributeDto attribute = getRequestAttributes(definition.getName(), attributes);

            if (attribute == null) {
                if (definition.isRequired()) {
                    errors.add(ValidationError.create("Required attribute {} not found.", definition.getName()));
                }
                continue; // skip other validations
            }

            Object attributeContent = attribute.getContent();

            if (definition.isRequired() && attributeContent == null) {
                errors.add(ValidationError.create("Value of required attribute {} not set.", definition.getName()));
                continue; // required attribute has no value, skip other validations
            }

            if (definition.isReadOnly()) {
                Object definitionContent = definition.getContent();
                if (definitionContent == null || !definitionContent.equals(attributeContent)) {
                    errors.add(ValidationError.create(
                            "Wrong value of read only attribute {}. Definition value = {} and attribute value = {}.",
                            definition.getName(),
                            definitionContent,
                            attributeContent));
                }
            }

            validateAttributeContent(definition, attribute, errors);

            if (AttributeType.STRING.equals(definition.getType())
                    && definition.getValidationRegex() != null) {
                Pattern pattern;
                try {
                    pattern = Pattern.compile(definition.getValidationRegex());
                    BaseAttributeContent<String> content = ATTRIBUTES_OBJECT_MAPPER.convertValue(attributeContent, BaseAttributeContent.class);
                    Matcher matcher = pattern.matcher(content.getValue());
                    if (!matcher.matches()) {
                        errors.add(ValidationError.create(
                                "Value {} of attribute {} doesn't match regex {}",
                                attributeContent,
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

    private static void validateAttributeContent(AttributeDefinition definition, RequestAttributeDto attribute, List<ValidationError> errors) {

        if (definition.getType() == null) {
            errors.add(ValidationError.create("Type of attribute definition {} not set.", definition.getName()));
        }

        Object attributeContent = attribute.getContent();
        if (!definition.isMultiSelect()) {
            List<Object> attributeContentList = new ArrayList<Object>();
            attributeContentList.add(attributeContent);
            attributeContent = attributeContentList;
        }

        // TODO: checking all items in the list for the type

        boolean wrongValue = false;
        try {
            for ( Object baseAttributeContent : (List<Object>) attributeContent) {
                switch (definition.getType()) {
                    case STRING:
                    case INTEGER:
                    case SECRET:
                    case BOOLEAN:
                    case FLOAT:
                    case TEXT:
                        BaseAttributeContent<?> stringBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, BaseAttributeContent.class);
                        if (stringBaseAttributeContent.getValue() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", definition.getName(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case FILE:
                        FileAttributeContent fileBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, FileAttributeContent.class);
                        if (fileBaseAttributeContent.getValue() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", definition.getName(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        Base64.getDecoder().decode(fileBaseAttributeContent.getValue());
                        break;
                    case CREDENTIAL:
                        JsonAttributeContent credentialBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, JsonAttributeContent.class);
                        if (credentialBaseAttributeContent.getValue() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", definition.getName(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        CredentialDto credentialDto = ATTRIBUTES_OBJECT_MAPPER.convertValue(credentialBaseAttributeContent.getData(), CredentialDto.class);
                        if (credentialDto == null) {
                            errors.add(ValidationError.create("Wrong data of Attribute {} {}.", definition.getName(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case DATE:
                        DateAttributeContent dateBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, DateAttributeContent.class);
                        if (dateBaseAttributeContent.getValue() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", definition.getName(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case JSON:
                        JsonAttributeContent jsonBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, JsonAttributeContent.class);
                        if (jsonBaseAttributeContent.getValue() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", definition.getName(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case TIME:
                        TimeAttributeContent timeBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, TimeAttributeContent.class);
                        if (timeBaseAttributeContent.getValue() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", definition.getName(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case DATETIME:
                        DateTimeAttributeContent dateTimeBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, DateTimeAttributeContent.class);
                        if (dateTimeBaseAttributeContent.getValue() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", definition.getName(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    default:
                        errors.add(ValidationError.create("Unknown type of Attribute definition {} {}.", definition.getName(), definition.getType()));
                        break;
                }
            }
        } catch (Exception e) {
            wrongValue = true;
        }

        if (wrongValue) {
            errors.add(ValidationError.create("Attribute {} of type {} has wrong value.", definition.getName(), definition.getType()));
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
                                if (request.getPathVariables() == null || request.getPathVariables().isEmpty()) {
                                    errors.add(ValidationError.create(
                                            "Callback path variables not set, but mapping require it {}", mapping));
                                    break;
                                }

                                if (request.getPathVariables().get(mapping.getTo()) == null) {
                                    errors.add(ValidationError.create(
                                            "Callback path variable {} not set, but mapping require it {}", mapping.getTo(), mapping));
                                    break;
                                }
                                if (AttributeType.CREDENTIAL.equals(mapping.getAttributeType())) {
                                    errors.add(ValidationError.create(
                                            "Callback mapping {} invalid. Type {} not allowed for path variable", mapping, mapping.getAttributeType()));
                                    break;
                                }
                                break;
                            case REQUEST_PARAMETER:
                                if (request.getQueryParameters() == null || request.getQueryParameters().isEmpty()) {
                                    errors.add(ValidationError.create(
                                            "Callback query parameters not set, but mapping require it {}", mapping));
                                    break;
                                }

                                if (request.getQueryParameters().get(mapping.getTo()) == null) {
                                    errors.add(ValidationError.create(
                                            "Callback query parameters {} not set, but mapping require it {}", mapping.getTo(), mapping));
                                    break;
                                }
                                if (AttributeType.CREDENTIAL.equals(mapping.getAttributeType())) {
                                    errors.add(ValidationError.create(
                                            "Callback mapping {} invalid. Type {} not allowed for query parameter", mapping, mapping.getAttributeType()));
                                    break;
                                }
                                break;
                            case BODY:
                                if (request.getRequestBody() == null || request.getRequestBody().isEmpty()) {
                                    errors.add(ValidationError.create(
                                            "Callback request body not set, but mapping require it {}", mapping));
                                    break;
                                }

                                if (request.getRequestBody().get(mapping.getTo()) == null) {
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

    public static List<RequestAttributeDto> createAttributes(String name, Object value) {
        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setUuid(UUID.randomUUID().toString());
        attribute.setName(name);
        attribute.setContent(value);
        return createAttributes(attribute);
    }

    public static List<RequestAttributeDto> createAttributes(RequestAttributeDto attribute) {
        List<RequestAttributeDto> attributes = createAttributes();
        attributes.add(attribute);

        return attributes;
    }

    public static List<RequestAttributeDto> createAttributes() {
        return new ArrayList<>();
    }

    public static List<AttributeDefinition> clientAttributeConverter(List<RequestAttributeDto> attributes) {
        if (attributes == null) {
            return new ArrayList<>();
        }
        List<AttributeDefinition> convertedDefinition = new ArrayList<>();
        for (RequestAttributeDto clt : attributes) {
            AttributeDefinition atr = new AttributeDefinition();
            atr.setContent(clt.getContent());
            atr.setName(clt.getName());
            atr.setUuid(clt.getUuid());
            convertedDefinition.add(atr);
        }
        return convertedDefinition;
    }

    public static List<AttributeDefinition> responseAttributeConverter(List<ResponseAttributeDto> attributes) {
        if (attributes == null) {
            return new ArrayList<>();
        }
        List<AttributeDefinition> convertedDefinition = new ArrayList<>();
        for (ResponseAttributeDto clt : attributes) {
            AttributeDefinition atr = new AttributeDefinition();
            atr.setContent(clt.getContent());
            atr.setName(clt.getName());
            atr.setUuid(clt.getUuid());
            atr.setType(clt.getType());
            atr.setLabel(clt.getLabel());
            convertedDefinition.add(atr);
        }
        return convertedDefinition;
    }

    public static List<RequestAttributeDto> getClientAttributes(List<?> attributes) {
        if (attributes == null || attributes.size() == 0) {
            return new ArrayList<>();
        }
        List<RequestAttributeDto> convertedDefinition = new ArrayList<>();
        if (attributes.get(0) instanceof AttributeDefinition) {
            List<AttributeDefinition> itrAttributes = (List<AttributeDefinition>) attributes;
            for (AttributeDefinition clt : itrAttributes) {
                RequestAttributeDto atr = new RequestAttributeDto();
                atr.setContent(clt.getContent());
                atr.setName(clt.getName());
                atr.setUuid(clt.getUuid());
                convertedDefinition.add(atr);
        }} else if (attributes.get(0) instanceof ResponseAttributeDto) {
            List<ResponseAttributeDto> itrAttributes = (List<ResponseAttributeDto>) attributes;
            for (ResponseAttributeDto clt : itrAttributes) {
                RequestAttributeDto atr = new RequestAttributeDto();
                atr.setContent(clt.getContent());
                atr.setName(clt.getName());
                atr.setUuid(clt.getUuid());
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
        if (attributes.get(0) instanceof AttributeDefinition) {
            List<AttributeDefinition> itrAttributes = (List<AttributeDefinition>) attributes;
            for (AttributeDefinition clt : itrAttributes) {
                ResponseAttributeDto atr = new ResponseAttributeDto();
                atr.setContent(clt.getContent());
                atr.setName(clt.getName());
                atr.setUuid(clt.getUuid());
                atr.setLabel(clt.getLabel());
                atr.setType(clt.getType());
                convertedDefinition.add(atr);
            }
        } else if (attributes.get(0) instanceof RequestAttributeDto) {
            List<RequestAttributeDto> itrAttributes = (List<RequestAttributeDto>) attributes;
            for (RequestAttributeDto clt : itrAttributes) {
                ResponseAttributeDto atr = new ResponseAttributeDto();
                atr.setContent(clt.getContent());
                atr.setName(clt.getName());
                atr.setUuid(clt.getUuid());
                convertedDefinition.add(atr);
            }
        } else {
            throw new IllegalArgumentException("Invalid argument provided to get Attributes");
        }

        return convertedDefinition;
    }

    public static <T> T getAttributeContentValue(String attributeName, List<?> attributes, Class<?> clazz) {
        AttributeContent content = (AttributeContent) AttributeDefinitionUtils.getAttributeContent(attributeName, attributes, clazz);
        if (content != null) {
            return content.getValue();
        }
        return null;
    }

    public static <T> T getJsonAttributeContentData(String attributeName, List<?> attributes, Class<?> clazz) {
        JsonAttributeContent content = AttributeDefinitionUtils.getAttributeContent(attributeName, attributes, JsonAttributeContent.class);
        if (content != null) {
            return (T) ATTRIBUTES_OBJECT_MAPPER.convertValue(content.getData(), clazz);
        }
        return null;
    }

    public static <T> List<T> getAttributeContentValueList(String attributeName, List<?> attributes, Class<?> clazz) {
        // TODO: validation that the attribute is multiSelect, if it make sense, because the request attribute can be without this flag
        List<?> list = getAttributeContent(attributeName, attributes);
        if (list != null) {
            List<T> listContent = new ArrayList<>();
            for (Object item : list) {
                AttributeContent ac = (AttributeContent) ATTRIBUTES_OBJECT_MAPPER.convertValue(item, clazz);
                listContent.add(ac.getValue());
            }
            return listContent;
        }
        return null;
    }

    public static <T> List<T> getJsonAttributeContentDataList(String attributeName, List<?> attributes, Class<?> clazz) {
        // TODO: validation that the attribute is multiSelect, if it make sense, because the request attribute can be without this flag
        List<?> list = getAttributeContent(attributeName, attributes);
        if (list != null) {
            List<T> listContent = new ArrayList<>();
            for (Object item : list) {
                JsonAttributeContent ac = ATTRIBUTES_OBJECT_MAPPER.convertValue(item, JsonAttributeContent.class);
                listContent.add((T) ATTRIBUTES_OBJECT_MAPPER.convertValue(ac.getData(), clazz));
            }
            return listContent;
        }
        return null;
    }
}
