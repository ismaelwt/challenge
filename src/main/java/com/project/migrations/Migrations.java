package com.project.migrations;

import com.project.service.HikariCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Migrations {



    private static final String SQL_CREATE_TABLE = "DROP TABLE IF EXISTS person; CREATE TABLE person (id character varying NOT null, name character varying(50), last_name character varying(50), age integer NOT NULL, CONSTRAINT person_pkey PRIMARY KEY (id), CONSTRAINT person_name_key UNIQUE (name));";

    public static void run() {

        try {
            System.out.println("Running migrations ... ");
            Connection conn = new HikariCPDataSource().getConnection();
            PreparedStatement pst = conn.prepareStatement(SQL_CREATE_TABLE);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Migrations Complete");
    }

}
