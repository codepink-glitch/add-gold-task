package services;

import DTO.clans.ClanPatchRequest;
import DTO.playerAddGold.PlayerAddGoldPostRequest;
import DTO.players.PlayerPatchRequest;
import entity.LogEntity;
import entity.Player;
import exceptions.ServiceException;
import logging.LogLevel;

public class PlayerAddGoldService {

    private static volatile PlayerAddGoldService INSTANCE;
    private static final Object MUTEX = new Object();

    private final ClanService clanService;
    private final PlayerService playerService;
    private final LoggingService loggingService;

    public static PlayerAddGoldService getInstance() {
        PlayerAddGoldService instance = INSTANCE;

        if (instance == null) {
            synchronized (MUTEX) {
                instance = INSTANCE;

                if (instance == null) {
                    INSTANCE = instance = new PlayerAddGoldService();
                }
            }
        }

        return instance;
    }

    private PlayerAddGoldService() {
        this.clanService = ClanService.getInstance();
        this.playerService = PlayerService.getInstance();
        this.loggingService = LoggingService.getInstance();
    }


    public boolean addGoldToClan(PlayerAddGoldPostRequest request) throws ServiceException {
        try {
            if (request.getGoldAmount() <= 0) {
                throw new RuntimeException("Can't pass negative or null gold value to clan");
            }

            synchronized (clanService.getSyncObject(request.getClanId())) {
                synchronized (playerService.getSyncObject(request.getPlayerId())) {
                    final Player player = playerService.getById(request.getPlayerId());

                    if (player.getGold() < request.getGoldAmount()) {
                        throw new RuntimeException("Player doesn't have enough gold");
                    }

                    final PlayerPatchRequest playerRequest =
                            PlayerPatchRequest.builder()
                                    .playerId(request.getPlayerId())
                                    .goldAmount(-request.getGoldAmount())
                                    .build();

                    if (!playerService.changeGoldAmount(playerRequest)) {
                        throw new RuntimeException("Player gold wasn't reduced.");
                    }

                    ClanPatchRequest clanRequest =
                            ClanPatchRequest.builder()
                                    .clanId(request.getClanId())
                                    .goldAmount(request.getGoldAmount())
                                    .build();

                    if (!clanService.changeGoldAmount(clanRequest)) {
                        playerRequest.setGoldAmount(request.getGoldAmount());
                        playerService.changeGoldAmount(playerRequest);
                        throw new RuntimeException("Clan gold wasn't changed");
                    }
                }
            }
        } catch (Exception e) {
            loggingService.log(new LogEntity(e.getMessage(), e.getCause().getClass().getSimpleName(), LogLevel.ERROR));
            throw new ServiceException(
                    String.format(
                            "Exception sending gold from player with id %d to clan with id %d.\nCause: %s",
                            request.getPlayerId(),
                            request.getClanId(),
                            e.getMessage())
            );
        }
        loggingService.log(new LogEntity(
                String.format("Send gold from player with id %d to clan with id %d for amount of %d.",
                        request.getPlayerId(), request.getClanId(), request.getGoldAmount()),
                null,
                LogLevel.INFO));
        return true;
    }

}
