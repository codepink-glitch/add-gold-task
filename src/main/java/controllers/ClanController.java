package controllers;

import dispatchers.clans.ClanDispatcherService;
import dispatchers.DispatcherService;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.patch;


public class ClanController {

    private static final String ENDPOINT_PREFIX = "/clans";
    private static ClanController INSTANCE;

    private final DispatcherService service;
    private ClanController() {
        this.service = new ClanDispatcherService();
    }

    public static ClanController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClanController();
        }
        return INSTANCE;
    }

    public void initEndpoints() {
        get(ENDPOINT_PREFIX, service::processGetRequest);
        post(ENDPOINT_PREFIX, service::processPostRequest);
        patch(ENDPOINT_PREFIX, service::processPatchRequest);

        // TODO logger
        System.out.printf("%s inited.%n", this.getClass().getSimpleName());
    }

}
