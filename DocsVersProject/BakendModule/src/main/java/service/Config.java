package service;

import dao.DAOType;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 01.04.13
 * Time: 11:08
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    private static String hibernateConfPath;
    private static String scriptsPath;
    private static String launchedXMLPAth;
    private static String uploadPath;
    private static String dbPropertiesPath;
    private static DAOType connType;
    private static boolean inMemory;

    public static void init(Properties properties) {
        hibernateConfPath = properties.getProperty("hibernateConfigFilePath");
        scriptsPath = properties.getProperty("scripts");
        launchedXMLPAth = properties.getProperty("launchedScriptsNamesStorage");
        uploadPath = properties.getProperty("file-upload");
        connType = DAOType.valueOf(properties.getProperty("connType"));
        dbPropertiesPath = properties.getProperty("database");
        inMemory = Boolean.valueOf(properties.getProperty("inMemory"));

    }

    public static String getHibernateConfPath() {
        return hibernateConfPath;
    }

    public static String getScriptsPath() {
        return scriptsPath;
    }

    public static String getLaunchedXMLPAth() {
        return launchedXMLPAth;
    }

    public static String getUploadPath() {
        return uploadPath;
    }

    public static String getDbPropertiesPath() {
        return dbPropertiesPath;
    }

    public static DAOType getConnType() {
        return connType;
    }

    public static boolean isInMemory() {
        return inMemory;
    }
}
