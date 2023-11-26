package com.gnida.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Converter {
    public static<T> String toJson(T object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static<T> T fromJson(String json, Class<T> tClass) {
        try {
            return new ObjectMapper().readValue(json, tClass);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}
