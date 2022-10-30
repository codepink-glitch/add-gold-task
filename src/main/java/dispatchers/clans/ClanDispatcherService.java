package dispatchers.clans;

import DTO.clans.ClanGetRequest;
import DTO.clans.ClanPatchRequest;
import DTO.clans.ClanPostRequest;
import DTO.errors.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Clan;
import services.ClanService;
import dispatchers.DispatcherService;
import spark.Request;
import spark.Response;
import util.*;

import java.util.Collections;
import java.util.Map;

public class ClanDispatcherService implements DispatcherService {

    private final static ClanService clanService = ClanService.getInstance();
    private final RequestMapper<ClanGetRequest> getMapper;
    private final RequestMapper<ClanPostRequest> postMapper;
    private final RequestMapper<ClanPatchRequest> patchMapper;
    private final ObjectMapper responseMapper = JacksonMapperHolder.getInstance().getObjectMapper();

    public ClanDispatcherService() {
        this.getMapper = MapperFactory.getMapper(RequestTypes.GET, ClanGetRequest.class);
        this.postMapper = MapperFactory.getMapper(RequestTypes.POST, ClanPostRequest.class);
        this.patchMapper = MapperFactory.getMapper(RequestTypes.POST, ClanPatchRequest.class);
    }

    @Override
    public String processGetRequest(Request request, Response response) {
        try {
            ClanGetRequest getRequest = getMapper.mapParams(request);
            Clan resultClan = clanService.getById(getRequest.getId());
            setResponseStatus(response, 200);
            return responseMapper.writeValueAsString(resultClan);
        } catch (Exception e) {
           setResponseStatus(response, 400);
           return new ErrorResponse(e).toString();
        }
    }

    @Override
    public String processPostRequest(Request request, Response response) {
        try {
            ClanPostRequest clanPostRequest = postMapper.mapParams(request);
            Clan newClan = clanService.createNew(clanPostRequest);
            setResponseStatus(response, 200);
            return responseMapper.writeValueAsString(newClan);
        } catch (Exception e) {
            setResponseStatus(response, 400);
            return new ErrorResponse(e).toString();
        }
    }

    @Override
    public String processDeleteRequest(Request request, Response response) {
        return "Unimplemented";
    }

    @Override
    public String processPatchRequest(Request request, Response response) {
        try {
            ClanPatchRequest clanPatchRequest = patchMapper.mapParams(request);
            Map<String, Boolean> resultResp = Collections.singletonMap("success",
                    clanService.changeGoldAmount(clanPatchRequest));
            setResponseStatus(response, 200);
            return responseMapper.writeValueAsString(resultResp);
        } catch (Exception e) {
            setResponseStatus(response, 400);
            return new ErrorResponse(e).toString();
        }
    }

}
