package it.unipi.dsmt.app.daos;

import it.unipi.dsmt.app.dtos.BoxDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Class to access user box info in the database
public class BoxDAO {
    private Connection boxConnection = null;

    public BoxDAO(Connection db) {
        boxConnection = db;
    }

    // Retrieve pokemon's information of the current user
    public List<BoxDTO> getBox(String username) throws SQLException {
        ArrayList<BoxDTO> result = new ArrayList<>();
        String sqlString = "SELECT b.ID,b.username, b.listed, p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
                "FROM box b " +
                "JOIN pokemon p ON b.pokemonID = p.ID " +
                "WHERE b.username=? ";
        PreparedStatement statement = boxConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            BoxDTO box = new BoxDTO(set.getInt("ID"),set.getString("username"),set.getString("pokemonName"),set.getString("primaryType"),
                    set.getString("secondaryType"),set.getInt("attack"),set.getInt("defense"),set.getString("imageURL"),set.getBoolean("listed"));
            result.add(box);
        }
        return result;
    }
}









