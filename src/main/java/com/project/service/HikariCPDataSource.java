package com.project.service;

import com.project.Server;
import com.project.properties.PropertiesReader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPDataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {


        config.setDriverClassName(PropertiesReader.properties.getProperty("db.driverClassName"));
        config.setJdbcUrl(PropertiesReader.properties.getProperty("db.url"));
        config.setUsername(PropertiesReader.properties.getProperty("db.username"));
        config.setPassword(PropertiesReader.properties.getProperty("db.password"));

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        config.setMaximumPoolSize(8);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(60000);
        config.setMinimumIdle(1);
        config.setLeakDetectionThreshold(60000);
        config.setMaxLifetime(60000);
        config.getConnectionInitSql();

        ds = new HikariDataSource(config);

        System.out.println("Configuration Database success");

    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private HikariCPDataSource() {

    }

}
