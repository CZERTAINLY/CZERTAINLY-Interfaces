package com.czertainly.core.util;

import com.czertainly.api.model.common.attribute.v1.AttributeDefinition;
import com.czertainly.api.model.common.attribute.v1.AttributeType;
import com.czertainly.api.model.common.attribute.v1.content.JsonAttributeContent;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallbackMapping;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeValueTarget;
import com.czertainly.api.model.common.attribute.v2.constraint.BaseAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.constraint.RegexpAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v2.content.data.CodeBlockAttributeContentData;
import com.czertainly.api.model.common.attribute.v2.content.data.CredentialAttributeContentData;
import com.czertainly.api.model.common.attribute.v2.content.data.FileAttributeContentData;
import com.czertainly.api.model.common.attribute.v2.content.data.SecretAttributeContentData;
import com.czertainly.api.model.common.attribute.v2.properties.DataAttributeProperties;
import com.czertainly.api.model.common.attribute.v2.properties.MetadataAttributeProperties;
import com.czertainly.core.deprecated.AttributeDefinitionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.MimeType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class V2AttributeMigrationUtils {
    private static final Logger logger = LoggerFactory.getLogger(V2AttributeMigrationUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<String> getMigrationCommands(ResultSet rows, String tableName, String columnName, String rowIdentifier) throws SQLException, JsonProcessingException {
        logger.debug("Migrating Table: {}, Column: {}", tableName, columnName);
        List<String> migrationCommands = new ArrayList<>();
        while (rows.next()) {
            // the certificate_location table has composite key
            if (tableName.equals("certificate_location")) {
                logger.debug("Migrating record with Location id: {}, Certificate id: {}", rows.getString("location_uuid"), rows.getString("certificate_uuid"));
            } else {
                logger.debug("Migrating record with is: {}", rows.getString(rowIdentifier));
            }
            List<BaseAttribute> attributeDefinitions = new ArrayList<>();
            List<AttributeDefinition> oldAttributeValue = AttributeDefinitionUtils.deserialize(rows.getString(columnName));
            if (oldAttributeValue == null) {
                continue;
            }
            for (AttributeDefinition item : oldAttributeValue) {
                attributeDefinitions.add(getNewAttributes(item, BaseAttribute.class));
            }
            String updateCommand;
            String serializedAttributes = com.czertainly.core.util.AttributeDefinitionUtils.serialize(attributeDefinitions);
            if (tableName.equals("certificate_location")) {
                updateCommand = "UPDATE " + tableName + " SET " + columnName + " = '" + serializedAttributes.replace("'", "") + "' WHERE location_uuid = '" + rows.getString("location_uuid") + "' AND certificate_uuid = '" + rows.getString("certificate_uuid") + "';";
            } else {
                updateCommand = "UPDATE " + tableName + " SET " + columnName + " = '" + serializedAttributes.replace("'", "") + "' WHERE " + rowIdentifier + " = '" + rows.getString(rowIdentifier) + "';";
            }
            migrationCommands.add(updateCommand);
        }
        return migrationCommands;
    }

    public static <T extends BaseAttribute> T getNewAttributes(AttributeDefinition oldAttribute,Class<T> clazz) {

        //Old Attribute Value to new attribute properties

        DataAttribute attribute = new DataAttribute();

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setList(oldAttribute.isList());
        properties.setMultiSelect(oldAttribute.isMultiSelect());
        properties.setReadOnly(oldAttribute.isReadOnly());
        properties.setRequired(oldAttribute.isRequired());
        properties.setLabel(oldAttribute.getLabel());
        properties.setVisible(oldAttribute.isVisible());
        properties.setGroup(oldAttribute.getGroup());

        attribute.setName(oldAttribute.getName());
        attribute.setUuid(oldAttribute.getUuid());
        attribute.setDescription(oldAttribute.getDescription());
        attribute.setType(com.czertainly.api.model.common.attribute.v2.AttributeType.DATA);
        attribute.setContentType(getAttributeContentType(oldAttribute.getType()));
        attribute.setContent(getAttributeContent(oldAttribute.getType() != null ? oldAttribute.getType() : AttributeType.STRING, oldAttribute.getContent()));
        attribute.setProperties(properties);
        attribute.setAttributeCallback(getAttributeCallback(oldAttribute.getAttributeCallback()));
        attribute.setConstraints(getAttributeConstraint(oldAttribute.getValidationRegex()));
        return (T) attribute;
    }

    private static List<BaseAttributeConstraint> getAttributeConstraint(String regex) {
        if (regex == null) {
            return null;
        }
        return List.of(new RegexpAttributeConstraint("", "", regex));
    }

    private static AttributeCallback getAttributeCallback(com.czertainly.api.model.common.attribute.v1.AttributeCallback oldCallback) {
        if (oldCallback == null) {
            return null;
        }
        AttributeCallback callback = new AttributeCallback();
        callback.setCallbackContext(oldCallback.getCallbackContext());
        callback.setCallbackMethod(oldCallback.getCallbackMethod());
        Set<AttributeCallbackMapping> mappings = new HashSet<>();
        for (com.czertainly.api.model.common.attribute.v1.AttributeCallbackMapping oldMapping : oldCallback.getMappings()) {
            AttributeCallbackMapping mapping = new AttributeCallbackMapping();
            mapping.setAttributeType(com.czertainly.api.model.common.attribute.v2.AttributeType.DATA);
            mapping.setAttributeContentType(getAttributeContentType(oldMapping.getAttributeType()));
            mapping.setFrom(oldMapping.getFrom());
            mapping.setTo(oldMapping.getTo());
            mapping.setTargets(oldMapping.getTargets().stream().map(e -> AttributeValueTarget.findByCode(e.getCode())).collect(Collectors.toSet()));
            mapping.setValue(oldMapping.getValue());
            mappings.add(mapping);
        }
        callback.setMappings(mappings);
        return callback;
    }

    private static AttributeContentType getAttributeContentType(AttributeType type) {
        if (type == null) {
            return null;
        }
        if (type.equals(AttributeType.JSON)) {
            return AttributeContentType.OBJECT;
        }
        return AttributeContentType.fromCode(type.getCode());
    }

    private static List<BaseAttributeContent> getAttributeContent(AttributeType attributeType, Object oldContentData) {
        List<BaseAttributeContent> attributeContents = new ArrayList<>();
        List<com.czertainly.api.model.common.attribute.v1.content.BaseAttributeContent> oldContentListItems = new ArrayList<>();
        if (oldContentData == null) {
            return null;
        }
        if (!(oldContentData instanceof List)) {
            oldContentListItems.add(convertOldContents(attributeType, oldContentData));
        } else {
            ((List<?>) oldContentData).forEach(e -> oldContentListItems.add(convertOldContents(attributeType, e)));
        }
        for (com.czertainly.api.model.common.attribute.v1.content.BaseAttributeContent oldContent : oldContentListItems) {
            switch (attributeType) {
                case STRING:
                    attributeContents.add(new StringAttributeContent(oldContent.getValue().toString(), oldContent.getValue().toString()));
                    break;
                case INTEGER:
                    attributeContents.add(new IntegerAttributeContent(oldContent.getValue().toString(), Integer.parseInt(oldContent.getValue().toString())));
                    break;
                case BOOLEAN:
                    if (oldContent.getValue() instanceof Boolean) {
                        attributeContents.add(new BooleanAttributeContent((Boolean) oldContent.getValue()));
                    } else {
                        String otherValue = oldContent.getValue().toString().toLowerCase();
                        if (otherValue.equals("yes")) {
                            attributeContents.add(new BooleanAttributeContent(true));
                        } else {
                            attributeContents.add(new BooleanAttributeContent(false));
                        }
                    }
                    break;
                case CREDENTIAL:
                    CredentialAttributeContentData credentialDto = new CredentialAttributeContentData();
                    LinkedHashMap credentialData = (LinkedHashMap) ((JsonAttributeContent) oldContent).getData();
                    credentialDto.setName((String) credentialData.get("name"));
                    credentialDto.setUuid((String) credentialData.get("uuid"));
                    credentialDto.setKind((String) credentialData.get("kind"));
                    List<DataAttribute> credentialAttributes = new ArrayList<>();
                    List<AttributeDefinition> oldCredentialAttributeValue = new ArrayList<>();
                    try {
                        oldCredentialAttributeValue = AttributeDefinitionUtils.deserialize(mapper.writeValueAsString(credentialData.get("attributes")));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    if (oldCredentialAttributeValue == null) {
                        oldCredentialAttributeValue = new ArrayList<>();
                    }
                    for (AttributeDefinition item : oldCredentialAttributeValue) {
                        credentialAttributes.add(getNewAttributes(item, DataAttribute.class));
                    }
                    credentialDto.setAttributes(credentialAttributes);
                    attributeContents.add(new CredentialAttributeContent(((JsonAttributeContent) oldContent).getValue(), credentialDto));
                    break;
                case DATE:
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    attributeContents.add(new DateAttributeContent(LocalDate.parse(oldContent.getValue().toString(), df)));
                    break;
                case DATETIME:
                    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                    ZonedDateTime z = ZonedDateTime.parse(oldContent.getValue().toString(), f);
                    attributeContents.add(new DateTimeAttributeContent(z));
                    break;
                case FLOAT:
                    attributeContents.add(new FloatAttributeContent(oldContent.getValue().toString(), (Float) oldContent.getValue()));
                    break;
                case TIME:
                    DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");
                    attributeContents.add(new TimeAttributeContent(LocalTime.parse(oldContent.getValue().toString(), tf)));
                    break;
                case TEXT:
                    attributeContents.add(new TextAttributeContent((String) oldContent.getValue()));
                    break;
                case SECRET:
                    attributeContents.add(new SecretAttributeContent("", new SecretAttributeContentData((String) oldContent.getValue())));
                    break;
                case JSON:
                    attributeContents.add(new ObjectAttributeContent(((JsonAttributeContent) oldContent).getValue(), ((JsonAttributeContent) oldContent).getData()));
                    break;
                case FILE:
                    FileAttributeContentData data = new FileAttributeContentData();
                    com.czertainly.api.model.common.attribute.v1.content.FileAttributeContent oldContentFileData = (com.czertainly.api.model.common.attribute.v1.content.FileAttributeContent) oldContent;
                    data.setContent(oldContentFileData.getValue());
                    data.setFileName(oldContentFileData.getFileName());
                    if (oldContentFileData.getContentType() != null && !oldContentFileData.getContentType().isEmpty())
                        try {
                            data.setMimeType(oldContentFileData.getContentType());
                        } catch (InvalidMimeTypeException e) {
                            //Do nothing
                        }
                    attributeContents.add(new FileAttributeContent(oldContentFileData.getFileName(), data));
                    break;
                case CODEBLOCK:
                    CodeBlockAttributeContentData codeBlockAttributeContentData = (CodeBlockAttributeContentData) oldContent.getValue();
                    attributeContents.add(new CodeBlockAttributeContent("", new CodeBlockAttributeContentData(codeBlockAttributeContentData.getLanguage(), codeBlockAttributeContentData.getCode())));
                    break;
            }
        }
        return attributeContents;
    }

    private static com.czertainly.api.model.common.attribute.v1.content.BaseAttributeContent convertOldContents(AttributeType attributeType, Object oldContentData) {
        if (oldContentData == null) {
            return null;
        }
        if (attributeType.equals(AttributeType.JSON)) {
            return mapper.convertValue(oldContentData, com.czertainly.api.model.common.attribute.v1.content.JsonAttributeContent.class);
        } else if (attributeType.equals(AttributeType.CREDENTIAL)) {
            return mapper.convertValue(oldContentData, com.czertainly.api.model.common.attribute.v1.content.JsonAttributeContent.class);
        } else if (attributeType.equals(AttributeType.FILE)) {
            return mapper.convertValue(oldContentData, com.czertainly.api.model.common.attribute.v1.content.FileAttributeContent.class);
        } else {
            return mapper.convertValue(oldContentData, com.czertainly.api.model.common.attribute.v1.content.BaseAttributeContent.class);
        }
    }

    // All the methods defined below will be used fot Metadata mirations

    public static List<String> getMetadataMigrationCommands(ResultSet rows, String tableName, String columnName, String rowIdentifier) throws SQLException, JsonProcessingException {
        logger.debug("Metadata - Migrating Table: {}, Column: {}", tableName, columnName);
        List<String> migrationCommands = new ArrayList<>();
        while (rows.next()) {
            // the certificate_location table has composite key
            if (tableName.equals("certificate_location")) {
                logger.debug("Metadata - Migrating record with Location id: {}, Certificate id: {}", rows.getString("location_uuid"), rows.getString("certificate_uuid"));
            } else {
                logger.debug("Metadata - Migrating record with is: {}", rows.getString(rowIdentifier));
            }

            List<MetadataAttribute> metadataDefinitions = new ArrayList<>();
            if (rows.getString(columnName) == null) {
                continue;
            }
            Map<String, Object> oldMetadata = mapper.readValue(rows.getString(columnName), new TypeReference<>() {
            });
            if (oldMetadata == null) {
                continue;
            }
            for (Map.Entry<String, Object> entry : oldMetadata.entrySet()) {
                metadataDefinitions.add(getMetadataAttribute(entry));
            }
            String updateCommand;
            String serializedMetadata = com.czertainly.core.util.AttributeDefinitionUtils.serialize(metadataDefinitions);
            if (tableName.equals("certificate_location")) {
                updateCommand = "UPDATE " + tableName + " SET " + columnName + " = '" + serializedMetadata.replace("'", "") + "' WHERE location_uuid = '" + rows.getString("location_uuid") + "' AND certificate_uuid = '" + rows.getString("certificate_uuid") + "';";
            } else {
                updateCommand = "UPDATE " + tableName + " SET " + columnName + " = '" + serializedMetadata.replace("'", "") + "' WHERE " + rowIdentifier + " = '" + rows.getString(rowIdentifier) + "';";
            }
            migrationCommands.add(updateCommand);
        }
        return migrationCommands;
    }

    public static List<MetadataAttribute> getMetadataMigrationAttributes(String metadata) throws SQLException, JsonProcessingException {
        List<MetadataAttribute> metadataDefinitions = new ArrayList<>();
        if (metadata == null) {
            return null;
        }
        Map<String, Object> oldMetadata = mapper.readValue(metadata, new TypeReference<>() {
        });
        if (oldMetadata == null) {
            return null;
        }
        for (Map.Entry<String, Object> entry : oldMetadata.entrySet()) {
            metadataDefinitions.add(getMetadataAttribute(entry));
        }
        return metadataDefinitions;
    }

    public static MetadataAttribute getMetadataAttribute(Map.Entry<String, Object> oldMetadata) {
        MetadataAttribute attribute = new MetadataAttribute();
        attribute.setUuid(null);
        attribute.setName(oldMetadata.getKey());

        //Attribute Properties
        MetadataAttributeProperties attributeProperties = new MetadataAttributeProperties();
        attributeProperties.setLabel(camelToHumanForm(oldMetadata.getKey()));
        attributeProperties.setVisible(true);
        attribute.setProperties(attributeProperties);
        attribute.setContentType(getMetadataAttributeType(oldMetadata.getValue()));

        attribute.setContent(getMetadataAttributeValue(oldMetadata.getValue()));
        attribute.setType(com.czertainly.api.model.common.attribute.v2.AttributeType.META);
        return attribute;
    }

    private static AttributeContentType getMetadataAttributeType(Object value) {
        if (value instanceof String) {
            return AttributeContentType.STRING;
        } else if (value instanceof Integer) {
            return AttributeContentType.INTEGER;
        } else if (value instanceof Float) {
            return AttributeContentType.FLOAT;
        } else if (value instanceof Boolean) {
            return AttributeContentType.BOOLEAN;
        } else {
            return AttributeContentType.OBJECT;
        }
    }

    private static List<BaseAttributeContent> getMetadataAttributeValue(Object value) {
        if (value instanceof String) {
            String metadataValue = (String) value;
            return List.of(new StringAttributeContent(metadataValue, metadataValue));
        } else if (value instanceof Integer) {
            Integer metadataValue = (Integer) value;
            return List.of(new IntegerAttributeContent(metadataValue.toString(), metadataValue));
        } else if (value instanceof Float) {
            Float metadataValue = (Float) value;
            return List.of(new FloatAttributeContent(metadataValue.toString(), metadataValue));
        } else if (value instanceof Boolean) {
            Boolean metadataValue = (Boolean) value;
            return List.of(new BooleanAttributeContent(metadataValue ? "Yes" : "No", metadataValue));
        } else {
            return List.of(new ObjectAttributeContent(value));
        }
    }


    public static String camelToHumanForm(String word) {
        return StringUtils.capitalize(
                StringUtils.join(
                        StringUtils.splitByCharacterTypeCamelCase(word),
                        ' '
                )
        );
    }

}
