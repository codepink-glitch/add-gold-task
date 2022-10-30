package DTO.clans;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClanPatchRequest {

    private long clanId;
    private long goldAmount;

}
