package it.unipi.dsmt.app.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import it.unipi.dsmt.app.dtos.UserProfileDTO;
import it.unipi.dsmt.app.entities.User;

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
    public boolean valid(String username, String password) throws SQLException {
        String sqlString = "SELECT password FROM user WHERE username=?";
        PreparedStatement statement = userConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        set.next();
        String storedPassword = set.getString(1);
        // encrypt password and return storedPassword.equals(encryptedPassword)
        return storedPassword.equals(password);
    }

    // Register a new user
    public String register(User userInfo) throws SQLException {
        try {
            String sqlString = "INSERT INTO user(username, password, name, surname, onlineFlag, creationTime) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = userConnection.prepareStatement(sqlString);
            statement.setString(1, userInfo.getUsername());
            statement.setString(2, userInfo.getPassword());
            statement.setString(3, userInfo.getName());
            statement.setString(4, userInfo.getSurname());
            statement.setBoolean(5, userInfo.getOnline_flag());
            statement.setDate(6, userInfo.getTimestamp());
            int changedCount = statement.executeUpdate();
            return changedCount == 0 ? "User already exists" : "";
        } catch (SQLIntegrityConstraintViolationException e) {
            return "User already exists";
        }
    }

    // Return the state of the user
    public boolean getOnlineStateOfUsername(String username) throws SQLException {
        String sqlString = "SELECT onlineFlag FROM user WHERE username=?";
        PreparedStatement statement = userConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        if (set.next())
            return set.getBoolean("onlineFlag");
        throw new SQLException("No Such Username");
    }

    // Set the user state on online
    public Boolean setOnlineFlag(Boolean flag, String username) throws SQLException {
        String sqlString = "UPDATE user SET onlineFlag = ? WHERE username = ?";
        PreparedStatement statement = userConnection.prepareStatement(sqlString);
        statement.setBoolean(1, flag);
        statement.setString(2, username);
        int changedCount = statement.executeUpdate();
        return changedCount == 1 ? true : false;
    }

    // Return user profile
    public UserProfileDTO getUserFromUsername(String username) throws SQLException {
        String sqlString = "SELECT name, surname, onlineFlag FROM user WHERE username=?";
        PreparedStatement statement = userConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        set.next();
        UserProfileDTO user = new UserProfileDTO(username, set.getString("name"), set.getString("surname"),
                set.getBoolean("onlineFlag"));
        return user;
    }

    /**
     * @deprecated
     */
    // Return user profile
    public ArrayList<UserProfileDTO> getUsersFromNameAndSurname(String name, String surname) throws SQLException {
        ArrayList<UserProfileDTO> result = new ArrayList<>();
        String sqlString = "SELECT username, onlineFlag FROM user WHERE name=? and surname=?";
        PreparedStatement statement = userConnection.prepareStatement(sqlString);
        statement.setString(1, name);
        statement.setString(2, surname);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            UserProfileDTO user = new UserProfileDTO(set.getString("username"), name, surname,
                    set.getBoolean("onlineFlag"));
            result.add(user);
        }
        return result;
    }

    // Return list of users profile
    public ArrayList<UserProfileDTO> getUsers() throws SQLException {
        ArrayList<UserProfileDTO> result = new ArrayList<>();
        String sqlString = "SELECT username, name, surname, onlineFlag FROM user ORDER BY onlineFlag DESC ";
        PreparedStatement statement = userConnection.prepareStatement(sqlString);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            UserProfileDTO user = new UserProfileDTO(set.getString("username"), set.getString("name"),
                    set.getString("surname"),
                    set.getBoolean("onlineFlag"));
            result.add(user);
        }
        return result;
    }

}
