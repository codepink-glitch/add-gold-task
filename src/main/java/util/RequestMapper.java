package util;

import exceptions.ReadingRequestException;
import spark.Request;

import java.lang.reflect.InvocationTargetException;

public interface RequestMapper <T> {

    public T mapParams(Request request) throws ReadingRequestException;

    default void throwException(String exClassName, String cause) throws ReadingRequestException {
        throw new ReadingRequestException(exClassName, cause);
    }
}
