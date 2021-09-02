package com.evil.inc.taskman.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceProvider {

    private static String URL = "jdbc:postgresql://localhost:5432/task-manager";
    private static String DATABASE = "task-manager";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "123456";

    public static Connection getMysqlConnection() throws SQLException {
        return DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
    }
}
