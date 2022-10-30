package controllers;

import dispatchers.DispatcherService;
import dispatchers.playerAddGold.PlayerAddGoldDispatcherService;

import static spark.Spark.post;

public class PlayerAddGoldController {

    private static final String ENDPOINT_PREFIX = "/playerAddGold";
    private static PlayerAddGoldController INSTANCE;
    private final DispatcherService service;

    private PlayerAddGoldController() {
        this.service = new PlayerAddGoldDispatcherService();
    }

    public static PlayerAddGoldController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerAddGoldController();
        }
        return INSTANCE;
    }

    public void initEndpoints() {
        post(ENDPOINT_PREFIX, service::processPostRequest);

        // TODO logger
        System.out.printf("%s inited.%n", this.getClass().getSimpleName());
    }
}
