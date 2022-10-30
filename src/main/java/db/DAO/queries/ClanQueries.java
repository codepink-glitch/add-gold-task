package db.DAO.queries;

public enum ClanQueries {
    GET_BY_ID("select c.clan_id, c.clan_name, c.gold, p.player_id, p.gold as player_gold, p.player_username " +
            "from clans c " +
            "join clans_players cp on c.clan_id = cp.clan_id " +
            "join players p on cp.player_id = p.player_id " +
            "where c.clan_id = ?"),
    CREATE_NEW("insert into clans (gold, clan_name) " +
            "values(0, ?)"),
    CHANGE_GOLD("update clans set gold = (gold + (? * 1)) " +
            "where clan_id = ?");
    private final String query;

    ClanQueries (String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
