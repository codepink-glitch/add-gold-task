package util;

import util.implementation.GetRequestMapper;
import util.implementation.PostRequestMapper;

public class MapperFactory {

    public static <T> RequestMapper<T> getMapper(RequestTypes type, Class<T> targetClass) {
        switch(type) {
            case GET:
                return new GetRequestMapper<>(targetClass);
            case POST:
                return new PostRequestMapper<>(targetClass);
            default:
                return null;
        }
    }


}
