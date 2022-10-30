package DTO.players;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerPatchRequest {

    private long playerId;
    private long goldAmount;

}
