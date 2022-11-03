package com.czertainly.core.util;

import com.czertainly.api.model.common.attribute.v1.AttributeDefinition;
import com.czertainly.api.model.common.attribute.v1.AttributeType;
import com.czertainly.api.model.common.attribute.v1.content.JsonAttributeContent;
import com.czertainly.api.model.common.attribute.v2.AttributeProperties;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallbackMapping;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeValueTarget;
import com.czertainly.api.model.common.attribute.v2.constraint.AttributeConstraintType;
import com.czertainly.api.model.common.attribute.v2.constraint.BaseAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.constraint.RegexpAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v2.content.data.FileAttributeContentData;
import com.czertainly.api.model.common.attribute.v2.content.data.SecretAttributeContentData;
import com.czertainly.api.model.core.credential.CredentialDto;
import com.czertainly.core.deprecated.AttributeDefinitionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
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
            for (AttributeDefinition item : oldAttributeValue) {
                attributeDefinitions.add(getNewAttributes(item));
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

    public static BaseAttribute getNewAttributes(AttributeDefinition oldAttribute) {

        //Old Attribute Value to new attribute properties

        DataAttribute attribute = new DataAttribute();

        AttributeProperties properties = new AttributeProperties();
        properties.setList(oldAttribute.isList());
        properties.setMulti(oldAttribute.isMultiSelect());
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
        return attribute;
    }

    private static List<BaseAttributeConstraint> getAttributeConstraint(String regex) {
        if (regex == null) {
            return null;
        }
        return List.of(new RegexpAttributeConstraint("", "", AttributeConstraintType.REGEXP, regex));
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
                    CredentialDto credentialDto = new CredentialDto();
                    LinkedHashMap credentialData = (LinkedHashMap) ((JsonAttributeContent) oldContent).getData();
                    credentialDto.setName((String) credentialData.get("name"));
                    credentialDto.setUuid((String) credentialData.get("uuid"));
                    credentialDto.setConnectorName((String) credentialData.get("connectorName"));
                    credentialDto.setConnectorUuid((String) credentialData.get("connectorUuid"));
                    credentialDto.setKind((String) credentialData.get("kind"));
                    credentialDto.setEnabled((Boolean) credentialData.get("enabled"));
                    List<BaseAttribute> credentialAttributes = new ArrayList<>();
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
                        credentialAttributes.add(getNewAttributes(item));
                    }
                    credentialDto.setAttributes(com.czertainly.core.util.AttributeDefinitionUtils.getResponseAttributes(credentialAttributes));
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
                            data.setMimeType(MimeType.valueOf(oldContentFileData.getContentType()));
                        } catch (InvalidMimeTypeException e) {
                            //Do nothing
                        }
                    attributeContents.add(new FileAttributeContent(oldContentFileData.getFileName(), data));
                    break;
            }
        }
        return attributeContents;
    }

    private static com.czertainly.api.model.common.attribute.v1.content.BaseAttributeContent convertOldContents(AttributeType attributeType, Object oldContentData) {
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

}
