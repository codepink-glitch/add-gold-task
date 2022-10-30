package db.DAO.queries;

public enum LogQueries {
    GET_ALL("select l.log_id, l.log_message, l.log_error, l.log_level, l.created_at " +
            "from logs l"),
//    GET_BY_PARAMS("select l.log_id, l.log_message, l.log_error, l.log_level, l.created_at " +
//            "from logs l " +
//            "where (? is null or l.log_level = ?) " +
//            "and (coalesce(?, '-1') = '-1' or lower(l.log_message) like lower(concat('%', ?::varchar, '%'))) " +
//            "and (coalesce(?, '-1') = '-1' or lower(l.log_error) like lower(concat('%', ?::varchar, '%')))"),
    CREATE_NEW("insert into logs (log_message, log_error, log_level) " +
            "values(?, ?, ?)");

    private final String query;

    LogQueries (String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
