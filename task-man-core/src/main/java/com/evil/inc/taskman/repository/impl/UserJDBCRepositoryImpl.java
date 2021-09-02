package com.evil.inc.taskman.repository.impl;

import com.evil.inc.taskman.entity.Task;
import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.repository.DataSourceProvider;
import com.evil.inc.taskman.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserJDBCRepositoryImpl implements UserRepository {


    private static final String DELETE_USER = "DELETE FROM users WHERE id=?";
    private static final String SELECT_USERS_BY_USER_NAME = "SELECT * FROM users WHERE userName=?";
    private static final String SELECT_USERS = "SELECT * FROM users ORDER BY id";
    public static final String SELECT_TASKS_BY_USER_ID = "SELECT * FROM tasks WHERE user_id = ?";


    public static UserJDBCRepositoryImpl INSTANCE;

    private UserJDBCRepositoryImpl() {
    }

    public static UserJDBCRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserJDBCRepositoryImpl();
        }

        return INSTANCE;
    }


    @Override
    public void saveUser(User user) {
        try (Connection connection = DataSourceProvider.getMysqlConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO users(firstName, lastName, userName) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {

                if (generatedKeys.next())
                    user.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            log.error("Something bad happened during fetching user = {} ", user, e);
        }
    }


    @Override
    public Optional<User> findByUsername(String username) {

        Optional<User> result;
        try (Connection connection = DataSourceProvider.getMysqlConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USERS_BY_USER_NAME);
             PreparedStatement ps2 = connection.prepareStatement(SELECT_TASKS_BY_USER_ID)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                final User user = new User(rs.getString("first_name"),
                                           rs.getString("last_name"),
                                           rs.getString("user_name"));
                user.setId(rs.getLong("id"));
                result = Optional.of(user);
                ps2.setLong(1, user.getId());

                try (ResultSet r2 = ps2.executeQuery()) {
                    while (r2.next()) {
                        Task task = new Task(r2.getString("title"),
                                             r2.getString("description"));
                        task.setId(r2.getLong("id"));
                        user.addTask(task);
                    }
                }
                return result;
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getMysqlConnection();
             PreparedStatement ps1 = connection.prepareStatement(SELECT_USERS);
             PreparedStatement ps2 = connection.prepareStatement(SELECT_TASKS_BY_USER_ID)) {

            try (ResultSet r = ps1.executeQuery()) {
                while (r.next()) {
                    User user = new User(r.getString("first_name"),
                                         r.getString("last_name"),
                                         r.getString("user_name"));
                    user.setId(r.getLong("id"));
                    users.add(user);
                }
            }

            for (User user : users) {
                ps2.setLong(1, user.getId());
                try (ResultSet r2 = ps2.executeQuery()) {
                    while (r2.next()) {
                        Task task = new Task(r2.getString("title"),
                                             r2.getString("description"));
                        task.setId(r2.getLong("id"));
                        user.addTask(task);
                    }

                }
            }

        } catch (SQLException e) {
            log.error("Something bad happened during fetching users = {} ", users, e);
        }
        return users;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void deleteUserById(Long id) {
        try (Connection connection = DataSourceProvider.getMysqlConnection();
             PreparedStatement ps1 = connection.prepareStatement(DELETE_USER)) {
            ps1.setLong(1, id);
            ps1.executeUpdate();
        } catch (SQLException e) {
            log.error("Something bad happened during fetching a user with id = {} ", id, e);
        }
    }


}
