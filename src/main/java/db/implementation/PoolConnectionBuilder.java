package db.implementation;

import db.ConnectionBuilder;
import db.DBUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnectionBuilder implements ConnectionBuilder {

    private static volatile PoolConnectionBuilder INSTANCE;
    private static final Object MUTEX = new Object();
    private final DataSource dataSource;

    private PoolConnectionBuilder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static PoolConnectionBuilder getInstance() {
        PoolConnectionBuilder instance = INSTANCE;

        if (instance == null) {
            synchronized (MUTEX) {
                instance = INSTANCE;

                if (instance == null) {
                    instance = INSTANCE = new PoolConnectionBuilder(DBUtil.getDataSource());
                }
            }
        }

        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
