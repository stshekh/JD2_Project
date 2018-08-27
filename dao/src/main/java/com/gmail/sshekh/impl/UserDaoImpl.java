package com.gmail.sshekh.impl;

import com.gmail.sshekh.UserDao;
import com.gmail.sshekh.model.RoleEnum;
import com.gmail.sshekh.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public User save(Connection connection, User user) {
        String insertTableSQL = "INSERT INTO T_USER(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ROLE) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                insertTableSQL,
                Statement.RETURN_GENERATED_KEYS
        )) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            return user;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserByEmail(Connection connection, String email) {
        String selectTableSQL = "SELECT * FROM T_USER WHERE EMAIL=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                selectTableSQL
        )) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
            //System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<User> findAll(Connection connection) {
        List<User> users = new ArrayList<>();
        String selectTableSQL = "SELECT * FROM T_USER";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                selectTableSQL
        )) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = getUser(resultSet);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
            //System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return users;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("ID"));
        user.setEmail(resultSet.getString("EMAIL"));
        user.setPassword(resultSet.getString("PASSWORD"));
        user.setFirstName(resultSet.getString("FIRST_NAME"));
        user.setLastName(resultSet.getString("LAST_NAME"));
        user.setRole(RoleEnum.valueOf(resultSet.getString("ROLE")));
        return user;
    }
}
