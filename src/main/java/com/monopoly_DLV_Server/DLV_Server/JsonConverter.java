package com.monopoly_DLV_Server.DLV_Server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class JsonConverter {

    private static JsonConverter instance = null;
    private ObjectMapper objectMapper;

    private JsonConverter() {
        objectMapper = new ObjectMapper();
    }

    public static JsonConverter getInstance(){
        if(instance == null){
            instance = new JsonConverter();
        }
        return instance;
    }

    public ArrayList<Object> getArray(String json, Class<?> classType){
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, classType));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getObject(String json, Class<?> classType){
        try {
            return objectMapper.readValue(json, classType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
