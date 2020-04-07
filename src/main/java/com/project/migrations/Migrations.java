package com.project.migrations;

import com.project.service.HikariCPDataSource;

import java.sql.Connection;
import java.sql.Statement;

public class Migrations {





    public static void run() {

        try (Connection conn = HikariCPDataSource.getConnection(); Statement stmt = conn.createStatement()) {


            String SQL_CREATE_TABLE = "DROP TABLE IF EXISTS person; CREATE TABLE person (id character varying NOT null, name character varying(50), last_name character varying(50), age integer NOT NULL, CONSTRAINT person_pkey PRIMARY KEY (id), CONSTRAINT person_name_key UNIQUE (name));";

            stmt.executeUpdate(SQL_CREATE_TABLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Migrations Complete");
    }

}
