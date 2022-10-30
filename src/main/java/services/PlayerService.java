package services;

import DTO.players.PlayerPatchRequest;
import DTO.players.PlayerPostRequest;
import db.DAO.PlayerDAO;
import db.implementation.PoolConnectionBuilder;
import entity.LogEntity;
import entity.Player;
import exceptions.DAOException;
import exceptions.ServiceException;
import logging.LogLevel;

public class PlayerService extends SynchronizedService<Long> {

    private static volatile PlayerService INSTANCE;
    private static final Object MUTEX = new Object();

    private final PlayerDAO playerDAO;
    private final LoggingService loggingService;

    public static PlayerService getInstance() {
        PlayerService instance = INSTANCE;

        if (instance == null) {
            synchronized (MUTEX) {
                instance = INSTANCE;

                if (instance == null) {
                    INSTANCE = instance = new PlayerService();
                }
            }
        }
        return instance;
    }

    private PlayerService() {
        this.playerDAO = new PlayerDAO(PoolConnectionBuilder.getInstance());
        this.loggingService = LoggingService.getInstance();
    }

    public Player getById(long id) throws ServiceException {
        try {
//            return playerDAO.getPlayerById(id);
            Player player = playerDAO.getPlayerById(id);
            loggingService.log(new LogEntity("Retrieved player by id: " + id, null, LogLevel.INFO));
            return player;
        } catch (DAOException e) {
            loggingService.log(new LogEntity(e.getMessage(), e.getCause().getClass().getSimpleName(), LogLevel.ERROR));
            throw new ServiceException(String.format("Exception getting player.\nCause: %s", e.getMessage()));
        }
    }

    public Player createNew(PlayerPostRequest request) throws ServiceException {
        try {
            Player player = playerDAO.registerNewPlayer(request.getUsername());
            loggingService.log(
                    new LogEntity(String.format("Created player with id: %d, and username: %s.", player.getId(), player.getUsername()),
                    null,
                    LogLevel.INFO
            ));
            return player;
        } catch (DAOException e) {
            loggingService.log(new LogEntity(e.getMessage(), e.getCause().getClass().getSimpleName(), LogLevel.ERROR));
            throw new ServiceException(String.format("Exception creating new player.\nCause: %s", e.getMessage()));
        }
    }

    public boolean changeGoldAmount(PlayerPatchRequest request) throws ServiceException {
        try {
            boolean result = false;

            synchronized (getSyncObject(request.getPlayerId())) {
                result =  playerDAO.changeGoldAmount(request.getPlayerId(), request.getGoldAmount());
            }

            loggingService.log(new LogEntity(
               String.format("Changed gold for player with id: %d for amount: %d", request.getPlayerId(), request.getGoldAmount()),
               null,
               LogLevel.INFO
            ));
            return result;
        } catch (DAOException e) {
            loggingService.log(new LogEntity(e.getMessage(), e.getCause().getClass().getSimpleName(), LogLevel.ERROR));
            throw new ServiceException(String.format("Exception changing player gold amount.\nCause: %s", e.getMessage()));        }
    }
}
