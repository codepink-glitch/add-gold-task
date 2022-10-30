package db.DAO.queries;

public enum PlayerQueries {
    GET_BY_ID("select p.player_id, p.gold, p.player_username, c.clan_id, c.clan_name, c.gold as clan_gold " +
            "from players p " +
            "join clans_players cp on p.player_id = cp.player_id " +
            "join clans c on cp.clan_id = c.clan_id " +
            "where p.player_id = ?"),
    CREATE_NEW("insert into players (gold, player_username) " +
            "values (0, ?)"),
    CHANGE_GOLD("update players set gold = (gold + (? * 1)) " +
        "where player_id = ?");

    private final String query;

    PlayerQueries (String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
