package com.czertainly.core.util;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndIdDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.AttributeProperties;
import com.czertainly.api.model.common.attribute.BaseAttribute;
import com.czertainly.api.model.common.attribute.DataAttribute;
import com.czertainly.api.model.common.attribute.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.callback.AttributeCallbackMapping;
import com.czertainly.api.model.common.attribute.callback.AttributeValueTarget;
import com.czertainly.api.model.common.attribute.callback.RequestAttributeCallback;
import com.czertainly.api.model.common.attribute.content.*;
import com.czertainly.api.model.core.credential.CredentialDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AttributeDefinitionUtils {

    private static final ObjectMapper ATTRIBUTES_OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static BaseAttribute getAttributeDefinition(String name, List<DataAttribute> attributes) {
        return attributes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }

    public static boolean containsAttributeDefinition(String name, List<DataAttribute> attributes) {
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
        } else if (attributes.get(0) instanceof BaseAttribute) {
            BaseAttribute definition = getRequestAttributes(name, attributes);
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
        ObjectAttributeContent converted = getObjectAttributeContentData(name, attributes, ObjectAttributeContent.class).get(0);
        return ATTRIBUTES_OBJECT_MAPPER.convertValue(converted.getData(), NameAndIdDto.class);
    }

    public static NameAndUuidDto getNameAndUuidData(String name, List<RequestAttributeDto> attributes) {
        ObjectAttributeContent converted = getObjectAttributeContentData(name, attributes, ObjectAttributeContent.class).get(0);
        return ATTRIBUTES_OBJECT_MAPPER.convertValue(converted.getData(), NameAndUuidDto.class);
    }

    public static CredentialDto getCredentialContent(String name, List<RequestAttributeDto> attributes) {
        return getObjectAttributeContentData(name, attributes, CredentialAttributeContent.class).get(0).getData();
    }

    public static String serialize(List<DataAttribute> attributes) {
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

    public static List<DataAttribute> deserialize(String attributesJson) {
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

    public static List<DataAttribute> mergeAttributes(List<DataAttribute> definitions, List<RequestAttributeDto> attributes) throws ValidationException {
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

    public static void validateAttributes(List<DataAttribute> definitions, List<RequestAttributeDto> attributes) throws ValidationException {
        List<ValidationError> errors = new ArrayList<>();

        // If attribute identified by id not in definitions - throw error
        for (RequestAttributeDto attribute : attributes) {
            if (!containsAttributeDefinition(attribute.getName(), definitions)) {
                errors.add(ValidationError.create("Attribute {} not supported.", attribute.getName()));
            }
        }

        for (DataAttribute definition : definitions) {
            RequestAttributeDto attribute = getRequestAttributes(definition.getName(), attributes);
            Boolean isRequired = false;
            AttributeProperties properties = definition.getProperties();
            if(properties != null) {
                isRequired = definition.getProperties().isRequired();
            }
            if(properties == null){
                properties = new AttributeProperties();
            }
            if (attribute == null) {
                if (isRequired) {
                    errors.add(ValidationError.create("Required attribute {} not found.", properties.getLabel()));
                }
                continue; // skip other validations
            }

            Object attributeContent = attribute.getContent();

            if (isRequired && attributeContent == null) {
                errors.add(ValidationError.create("Value of required attribute {} not set.", properties.getLabel()));
                continue; // required attribute has no value, skip other validations
            }

            if (properties != null && properties.isReadOnly()) {
                Object definitionContent = definition.getContent();
                if (definitionContent == null || !definitionContent.equals(attributeContent)) {
                    errors.add(ValidationError.create(
                            "Wrong value of read only attribute {}. Definition value = {} and attribute value = {}.",
                            properties.getLabel(),
                            definitionContent,
                            attributeContent));
                }
            }

            validateAttributeContent(definition, attribute, errors);

//            if (AttributeContentType.STRING.equals(definition.getType())
//                    && definition.getValidationRegex() != null) {
//                Pattern pattern;
//                try {
//                    pattern = Pattern.compile(definition.getValidationRegex());
//                    BaseAttributeContent<String> content = ATTRIBUTES_OBJECT_MAPPER.convertValue(attributeContent, BaseAttributeContent.class);
//                    Matcher matcher = pattern.matcher(content.getValue());
//                    if (!matcher.matches()) {
//                        errors.add(ValidationError.create(
//                                "Value {} of attribute {} doesn't match regex {}",
//                                attributeContent,
//                                definition.getLabel(),
//                                definition.getValidationRegex()));
//                    }
//                } catch (Exception e) {
//                    errors.add(ValidationError.create(
//                            "Could not validate value of field {} due to error {}",
//                            definition.getLabel(),
//                            ExceptionUtils.getRootCauseMessage(e)));
//                }
//            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Attributes validation failed.", errors);
        }
    }

    private static void validateAttributeContent(DataAttribute definition, RequestAttributeDto attribute, List<ValidationError> errors) {
        AttributeProperties properties = definition.getProperties();
        if(properties == null){
            properties = new AttributeProperties();
        }
        if (definition.getType() == null) {
            errors.add(ValidationError.create("Type of attribute definition {} not set.", properties.getLabel()));
        }

        Object attributeContent = attribute.getContent();
//        if (properties != null && !properties.isMulti()) {
//            List<Object> attributeContentList = new ArrayList<Object>();
//            attributeContentList.add(attributeContent);
//            attributeContent = attributeContentList;
//        }

        // TODO: checking all items in the list for the type

        boolean wrongValue = false;
        try {
            for (Object baseAttributeContent : (List<Object>) attributeContent) {
                switch (definition.getContentType()) {
                    case STRING:
                        BaseAttributeContent<?> stringBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, StringAttributeContent.class);
                        if (stringBaseAttributeContent.getData() == null || AttributeContentType.getClass(definition.getContentType()) == null || !stringBaseAttributeContent.getData().getClass().isAssignableFrom(AttributeContentType.getClass(definition.getContentType()))) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case INTEGER:
                        BaseAttributeContent<?> integerBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, IntegerAttributeContent.class);
                        if (integerBaseAttributeContent.getData() == null || AttributeContentType.getClass(definition.getContentType()) == null || !integerBaseAttributeContent.getData().getClass().isAssignableFrom(AttributeContentType.getClass(definition.getContentType()))) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case SECRET:
                        BaseAttributeContent<?> secretBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, SecretAttributeContent.class);
                        if (secretBaseAttributeContent.getData() == null || AttributeContentType.getClass(definition.getContentType()) == null || !secretBaseAttributeContent.getData().getClass().isAssignableFrom(AttributeContentType.getClass(definition.getContentType()))) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case BOOLEAN:
                        BaseAttributeContent<?> boolBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, BooleanAttributeContent.class);
                        if (boolBaseAttributeContent.getData() == null || AttributeContentType.getClass(definition.getContentType()) == null || !boolBaseAttributeContent.getData().getClass().isAssignableFrom(AttributeContentType.getClass(definition.getContentType()))) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case FLOAT:
                        BaseAttributeContent<?> floatBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, FloatAttributeContent.class);
                        if (floatBaseAttributeContent.getData() == null || AttributeContentType.getClass(definition.getContentType()) == null || !floatBaseAttributeContent.getData().getClass().isAssignableFrom(AttributeContentType.getClass(definition.getContentType()))) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case TEXT:
                        BaseAttributeContent<?> textBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, TextAttributeContent.class);
                        if (textBaseAttributeContent.getData() == null || AttributeContentType.getClass(definition.getContentType()) == null || !textBaseAttributeContent.getData().getClass().isAssignableFrom(AttributeContentType.getClass(definition.getContentType()))) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case FILE:
                        FileAttributeContent fileBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, FileAttributeContent.class);
                        if (fileBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        Base64.getDecoder().decode(fileBaseAttributeContent.getData().getContent());
                        break;
                    case CREDENTIAL:
                        CredentialAttributeContent credentialBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, CredentialAttributeContent.class);
                        if (credentialBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        CredentialDto credentialDto = ATTRIBUTES_OBJECT_MAPPER.convertValue(credentialBaseAttributeContent.getData(), CredentialDto.class);
                        if (credentialDto == null) {
                            errors.add(ValidationError.create("Wrong data of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case DATE:
                        DateAttributeContent dateBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, DateAttributeContent.class);
                        if (dateBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case OBJECT:
                        ObjectAttributeContent jsonBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, ObjectAttributeContent.class);
                        if (jsonBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case TIME:
                        TimeAttributeContent timeBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, TimeAttributeContent.class);
                        if (timeBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    case DATETIME:
                        DateTimeAttributeContent dateTimeBaseAttributeContent = ATTRIBUTES_OBJECT_MAPPER.convertValue(baseAttributeContent, DateTimeAttributeContent.class);
                        if (dateTimeBaseAttributeContent.getData() == null) {
                            errors.add(ValidationError.create("Wrong value of Attribute {} {}.", properties.getLabel(), definition.getType()));
                            wrongValue = true;
                            break;
                        }
                        break;
                    default:
                        errors.add(ValidationError.create("Unknown type of Attribute definition {} {}.", properties.getLabel(), definition.getType()));
                        break;
                }
            }
        } catch (Exception e) {
            wrongValue = true;
        }

        if (wrongValue) {
            errors.add(ValidationError.create("Attribute {} of type {} has wrong value.", properties.getLabel(), definition.getType()));
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
                                if (AttributeContentType.CREDENTIAL.equals(mapping.getAttributeType())) {
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
                                if (AttributeContentType.CREDENTIAL.equals(mapping.getAttributeType())) {
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

    public static List<RequestAttributeDto> createAttributes(String name, List<BaseAttributeContent> value) {
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
            atr.setContentType(clt.getType());
            AttributeProperties properties = new AttributeProperties();
            properties.setLabel(clt.getLabel());
            atr.setProperties(properties);
            convertedDefinition.add(atr);
        }
        return convertedDefinition;
    }

    public static List<RequestAttributeDto> getClientAttributes(List<?> attributes) {
        if (attributes == null || attributes.size() == 0) {
            return new ArrayList<>();
        }
        List<RequestAttributeDto> convertedDefinition = new ArrayList<>();
        if (attributes.get(0) instanceof DataAttribute) {
            List<DataAttribute> itrAttributes = (List<DataAttribute>) attributes;
            for (DataAttribute clt : itrAttributes) {
                RequestAttributeDto atr = new RequestAttributeDto();
                atr.setContent(clt.getContent());
                atr.setName(clt.getName());
                atr.setUuid(clt.getUuid());
                convertedDefinition.add(atr);
            }
        } else if (attributes.get(0) instanceof ResponseAttributeDto) {
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
        if (attributes.get(0) instanceof DataAttribute) {
            List<DataAttribute> itrAttributes = (List<DataAttribute>) attributes;
            for (DataAttribute clt : itrAttributes) {
                ResponseAttributeDto atr = new ResponseAttributeDto();
                atr.setContent(clt.getContent());
                atr.setName(clt.getName());
                atr.setUuid(clt.getUuid());
                atr.setLabel(clt.getProperties().getLabel());
                atr.setType(clt.getContentType());
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

    public static <T> List<T> getAttributeContentValue(String attributeName, List<?> attributes, Class<T> clazz) {
        List<T> content = AttributeDefinitionUtils.getAttributeContent(attributeName, attributes, clazz);
        if (content != null) {
            return content;
        }
        return null;
    }

    public static <T> List<T> getObjectAttributeContentData(String attributeName, List<?> attributes, Class<T> clazz) {
        List<ObjectAttributeContent> content = AttributeDefinitionUtils.getAttributeContent(attributeName, attributes, ObjectAttributeContent.class);
        if (content != null) {
            return ATTRIBUTES_OBJECT_MAPPER.convertValue(content, ATTRIBUTES_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        }
        return null;
    }

    public static <T> List<T> getAttributeContentValueList(String attributeName, List<?> attributes, Class<?> clazz) {
        // TODO: validation that the attribute is multiSelect, if it make sense, because the request attribute can be without this flag
        List<?> list = getAttributeContent(attributeName, attributes);
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
        List<?> list = getAttributeContent(attributeName, attributes);
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
}
