package com.gnida.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnida.domain.BudgetDto;
import com.gnida.entity.Budget;

import java.io.IOException;
import java.util.List;

public class Converter {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
//        mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

    }
    public static<T> String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static<T> T fromJson(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public static final TypeReference<List<BudgetDto>> budgetListType = new TypeReference<>() {};

    public static ObjectMapper getInstance() {
        return mapper;
    }
}
