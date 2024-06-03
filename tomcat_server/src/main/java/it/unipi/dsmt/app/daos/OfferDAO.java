package it.unipi.dsmt.app.daos;

import it.unipi.dsmt.app.dtos.OfferDTO;

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
        String sqlString = "SELECT o.ID, o.boxID, b.pokemonID, o.trader, b.username, o.timestamp, p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
                "FROM offer o " +
                "JOIN box b ON o.boxID = b.ID " +
                "JOIN pokemon p  ON b.pokemonID = p.ID "+
                "WHERE listingID= ?  ";
        PreparedStatement statement = offerConnection.prepareStatement(sqlString);
        statement.setInt(1, listingID);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            OfferDTO offer = new OfferDTO(set.getInt("ID"),set.getInt("boxID"),set.getString("trader"),set.getString("username"),
                    set.getTimestamp("timestamp"),set.getString("pokemonName"),set.getString("primaryType"),
                    set.getString("secondaryType"), set.getInt("attack"),set.getInt("defense"),set.getString("imageURL"));
            result.add(offer);
        }
        return result;
    }

}
