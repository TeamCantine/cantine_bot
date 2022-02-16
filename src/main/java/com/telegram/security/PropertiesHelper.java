package com.telegram.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {

    static Properties prop = new Properties();

    private static void loadDbConfig(){

        if (prop.isEmpty())

        try (InputStream input = PropertiesHelper.class.getResourceAsStream("/config.properties")){

            // load a properties file
            prop.load(input);



        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static String getDatabaseAddress(){
        return prop.getProperty("database");
    }
    public static String getDatabaseUser(){
        return prop.getProperty("dbuser");
    }
    public static String getDatabasePassword(){
        return prop.getProperty("dbpassword");
    }

    public static void main(String[] args) {
        loadDbConfig();
        // get the property value and print it out
        System.out.println(prop.getProperty("database"));
        System.out.println(prop.getProperty("dbuser"));
        System.out.println(prop.getProperty("dbpassword"));
    }

}
