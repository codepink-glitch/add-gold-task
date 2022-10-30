package entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Clan {

    private long id;
    private String name;
    private long gold;
    private List<Player> members;

    public Clan(long id, String name, long gold) {
        this.id = id;
        this.name = name;
        this.gold = gold;
        this.members = new ArrayList<>();
    }
}
