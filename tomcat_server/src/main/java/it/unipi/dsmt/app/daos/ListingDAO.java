package it.unipi.dsmt.app.daos;

import it.unipi.dsmt.app.dtos.ListingDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Class to access listing info in the database
public class ListingDAO {
    private Connection listingConnection = null;

    public ListingDAO(Connection db) {
        listingConnection = db;
    }

    // Get all the listings
    public List<ListingDTO> getListings() throws SQLException {
        ArrayList<ListingDTO> result = new ArrayList<>();
        String sqlString = "SELECT l.ID, l.winner, l.timestamp, l.username,p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
                "FROM listing l " +
                "JOIN box b ON l.boxID = b.ID " +
                "JOIN pokemon p ON b.pokemonID = p.ID";
        PreparedStatement statement = listingConnection.prepareStatement(sqlString);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            ListingDTO listing = new ListingDTO(set.getInt("ID"),set.getString("username"),set.getString("winner"), set.getTimestamp("timestamp"),set.getString("pokemonName"),set.getString("primaryType"),
                    set.getString("secondaryType"),set.getInt("attack"),set.getInt("defense"),set.getString("imageURL"));
            result.add(listing);
        }
        return result;
    }

    // Retrieve the listings of the current user
    public List<ListingDTO> getListingsByUsername(String username) throws SQLException {
        ArrayList<ListingDTO> result = new ArrayList<>();
        String sqlString = "SELECT l.ID, l.winner, l.timestamp, l.username, p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
                "FROM listing l " +
                "JOIN box b ON l.boxID = b.ID " +
                "JOIN pokemon p  ON b.pokemonID = p.ID "+
                "WHERE l.username= ? AND l.winner IS NULL ";
        PreparedStatement statement = listingConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            ListingDTO listing = new ListingDTO(set.getInt("ID"),username,set.getString("winner"), set.getTimestamp("timestamp"),set.getString("pokemonName"),set.getString("primaryType"),
                    set.getString("secondaryType"),set.getInt("attack"),set.getInt("defense"),set.getString("imageURL"));
            result.add(listing);
        }
        return result;

    }

    // Retrieve the selected listing info
    public ListingDTO getListingInfo(int listingID) throws SQLException {
        String sqlString = "SELECT l.ID, l.winner, l.timestamp, l.username, p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
                "FROM listing l " +
                "JOIN box b ON l.boxID = b.ID " +
                "JOIN pokemon p  ON b.pokemonID = p.ID "+
                "WHERE l.ID= ? ";
        PreparedStatement statement = listingConnection.prepareStatement(sqlString);
        statement.setInt(1, listingID);
        ResultSet set = statement.executeQuery();
        if(set.next()) {
            ListingDTO listing = new ListingDTO(set.getInt("ID"),set.getString("username"),set.getString("winner"), set.getTimestamp("timestamp"),set.getString("pokemonName"),set.getString("primaryType"),
                set.getString("secondaryType"),set.getInt("attack"),set.getInt("defense"),set.getString("imageURL"));
            return listing;
        }
        return null;
    }

}
