package it.unipi.dsmt.app.daos;

import it.unipi.dsmt.app.dtos.ListingDTO;
import it.unipi.dsmt.app.dtos.PokemonDTO;
import it.unipi.dsmt.app.entities.Listing;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Class to access listing info in the database
public class ListingDAO {
    private Connection listingConnection = null;

    public ListingDAO(Connection db) {
        listingConnection = db;
    }

    // Retrieve all the listings of the database
    public List<ListingDTO> getListings() throws SQLException {
        ArrayList<ListingDTO> result = new ArrayList<>();
        String sqlString = "SELECT l.ID, l.winner, l.timestamp, b.username,p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
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
        String sqlString = "SELECT l.ID, l.winner, l.timestamp, b.username, p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
                "FROM listing l " +
                "JOIN box b ON l.boxID = b.ID " +
                "JOIN pokemon p  ON b.pokemonID = p.ID "+
                "WHERE b.username= ? ";
        PreparedStatement statement = listingConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            for (int i = 1; i <= set.getMetaData().getColumnCount(); i++) {
                String columnName = set.getMetaData().getColumnName(i);
                Object value = set.getObject(i);
                System.out.println(columnName + ": " + value);
            }
            System.out.println();
            ListingDTO listing = new ListingDTO(set.getInt("ID"),username,set.getString("winner"), set.getTimestamp("timestamp"),set.getString("pokemonName"),set.getString("primaryType"),
                    set.getString("secondaryType"),set.getInt("attack"),set.getInt("defense"),set.getString("imageURL"));
            result.add(listing);
            System.out.print(set.getInt("ID")+"ID");
        }
        return result;

    }

    // Retrieve the selected listing info
    public ListingDTO getListingInfo(int listingID) throws SQLException {
        String sqlString = "SELECT l.ID, l.winner, l.timestamp, b.username, p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
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

    // Check if the current user is the owner of the selected listing
    public boolean validateListing(String currentUsername, int listingID)  throws SQLException {
        boolean ret = false;
        String sqlString = "SELECT l.ID, l.winner, l.timestamp, b.username, b.pokemonID " +
                "FROM listing l " +
                "JOIN box b ON l.boxID = b.ID " +
                "WHERE l.ID=? AND b.username=?";
        PreparedStatement statement = listingConnection.prepareStatement(sqlString);
        statement.setInt(1, listingID);
        statement.setString(2, currentUsername);
        ResultSet set = statement.executeQuery();
        if (set.next())
            return true;
        throw new SQLException("No listing for this user");
    }




    // Set the winner of the selected listing
    public boolean setWinner(int listingID, String winner) throws SQLException {
        String sqlString = "UPDATE listing SET winner = ? WHERE ID = ?";
        PreparedStatement statement = listingConnection.prepareStatement(sqlString);
        statement.setInt(1, listingID);
        statement.setString(2, winner);
        int changedCount = statement.executeUpdate();
        return changedCount == 1 ? true : false;
    }

    // Insert a new listing into the database
    public String insertListing(Listing listing) throws SQLException  {
        try {
            String sqlString = "INSERT INTO listing(boxID, winner, timestamp) VALUES (?, ?, ?)";
            PreparedStatement statement = listingConnection.prepareStatement(sqlString);
            statement.setInt(1, listing.getBoxID());
            statement.setString(2, listing.getWinner());
            statement.setTimestamp(3, listing.getTimestamp());
            int changedCount = statement.executeUpdate();
            return changedCount == 0 ? "Listing not inserted" : "";
        } catch (SQLIntegrityConstraintViolationException e) {
            return "Listing not inserted";
        }

    }

    // Delete the selected listing from the database
    public String deleteListing(int listingID) throws SQLException {
        try {
            String sqlString = "DELETE FROM listing WHERE ID = ?";
            PreparedStatement statement = listingConnection.prepareStatement(sqlString);
            statement.setInt(1, listingID);
            int changedCount = statement.executeUpdate();
            return changedCount == 0 ? "Listing not deleted" : "";
        } catch (SQLException e) {
            return "Listing not deleted";
        }
    }




}
