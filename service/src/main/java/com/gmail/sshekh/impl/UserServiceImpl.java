package com.gmail.sshekh.impl;

import com.gmail.sshekh.UserDao;
import com.gmail.sshekh.UserService;
import com.gmail.sshekh.connection.ConnectionService;
import com.gmail.sshekh.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private UserDao userDao = new UserDaoImpl();

    @Override
    public User save(User user) {
        Connection connection = ConnectionService.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            User savedUser = userDao.save(connection, user);
            connection.commit();
            return savedUser;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        try (Connection connection = ConnectionService.getInstance().getConnection()) {
            try {
                connection.setAutoCommit(false);
                User savedUser = userDao.findUserByEmail(connection, email);
                connection.commit();
                return savedUser;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        Connection connection = ConnectionService.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            List<User> users = userDao.findAll(connection);
            connection.commit();
            return users;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
