package util.implementation;

import exceptions.ReadingRequestException;
import exceptions.UnknownFieldException;
import lombok.SneakyThrows;
import spark.QueryParamsMap;
import spark.Request;
import util.RequestMapper;

import java.lang.reflect.Field;

public class GetRequestMapper<T> implements RequestMapper<T> {

    private final Class<T> targetClass;

    public GetRequestMapper(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public T mapParams(Request request) throws ReadingRequestException {
        try {
            QueryParamsMap requestParamsMap = request.queryMap();
            T targetObject = targetClass.getConstructor().newInstance();
            fillTargetObject(targetObject, targetClass.getDeclaredFields(), requestParamsMap);
            return targetObject;
        } catch (Exception e) {
            throwException(e.getClass().getName(), e.getLocalizedMessage());
        }
        return null;
    }

    @SneakyThrows
    private void fillTargetObject(T targetObject, Field[] fields, QueryParamsMap paramsMap) {
        for (Field field: fields) {
            String value = paramsMap.get(field.getName()).value();
            if (value != null) {
                field.setAccessible(true);
                field.set(targetObject, convertValue(value, field.getType()));
            }
        }
    }

    private Object convertValue(String value, Class<?> convertTo) throws NumberFormatException, UnknownFieldException {
        switch(convertTo.getSimpleName()) {
            case "Integer":
                return Integer.valueOf(value);
            case "Long":
                return Long.valueOf(value);
            case "String":
                return value;
            default:
                throw new UnknownFieldException(String.format("Unknown field of type: %s", convertTo.getName()));
        }
    }
}
