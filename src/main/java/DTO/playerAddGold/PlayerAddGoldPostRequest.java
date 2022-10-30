package DTO.playerAddGold;

import lombok.Data;

@Data
public class PlayerAddGoldPostRequest {

    private long clanId;
    private long playerId;
    private long goldAmount;

}
