package dispatchers.players;

import DTO.errors.ErrorResponse;
import DTO.players.PlayerGetRequest;
import DTO.players.PlayerPatchRequest;
import DTO.players.PlayerPostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import dispatchers.DispatcherService;
import entity.Player;
import services.PlayerService;
import spark.Request;
import spark.Response;
import util.JacksonMapperHolder;
import util.MapperFactory;
import util.RequestMapper;
import util.RequestTypes;

import java.util.Collections;
import java.util.Map;

public class PlayerDispatcherService implements DispatcherService {

    private final static PlayerService playerService = PlayerService.getInstance();
    private final RequestMapper<PlayerGetRequest> getMapper;
    private final RequestMapper<PlayerPostRequest> postMapper;
    private final RequestMapper<PlayerPatchRequest> patchMapper;
    private final ObjectMapper responseMapper = JacksonMapperHolder.getInstance().getObjectMapper();

    public PlayerDispatcherService() {
        this.getMapper = MapperFactory.getMapper(RequestTypes.GET, PlayerGetRequest.class);
        this.postMapper = MapperFactory.getMapper(RequestTypes.POST, PlayerPostRequest.class);
        this.patchMapper = MapperFactory.getMapper(RequestTypes.POST, PlayerPatchRequest.class);
    }

    @Override
    public String processGetRequest(Request request, Response response) {
        try {
            PlayerGetRequest playerGetRequest = getMapper.mapParams(request);
            Player resultPlayer = playerService.getById(playerGetRequest.getId());
            setResponseStatus(response, 200);
            return responseMapper.writeValueAsString(resultPlayer);
        } catch (Exception e) {
            setResponseStatus(response, 400);
            return new ErrorResponse(e).toString();
        }
    }

    @Override
    public String processPostRequest(Request request, Response response) {
        try {
            PlayerPostRequest playerPostRequest = postMapper.mapParams(request);
            Player newPlayer = playerService.createNew(playerPostRequest);
            setResponseStatus(response, 200);
            return responseMapper.writeValueAsString(newPlayer);
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
        try {
            PlayerPatchRequest playerPatchRequest = patchMapper.mapParams(request);
            Map<String, Boolean> resultResp = Collections.singletonMap("success",
                    playerService.changeGoldAmount(playerPatchRequest));
            setResponseStatus(response, 200);
            return responseMapper.writeValueAsString(resultResp);
        } catch (Exception e) {
            setResponseStatus(response, 400);
            return new ErrorResponse(e).toString();
        }
    }

}
