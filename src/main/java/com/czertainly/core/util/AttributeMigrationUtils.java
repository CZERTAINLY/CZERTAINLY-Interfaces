package com.czertainly.core.util;

import com.czertainly.api.model.common.attribute.AttributeCallback;
import com.czertainly.api.model.common.attribute.AttributeDefinition;
import com.czertainly.api.model.common.attribute.AttributeType;
import com.czertainly.api.model.common.attribute.content.BaseAttributeContent;
import com.czertainly.api.model.common.attribute.content.FileAttributeContent;
import com.czertainly.api.model.common.attribute.content.JsonAttributeContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttributeMigrationUtils {

    public static List<String> getMigrationCommands(ResultSet rows, String tableName, String columnName) throws SQLException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> migrationCommands = new ArrayList<>();
        while (rows.next()) {
            List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
            List<Map<String, Object>> oldAttributeValue = mapper.readValue( rows.getString(columnName), new TypeReference<List<Map<String, Object>>>(){});
            for(Map<String, Object> item: oldAttributeValue){
                attributeDefinitions.add(getNewAttributes(item));
            }
            String serializedAttributes = AttributeDefinitionUtils.serialize(attributeDefinitions);
            String updateCommand = "UPDATE " + tableName + " SET " + columnName + " = '" + serializedAttributes + "' WHERE id = " + rows.getString("id")+ ";";
            migrationCommands.add(updateCommand);
        }
        return migrationCommands;
    }

    public static AttributeDefinition getNewAttributes(Map<String, Object> oldAttribute){
        AttributeDefinition attributeDefinition = new AttributeDefinition();
        attributeDefinition.setLabel((String) oldAttribute.get("label"));
        attributeDefinition.setName((String) oldAttribute.get("name"));
        attributeDefinition.setUuid((String) oldAttribute.get("uuid"));
        attributeDefinition.setReadOnly((Boolean) oldAttribute.get("readOnly"));
        if(oldAttribute.get("required") != null) {
            attributeDefinition.setRequired((Boolean) oldAttribute.get("required"));
        }else{
            attributeDefinition.setRequired(true);
        }
        if(oldAttribute.get("description") != null) {
            attributeDefinition.setDescription(((String) oldAttribute.get("description")).replaceAll("'", "''"));
        }else{
            attributeDefinition.setDescription(null);
        }
        if(oldAttribute.get("visible") != null) {
            attributeDefinition.setVisible((Boolean) oldAttribute.get("visible"));
        }else{
            attributeDefinition.setVisible(true);
        }
        if(oldAttribute.get("multiValue") != null) {
            attributeDefinition.setMultiSelect((Boolean) oldAttribute.get("multiValue"));
        }else{
            attributeDefinition.setMultiSelect(false);
        }
        if(oldAttribute.get("validationRegex") != null) {
            attributeDefinition.setValidationRegex((String) oldAttribute.get("validationRegex"));
        }else{
            attributeDefinition.setMultiSelect(false);
        }
        if(oldAttribute.get("value") != null) {
            attributeDefinition.setContent(getAttributeValue(oldAttribute.get("value"), (String) oldAttribute.get("type")));
        }else{
            attributeDefinition.setContent(null);
        }
        attributeDefinition.setType(getAttributeType(oldAttribute.get("value"), (String) oldAttribute.get("type")));
        attributeDefinition.setList(isAttributeList((String) oldAttribute.get("type")));
        if(oldAttribute.get("attributeCallback") != null){
            ObjectMapper mapper = new ObjectMapper();
            try {
                String callbackAsString = mapper.writeValueAsString(oldAttribute.get("attributeCallback"));
                AttributeCallback attributeCallback = mapper.readValue(callbackAsString, AttributeCallback.class);
                attributeDefinition.setAttributeCallback(attributeCallback);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return attributeDefinition;
    }

    private static AttributeType getAttributeType(Object oldValue, String oldAttributeType){
        if (oldAttributeType.equals("LIST")){
            if(oldValue instanceof List) {
                if (((List<?>) oldValue).get(0) instanceof String) {
                    return AttributeType.STRING;
                } else {
                    return AttributeType.JSON;
                }
            }else if (oldValue instanceof Map){
                return AttributeType.JSON;
            }
            else if (oldValue instanceof String){
                return AttributeType.STRING;
            }
            else if (oldValue instanceof Integer){
                return AttributeType.INTEGER;
            }
            else if (oldValue instanceof Float){
                return AttributeType.FLOAT;
            }
        }
        if(oldValue instanceof Integer){
            return AttributeType.INTEGER;
        }
        if(oldValue instanceof Float){
            return AttributeType.FLOAT;
        }

        return AttributeType.fromCode(((String) oldAttributeType).toLowerCase());
    }

    private static Boolean isAttributeList(String oldAttributeType){
        if (oldAttributeType.equals("LIST")){
            return true;
        }
        return false;
    }

    private static Object getAttributeValue(Object oldValue, String oldType){
        if(oldType.equals("FILE")){
            return new FileAttributeContent(){{setValue((String) oldValue);}};
        }
        if(oldValue instanceof String){
            return new BaseAttributeContent(){{setValue((String) oldValue);}};
        }else if(oldValue instanceof Integer){
            return new BaseAttributeContent<Integer>(){{setValue((Integer) oldValue);}};
        }else if(oldValue instanceof Float){
            return new BaseAttributeContent<Float>(){{setValue((Float) oldValue);}};
        }else if(oldValue instanceof Boolean){
            return new BaseAttributeContent<Boolean>(){{setValue((Boolean) oldValue);}};
        }else if(oldValue instanceof Integer){
            return new BaseAttributeContent<Integer>(){{setValue((Integer) oldValue);}};
        }else if(oldValue instanceof List || oldType.equals("LIST")){
            if(oldValue instanceof List){
                if(((List<?>) oldValue).get(0) instanceof String) {
                    return new BaseAttributeContent<>() {{
                        setValue(oldValue);
                    }};
                }
            }
            else {
                String primKey;
                Map<String, Object> insValue = (Map<String, Object>) oldValue;
                if(insValue.containsKey("name")){
                    primKey = (String) insValue.get("name");
                }else{
                    try {
                        primKey = (String) insValue.get(insValue.keySet().stream().findFirst().orElse("error"));
                    }catch (Exception e){
                        primKey = "error";
                    }
                }
                String finalPrimKey = primKey;
                return new JsonAttributeContent(){{setValue(finalPrimKey);setData(oldValue);}};
            }
        }
        return null;
    }
}
