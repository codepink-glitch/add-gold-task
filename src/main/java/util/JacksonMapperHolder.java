package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class JacksonMapperHolder {

    private static volatile JacksonMapperHolder INSTANCE;
    private static final Object MUTEX = new Object();
    private final ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private JacksonMapperHolder() {
        super();
    }

    public static JacksonMapperHolder getInstance() {
        JacksonMapperHolder instance = INSTANCE;

        if (instance == null) {
            synchronized(MUTEX) {
                instance = INSTANCE;

                if (instance == null) {
                    INSTANCE = instance = new JacksonMapperHolder();
                }

            }
        }

        return instance;
    }

    public final ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
