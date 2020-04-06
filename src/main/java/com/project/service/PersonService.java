package com.project.service;

import com.project.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonService {

    public List<Person> findAll() {


        try {

            String SQL_QUERY = "select * from person";
            List<Person> personList = null;

            Connection conn = HikariCPDataSource.getConnection();
            PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
            ResultSet rs = pst.executeQuery();
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Person createPerson(Person p) {
        try {

            String SQL_QUERY = "insert into person values('" + p.getId() + "','" + p.getName() + "','" + p.getLastName() + "'," + p.getAge() + ");";
            Connection conn = HikariCPDataSource.getConnection();
            PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
            pst.execute();

            return p;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Integer updatePerson(String identifier, Person p) {

        try {

            String SQL_QUERY = "update person set name='" + p.getName() + "', last_name = '" + p.getLastName() + "', age = " + p.getAge() + " where id = '" + identifier + "'";
            Integer rowsAffected = 0;

            Connection conn = HikariCPDataSource.getConnection();
            PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
            return pst.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Integer deletePerson(String identifier) {

        try {

            String SQL_QUERY = "delete from person where id='" + identifier + "'";

            Integer rowsAffected = 0;

            Connection conn = HikariCPDataSource.getConnection();
            PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
            return pst.executeUpdate();


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
