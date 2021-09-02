package com.evil.inc.taskman.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceProvider {

    private static String URL = "jdbc:postgresql://localhost:3306/";
    private static String DATABASE = "task-man";
    private static String USERNAME = "root";
    private static String PASSWORD = "mysqleight";

    public static Connection getMysqlConnection() throws SQLException {
        return DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
    }
}
