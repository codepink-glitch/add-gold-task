package services;

import DTO.clans.ClanPatchRequest;
import DTO.clans.ClanPostRequest;
import db.DAO.ClanDAO;
import db.implementation.PoolConnectionBuilder;
import entity.Clan;
import entity.LogEntity;
import exceptions.DAOException;
import exceptions.ServiceException;
import logging.LogLevel;


public class ClanService extends SynchronizedService<Long> {

    private static volatile ClanService INSTANCE;
    private static final Object MUTEX = new Object();

    private final ClanDAO clanDAO;
    private final LoggingService loggingService;

    public static ClanService getInstance() {
        ClanService instance = INSTANCE;

        if (instance == null) {
            synchronized (MUTEX) {
                instance = INSTANCE;

                if (instance == null) {
                    INSTANCE = instance = new ClanService();
                }
            }
        }

        return instance;
    }

    private ClanService() {
        this.clanDAO = new ClanDAO(PoolConnectionBuilder.getInstance());
        this.loggingService = LoggingService.getInstance();
    }

    public Clan getById(long id) throws ServiceException {
        try {
            Clan clan = clanDAO.getClanById(id);
            loggingService.log(new LogEntity("Retrieved clan by id: " + id, null, LogLevel.INFO));
            return clan;
        } catch (DAOException e) {
            loggingService.log(new LogEntity(e.getMessage(), e.getCause().getClass().getSimpleName(), LogLevel.ERROR));
            throw new ServiceException(String.format("Exception getting clan.\nCause: %s", e.getMessage()));
        }
    }

    public Clan createNew(ClanPostRequest request) throws ServiceException {
        try {
            Clan clan = clanDAO.registerNewClan(request.getClanName());
            loggingService.log(
                    new LogEntity(String.format("Created clan with id: %d, and clan name: %s.", clan.getId(), clan.getName()),
                            null,
                            LogLevel.INFO));
            return clan;
        } catch (DAOException e) {
            loggingService.log(new LogEntity(e.getMessage(), e.getCause().getClass().getSimpleName(), LogLevel.ERROR));
            throw new ServiceException(String.format("Exception creating new clan.\nCause: %s", e.getMessage()));
        }
    }

    public boolean changeGoldAmount(ClanPatchRequest request) throws ServiceException {
        try {
            boolean result;

            synchronized (getSyncObject(request.getClanId())) {
                result = clanDAO.changeGoldAmount(request.getClanId(), request.getGoldAmount());
            }

            loggingService.log(new LogEntity(
               String.format("Changed gold for clan with id: %d for amount: %d", request.getClanId(), request.getGoldAmount()),
                null,
                 LogLevel.INFO
            ));
            return result;
        } catch (DAOException e) {
            loggingService.log(new LogEntity(e.getMessage(), e.getCause().getClass().getSimpleName(), LogLevel.ERROR));
            throw new ServiceException(String.format("Exception changing clan gold amount.\nCause: %s", e.getMessage()));
        }
    }



}
