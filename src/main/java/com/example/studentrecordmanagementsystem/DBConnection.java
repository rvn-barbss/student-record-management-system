package com.example.studentrecordmanagementsystem;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    public static Connection connect() {
        try {
            Properties props = new Properties();
            FileInputStream fileInputStream = new FileInputStream(".env");
            props.load(fileInputStream);
            fileInputStream.close();


            String dbUrl = props.getProperty("DB_URL");
            String dbUser = props.getProperty("DB_USER");
            String dbPassword = props.getProperty("DB_PASSWORD");


            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);

        } catch (Exception e) {
            System.out.println("Database Connection Failed! Check your .env file.");
            e.printStackTrace();
            return null;
        }
    }
}