package controllers;

import dispatchers.DispatcherService;
import dispatchers.logs.LoggingDispatcherService;

import static spark.Spark.get;
import static spark.Spark.post;

public class LoggingController {

    private static final String ENDPOINT_PREFIX = "/logs";
    private static LoggingController INSTANCE;

    private final DispatcherService service;

    private LoggingController() {
        this.service = new LoggingDispatcherService();
    }

    public static LoggingController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoggingController();
        }
        return INSTANCE;
    }

    public void initEndpoint() {
        get(ENDPOINT_PREFIX, service::processGetRequest);
//        post(ENDPOINT_PREFIX, service::processPostRequest);

        System.out.printf("%s inited.%n", this.getClass().getSimpleName());
    }
}
