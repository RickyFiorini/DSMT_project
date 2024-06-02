package it.unipi.dsmt.app.daos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import it.unipi.dsmt.app.dtos.UserProfileDTO;
import it.unipi.dsmt.app.entities.User;
import it.unipi.dsmt.app.utils.AccessController;

// To access the user info in the database
public class UserDAO {
    private Connection userConnection = null;

    public UserDAO(Connection db) {
        userConnection = db;
    }

    // TODO CHECK METHODS AND DELETE THE UNUSED ONES

    // Check if the user exists in the db
    public boolean exists(String username) throws SQLException {
        String sqlString = "SELECT username FROM user WHERE username=?";
        PreparedStatement statement = userConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        return set.next();
    }

    // Check the user password and return it
    public boolean valid(String username, String password) throws SQLException, NoSuchAlgorithmException {
        String sqlString = "SELECT password FROM user WHERE username=?";
        PreparedStatement statement = userConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        set.next();
        String storedPassword = set.getString(1);
        return storedPassword.equals(password);
    }

    // Register a new user
    public String register(User userInfo) throws SQLException {
        try {
            String sqlString = "INSERT INTO user(username, password, name, surname, timestamp) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = userConnection.prepareStatement(sqlString);
            statement.setString(1, userInfo.getUsername());
            statement.setString(2, userInfo.getPassword());
            statement.setString(3, userInfo.getName());
            statement.setString(4, userInfo.getSurname());
            statement.setDate(5, userInfo.getTimestamp());
            int changedCount = statement.executeUpdate();
            return changedCount == 0 ? "User already exists" : "";
        } catch (SQLIntegrityConstraintViolationException e) {
            return "User already exists";
        }
    }

    // Return user profile
    public UserProfileDTO getUserFromUsername(String username) throws SQLException {
        String sqlString = "SELECT name, surname FROM user WHERE username=? ";
        PreparedStatement statement = userConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        set.next();
        UserProfileDTO user = new UserProfileDTO(username, set.getString("name"), set.getString("surname"));
        return user;
    }

    /**
     * @deprecated
     */
    // Return user profile
    public ArrayList<UserProfileDTO> getUsersFromNameAndSurname(String name, String surname) throws SQLException {
        ArrayList<UserProfileDTO> result = new ArrayList<>();
        String sqlString = "SELECT username, FROM user WHERE name=? and surname=?";
        PreparedStatement statement = userConnection.prepareStatement(sqlString);
        statement.setString(1, name);
        statement.setString(2, surname);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            UserProfileDTO user = new UserProfileDTO(set.getString("username"), name, surname);
            result.add(user);
        }
        return result;
    }

    // Return list of users profile
    public ArrayList<UserProfileDTO> getUsers() throws SQLException {
        ArrayList<UserProfileDTO> result = new ArrayList<>();
        String sqlString = "SELECT username, name, surname, online_flag FROM user ORDER BY online_flag DESC ";
        PreparedStatement statement = userConnection.prepareStatement(sqlString);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            UserProfileDTO user = new UserProfileDTO(set.getString("username"), set.getString("name"),
                    set.getString("surname"));
            result.add(user);
        }
        return result;
    }


}
