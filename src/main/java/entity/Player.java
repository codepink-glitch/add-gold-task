package entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Player {

    private long id;
    private long gold;
    private String username;
    private Clan clan;

    public Player(long id, long gold, String username) {
        this.id = id;
        this.gold = gold;
        this.username = username;
    }

    public Player(long id, long gold, String username, Clan clan) {
        this.id = id;
        this.gold = gold;
        this.username = username;
    }
}
