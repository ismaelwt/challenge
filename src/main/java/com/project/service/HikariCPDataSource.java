package com.project.service;

import com.project.Server;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPDataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private static Properties properties = new Properties();

    public void createConnection() {


        config.setDriverClassName(properties.getProperty("driverClassName"));
        config.setJdbcUrl(properties.getProperty("jdbcUrl"));
        config.setUsername(properties.getProperty("username"));
        config.setPassword(properties.getProperty("password"));

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
        System.out.println("Configuration success");

    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void closeConnection() {


    }

    public HikariCPDataSource() {
        getProperties();

        if (ds == null)
            createConnection();
    }


    private void getProperties() {

        try {

            InputStream stream = Server.class.getClassLoader().getResourceAsStream("hikari.properties");
            properties.load(stream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
