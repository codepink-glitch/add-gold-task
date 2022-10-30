package dispatchers.playerAddGold;

import DTO.playerAddGold.PlayerAddGoldPostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import services.PlayerAddGoldService;
import dispatchers.DispatcherService;
import spark.Request;
import spark.Response;
import util.JacksonMapperHolder;
import util.MapperFactory;
import util.RequestMapper;
import util.RequestTypes;

import java.util.Collections;
import java.util.Map;

public class PlayerAddGoldDispatcherService implements DispatcherService {

    private final static PlayerAddGoldService service = PlayerAddGoldService.getInstance();
    private final RequestMapper<PlayerAddGoldPostRequest> postMapper;
    private final ObjectMapper responseMapper = JacksonMapperHolder.getInstance().getObjectMapper();

    public PlayerAddGoldDispatcherService() {
        this.postMapper = MapperFactory.getMapper(RequestTypes.POST, PlayerAddGoldPostRequest.class);
    }

    @Override
    public String processGetRequest(Request request, Response response) {
        return "Unimplemented";
    }

    @Override
    public String processPostRequest(Request request, Response response) {
        try {
            PlayerAddGoldPostRequest postRequest = postMapper.mapParams(request);
            Map<String, Boolean> result = Collections.singletonMap("success",
                    service.addGoldToClan(postRequest));
            setResponseStatus(response, 200);
            return responseMapper.writeValueAsString(result);
        } catch (Exception e) {
            setResponseStatus(response, 400);
            return new Error(e).toString();
        }
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
