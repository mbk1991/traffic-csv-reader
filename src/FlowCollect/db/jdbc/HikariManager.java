package FlowCollect.db.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariManager {
    private static final HikariDataSource HIKARI_DATA_SOURCE = new HikariDataSource(); //single tone
    private static boolean sealed = false;

    private HikariManager() {
        //no instance
    }

    public static Connection getConnection() throws SQLException {
        return HIKARI_DATA_SOURCE.getConnection();
    }

    public static void setConfig(String driver, String url, String user, String pw, int cpSize) throws SQLException {
        if(!sealed){
            HIKARI_DATA_SOURCE.setDriverClassName(driver);
            HIKARI_DATA_SOURCE.setJdbcUrl(url);
            HIKARI_DATA_SOURCE.setUsername(user);
            HIKARI_DATA_SOURCE.setPassword(pw);
            HIKARI_DATA_SOURCE.setMaximumPoolSize(cpSize);
            HIKARI_DATA_SOURCE.setReadOnly(false);
            sealed = true;
        }
    }
}
