package db;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBUtil {

    private final static String PROPERTIES_PATH = "src/main/resources/db.properties";
    private final static String DRIVER_CLASS_KEY = "driver.class.name";
    private final static String URL_KEY = "db.url";
    private final static String USER_KEY = "db.username";
    private final static String PASSWORD_KEY = "db.password";
    private final static String MAX_CONNECTIONS_KEY = "db.max_connections";
    private final static String MIN_CONNECTIONS_KEY = "db.min_connections";

    private static BasicDataSource dataSource;

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(PROPERTIES_PATH));

            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty(DRIVER_CLASS_KEY));
            dataSource.setUrl(properties.getProperty(URL_KEY));
            dataSource.setUsername(properties.getProperty(USER_KEY));
            dataSource.setPassword(properties.getProperty(PASSWORD_KEY));
            dataSource.setMaxTotal(Integer.parseInt(properties.getProperty(MAX_CONNECTIONS_KEY)));
            dataSource.setMinIdle(Integer.parseInt(properties.getProperty(MIN_CONNECTIONS_KEY)));

        } catch (IOException e) {
            System.err.println("Exception parsing database configuration. Check " + PROPERTIES_PATH + " path.");
            System.exit(0);
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
