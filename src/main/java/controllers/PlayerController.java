package controllers;

import dispatchers.DispatcherService;
import dispatchers.players.PlayerDispatcherService;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.patch;

public class PlayerController {

    private static final String ENDPOINT_PREFIX = "/players";
    private static PlayerController INSTANCE;

    private final DispatcherService service;

    private PlayerController() {
        this.service = new PlayerDispatcherService();
    }

    public static PlayerController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerController();
        }
        return INSTANCE;
    }

    public void initEndpoints() {
        get(ENDPOINT_PREFIX, service::processGetRequest);
        post(ENDPOINT_PREFIX, service::processPostRequest);
        patch(ENDPOINT_PREFIX, service::processPatchRequest);

        System.out.printf("%s inited.%n", this.getClass().getSimpleName());
    }
}
