package dispatchers;

import spark.Request;
import spark.Response;

public interface DispatcherService {

    public String processGetRequest(Request request, Response response);

    public String processPostRequest(Request request, Response response);

    public String processDeleteRequest(Request request, Response response);

    public String processPatchRequest(Request request, Response response);

    default void setResponseStatus (Response response, int status) {
        response.status(status);
        response.type("application/json");
    }
}
