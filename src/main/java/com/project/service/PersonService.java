package com.project.service;

import com.project.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonService {

    public List<Person> findAll() {


        try (Connection conn = HikariCPDataSource.getConnection(); Statement stmt = conn.createStatement()) {


            String SQL_QUERY = "select * from person";
            List<Person> personList = null;
            ResultSet rs = stmt.executeQuery(SQL_QUERY);
            personList = new ArrayList<>();
            Person p;
            while (rs.next()) {
                p = new Person();
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setLastName(rs.getString("last_name"));
                p.setAge(rs.getInt("age"));
                personList.add(p);
            }


            return personList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Person createPerson(Person p) {

        try (Connection conn = HikariCPDataSource.getConnection(); Statement stmt = conn.createStatement()) {


            String SQL_QUERY = "insert into person values('" + p.getId() + "','" + p.getName() + "','" + p.getLastName() + "'," + p.getAge() + ");";

            stmt.executeUpdate(SQL_QUERY);

            return p;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Integer updatePerson(String identifier, Person p) {

        try (Connection conn = HikariCPDataSource.getConnection(); Statement stmt = conn.createStatement()) {

            String SQL_QUERY = "update person set name='" + p.getName() + "', last_name = '" + p.getLastName() + "', age = " + p.getAge() + " where id = '" + identifier + "'";

            return stmt.executeUpdate(SQL_QUERY);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Integer deletePerson(String identifier) {

        try (Connection conn = HikariCPDataSource.getConnection(); Statement stmt = conn.createStatement()) {

            String SQL_QUERY = "delete from person where id='" + identifier + "'";
            return stmt.executeUpdate(SQL_QUERY);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Integer deleteAllFromPerson() {

        try (Connection conn = HikariCPDataSource.getConnection(); Statement stmt = conn.createStatement()) {

            String SQL_QUERY = "delete from person";
            return stmt.executeUpdate(SQL_QUERY);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String> verify(Person p) {

        List<String> m = new ArrayList<>();


        if (p.getId() == null || p.getId().isEmpty()) {
            p.setId(UUID.randomUUID().toString());
        }

        if (p.getName() == null || p.getName().isEmpty()) {
            m.add("Nome não pode ser vazio");
        }

        if (p.getLastName() == null || p.getLastName().isEmpty()) {
            m.add("Sobrenome não pode ser vazio");
        }

        if (p.getAge() == null) {
            m.add("Idade não pode ser vazio");
        }

        return m;
    }
}
