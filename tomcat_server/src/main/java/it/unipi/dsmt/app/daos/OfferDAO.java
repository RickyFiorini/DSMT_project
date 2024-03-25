package it.unipi.dsmt.app.daos;

import it.unipi.dsmt.app.dtos.ListingDTO;
import it.unipi.dsmt.app.dtos.OfferDTO;
import it.unipi.dsmt.app.entities.Offer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Class to access offer info in the database
public class OfferDAO {
    private Connection offerConnection = null;

    public OfferDAO(Connection db) {
        offerConnection = db;
    }

    // Retrieve all the offers of the selected listing
    public List<OfferDTO> getOfferByListing(int listingID) throws SQLException {
        ArrayList<OfferDTO> result = new ArrayList<>();
        String sqlString = "SELECT b.pokemonID, o.trader, b.username, o.checked, o.timestamp " +
                "FROM offer o " +
                "JOIN box b ON o.boxID = b.ID " +
                "WHERE listingID= ?  ";
        PreparedStatement statement = offerConnection.prepareStatement(sqlString);
        statement.setInt(1, listingID);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            OfferDTO offer = new OfferDTO(set.getInt("pokemonID"),set.getString("trader"),set.getString("username"),
                    set.getBoolean("checked"), set.getTimestamp("timestamp"));
            result.add(offer);
        }
        return result;

    }

    // Retrieve the offer of the current user for the selected listing
    public OfferDTO getUserOfferByListing(String currentUsername, int listingID) throws SQLException {
        String sqlString = "SELECT b.pokemonID, o.trader, b.username, o.checked, o.timestamp " +
                "FROM offer o " +
                "JOIN box b ON o.boxID = b.ID " +
                "WHERE o.listingID= ? AND b.username= ? ";
        PreparedStatement statement = offerConnection.prepareStatement(sqlString);
        statement.setInt(1, listingID);
        statement.setString(1, currentUsername);
        ResultSet set = statement.executeQuery();
        set.next();
        OfferDTO offer = new OfferDTO(set.getInt("pokemonID"),set.getString("trader"),set.getString("username"),
                set.getBoolean("checked"), set.getTimestamp("timestamp"));
        return offer;
    }

    // Check if the selected offer is the current user offer
    public void validateOfferIDByUsername(int offerID, String currentUsername) throws SQLException {
        String sqlString = "SELECT b.username " +
                "FROM offer o " +
                "JOIN box b ON o.boxID = b.ID " +
                "WHERE o.ID= ? AND b.username= ? ";
        PreparedStatement statement = offerConnection.prepareStatement(sqlString);
        statement.setInt(1, offerID);
        statement.setString(1, currentUsername);
        ResultSet set = statement.executeQuery();
        if (set.next())
            throw new SQLException("Invalid chat for username");

    }

    // Retrieve the user of the selected offer
    public String getUserByOfferID(int offerID) throws SQLException {
        String sqlString = "SELECT b.username " +
                "FROM offer o " +
                "JOIN box b ON o.boxID = b.ID " +
                "WHERE o.ID= ? ";
        PreparedStatement statement = offerConnection.prepareStatement(sqlString);
        statement.setInt(1, offerID);
        ResultSet set = statement.executeQuery();
        set.next();
        return set.getString(("username"));
    }

    // Insert a new offer in the database
    public String insertOffer(Offer offer) throws SQLException  {
        try {
            String sqlString = "INSERT INTO offer(listingID, boxID,trader,checked,timestamp) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = offerConnection.prepareStatement(sqlString);
            statement.setInt(1, offer.getListingID());
            statement.setInt(2, offer.getBoxID());
            statement.setString(3, offer.getTrader());
            statement.setBoolean(3, offer.isChecked());
            statement.setTimestamp(4, offer.getTimestamp());
            int changedCount = statement.executeUpdate();
            return changedCount == 0 ? "Offer not inserted" : "";
        } catch (SQLIntegrityConstraintViolationException e) {
            return "Offer not inserted";
        }
    }

    // Update the selected offer:
    // update pokemon (boxID), set "checked" to false and update the timestamp
    public boolean updateOffer(int offerID, Offer offer) throws SQLException {
        String sqlString = "UPDATE offer SET checked=? AND timestamp=? AND boxID=? WHERE ID = ?";
        PreparedStatement statement = offerConnection.prepareStatement(sqlString);
        statement.setBoolean(1, offer.isChecked());
        statement.setTimestamp(2, offer.getTimestamp());
        statement.setInt(3, offer.getBoxID());
        statement.setInt(4, offerID);
        int changedCount = statement.executeUpdate();
        return changedCount == 1 ? true : false;
    }

    // Delete the selected offer from the database
    public String deleteUserOfferByID(int offerID) throws SQLException {
        try {
            String sqlString = "DELETE FROM offer WHERE ID = ? ";
            PreparedStatement statement = offerConnection.prepareStatement(sqlString);
            statement.setInt(1, offerID);
            int changedCount = statement.executeUpdate();
            return changedCount == 0 ? "Offer not deleted" : "";
        } catch (SQLException e) {
            return "Offer not deleted";
        }

    }

}
