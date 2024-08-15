package Prop;

import Util.NCutil;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropData {
    private static Properties prop;
    private static String propPath;
    private static String DB_DRIVER;
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWD;
    private static String FLOW_COLLECT_CRON;

    public static void setFlowCollectCron(String flowCollectCron) {
        FLOW_COLLECT_CRON = flowCollectCron;
    }

    public static int getChunkSize() {
        return CHUNK_SIZE;
    }

    public static void setChunkSize(int chunkSize) {
        CHUNK_SIZE = chunkSize;
    }

    public static int getThreadPoolSize() {
        return THREAD_POOL_SIZE;
    }

    public static void setThreadPoolSize(int threadPoolSize) {
        THREAD_POOL_SIZE = threadPoolSize;
    }

    public static int getConnectionPoolSize() {
        return CONNECTION_POOL_SIZE;
    }

    public static void setConnectionPoolSize(int connectionPoolSize) {
        CONNECTION_POOL_SIZE = connectionPoolSize;
    }

    private static int CHUNK_SIZE;
    private static int THREAD_POOL_SIZE;
    private static int CONNECTION_POOL_SIZE;


    public static String getFlowCollectCron() {
        return FLOW_COLLECT_CRON;
    }

    private static String DIR_PATH;

    private static String DEVICE_PER_THREAD;

    static {
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows") || osName.contains("Mac")) {
            propPath = "./conf/NC_Config.properties";
        } else {
            propPath = "../conf/NC_Config.properties";
        }
        PropData.setPropPath(propPath);
        try {
            PropData.readProperties();
        } catch (IOException e) {
            NCutil.log_msg("Error: Read Properties error. ");
            System.exit(-1);
        }
    }

    private PropData() {
        //no instance;
    }

    public static void readProperties() throws IOException {
        prop = new Properties();
        prop.load(new FileReader(propPath));

        loadDBInfo();
        loadFlowConfig();
        loadCron();
    }

    private static void loadFlowConfig() {
        setDirPath(prop.getProperty("INPUT_CSV_DIR"));
        setChunkSize(Integer.parseInt(prop.getProperty("CHUNK_SIZE")));
        setThreadPoolSize(Integer.parseInt(prop.getProperty("THREAD_POOL_SIZE")));
        setConnectionPoolSize(Integer.parseInt(prop.getProperty("CONNECTION_POOL_SIZE")));
    }


    public static String getDirPath() {
        return DIR_PATH;
    }

    public static void setDirPath(String dirPath) {
        DIR_PATH = dirPath;
    }


    private static void loadDBInfo() {
        setDbDriver(prop.getProperty("JDBC_DRIVER_MYSQL"));
        setDbUrl(prop.getProperty("JDBC_URL_THIN_MYSQL"));
        setDbUser(prop.getProperty("DB_USER_ID_MYSQL"));
        setDbPasswd(prop.getProperty("DB_USER_PW_MYSQL"));
    }

    private static void loadCron() {
        FLOW_COLLECT_CRON = prop.getProperty("FlowCollectCron");
    }


    public static String getDbDriver() {
        return DB_DRIVER;
    }

    public static void setDbDriver(String dbDriver) {
        DB_DRIVER = dbDriver;
    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static void setDbUrl(String dbUrl) {
        DB_URL = dbUrl;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static void setDbUser(String dbUser) {
        DB_USER = dbUser;
    }

    public static String getDbPasswd() {
        return DB_PASSWD;
    }

    public static void setDbPasswd(String dbPasswd) {
        DB_PASSWD = dbPasswd;
    }

    public static String getDevicePerThread() {
        return DEVICE_PER_THREAD;
    }

    public static void setDevicePerThread(String devicePerThread) {
        DEVICE_PER_THREAD = devicePerThread;
    }

    public static void setPropPath(String propPath) {
        PropData.propPath = propPath;
    }
}




