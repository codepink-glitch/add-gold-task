package util.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.ReadingRequestException;
import spark.Request;
import util.JacksonMapperHolder;
import util.RequestMapper;


public class PostRequestMapper<T> implements RequestMapper<T> {

    private final Class<T> targetClass;

    private final static ObjectMapper objectMapper =
            JacksonMapperHolder.getInstance().getObjectMapper();

    public PostRequestMapper(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public T mapParams(Request request) throws ReadingRequestException {
        try {
            return objectMapper.readValue(request.body(), targetClass);
        } catch (Exception e) {
            throwException(e.getClass().getName(), e.getLocalizedMessage());
        }
        return null;
    }
}
