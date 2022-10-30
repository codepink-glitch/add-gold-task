package services;

import DTO.logs.LoggingPostRequest;
import db.DAO.LogDAO;
import db.implementation.PoolConnectionBuilder;
import entity.LogEntity;
import exceptions.ServiceException;
import logging.LogLevel;

import java.util.List;

public class LoggingService {

    private static volatile LoggingService INSTANCE;
    private static final Object MUTEX = new Object();
    private final LogDAO logDAO;

    private LoggingService() {
        super();
        this.logDAO = new LogDAO(PoolConnectionBuilder.getInstance());
    }

    public static LoggingService getInstance() {
        LoggingService instance = INSTANCE;

        if (instance == null) {
            synchronized (MUTEX) {
                instance = INSTANCE;

                if (instance == null) {
                    INSTANCE = instance = new LoggingService();
                }
            }
        }

        return instance;
    }

    public List<LogEntity> getAll() throws ServiceException {
        try {
            return logDAO.getAll();
        } catch (Exception e) {
            log(new LogEntity(e.getMessage(), e.getCause().getClass().getSimpleName(), LogLevel.ERROR));
            throw new ServiceException("Exception getting all logs.\nCause: " +  e.getMessage());
        }
    }

//    public List<LogEntity> getByParams(LoggingPostRequest request) throws ServiceException {
//        try {
//            return logDAO.getByParams(request);
//        } catch (Exception e) {
//            log(new LogEntity(e.getMessage(), e.getCause().getClass().getSimpleName(), LogLevel.ERROR));
//            throw new ServiceException("Exception getting logs by params.\nCause: " +  e.getMessage());
//        }
//    }

    public boolean log(LogEntity entity) {
        try {
            return logDAO.createNew(entity);
        } catch (Exception e) {
            System.err.println("Exception while logging, check database connection.");
        }
        return false;
    }

}
