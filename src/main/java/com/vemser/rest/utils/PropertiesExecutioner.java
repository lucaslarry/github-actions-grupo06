package com.vemser.rest.utils;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;

public class PropertiesExecutioner {
    private static Properties getProp() {
        Properties props = new Properties();
        try {
            FileInputStream file = new FileInputStream("src/main/resources/application.properties");
            props.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public static String getEmail(){
        return getProp().getProperty("email");
    }

    public static String getPwd(){
        return getProp().getProperty("password");
    }

    public static String getId(){
        return getProp().getProperty("id");
    }
}
