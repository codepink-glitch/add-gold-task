package dispatchers.logs;

import DTO.errors.ErrorResponse;
import DTO.logs.LoggingPostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import dispatchers.DispatcherService;
import entity.LogEntity;
import services.LoggingService;
import spark.Request;
import spark.Response;
import util.JacksonMapperHolder;
import util.MapperFactory;
import util.RequestMapper;
import util.RequestTypes;

import java.util.List;

public class LoggingDispatcherService implements DispatcherService {

    private final static LoggingService loggingService = LoggingService.getInstance();
    private final ObjectMapper responseMapper = JacksonMapperHolder.getInstance().getObjectMapper();
    private final RequestMapper<LoggingPostRequest> postMapper;

    public LoggingDispatcherService() {
        this.postMapper = MapperFactory.getMapper(RequestTypes.POST, LoggingPostRequest.class);
    }

    @Override
    public String processGetRequest(Request request, Response response) {
        try {
            List<LogEntity> all = loggingService.getAll();
            setResponseStatus(response, 200);
            return responseMapper.writeValueAsString(all);
        } catch (Exception e) {
            setResponseStatus(response, 400);
            return new ErrorResponse(e).toString();
        }
    }

    @Override
    public String processPostRequest(Request request, Response response) {
        return "Unimplemented";
//        try {
//            LoggingPostRequest loggingPostRequest = postMapper.mapParams(request);
//            List<LogEntity> byParams = loggingService.getByParams(loggingPostRequest);
//            setResponseStatus(response, 200);
//            return responseMapper.writeValueAsString(byParams);
//        } catch (Exception e) {
//            setResponseStatus(response, 400);
//            return new Error(e).toString();
//        }
    }

    @Override
    public String processDeleteRequest(Request request, Response response) {
        return "Unimplemented";
    }

    @Override
    public String processPatchRequest(Request request, Response response) {
        return "Unimplemented";
    }
}
