package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.QueryParamsMap;
import spark.Request;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class RequestsMappingUtil <P, G> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public P pullBodyParams(Request request, Class<P> targetClass) throws JsonProcessingException {
        return objectMapper.readValue(request.body(), targetClass);
    }

    public G pullQueryParams(Request request, Class<G> targetClass)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        QueryParamsMap requestParamsMap = request.queryMap();
        G targetObject = targetClass.getConstructor().newInstance();
        fillTargetObject(targetObject, targetClass.getDeclaredFields(), requestParamsMap);
        return targetObject;
    }

    private void fillTargetObject(Object targetObject, Field[] fields, QueryParamsMap paramsMap) throws IllegalAccessException {
        for (Field field: fields) {
            String value = paramsMap.get(field.getName()).value();
            if (value != null) {
                field.setAccessible(true);
                field.set(targetObject, convertValue(value, field.getType()));
            }
        }
    }


    public String convertResponseToString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    private Object convertValue(String value, Class<?> convertTo) throws NumberFormatException {
        switch(convertTo.getSimpleName()) {
            case "Integer":
                return Integer.valueOf(value);
            case "Long":
                return Long.valueOf(value);
            case "String":
                return value;
            default:
                return null;
        }
    }
}
