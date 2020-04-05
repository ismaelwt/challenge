package com.project.properties;

import com.project.Server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {


    public static Properties properties;



    public PropertiesReader() {

        try {

            properties = new Properties();
            InputStream stream = Server.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(stream);

            PrintProps();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void PrintProps (){


        System.out.println(PropertiesReader.properties.getProperty("db.driverClassName"));
        System.out.println(PropertiesReader.properties.getProperty("db.url"));
        System.out.println(PropertiesReader.properties.getProperty("db.username"));
        System.out.println(PropertiesReader.properties.getProperty("db.password"));

    }

}
