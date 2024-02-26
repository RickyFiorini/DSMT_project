package it.unipi.dsmt.app.daos;

import it.unipi.dsmt.app.dtos.OfferDTO;
import it.unipi.dsmt.app.entities.Offer;

import java.sql.Connection;
import java.util.List;

// Class to access offer info in the database
public class OfferDAO {
    private Connection offerConnection = null;

    public OfferDAO(Connection db) {
        offerConnection = db;
    }

    // TODO OFFER TABLE QUERY

    // Retrieve all the offers of the selected listing
    public List<OfferDTO> getOfferByListing(int listingID) {

    }

    // Retrieve the offer ID of the current user for the selected listing
    // If no offer is found, return -1
    public int getUserOfferByListing(String currentUsername, int listingID) {

    }

    // Check if the selected offer is the current user offer
    public void validateOfferIDByUsername(int offerID, String currentUsername) {

    }

    // Retrieve the user of the selected offer
    public String getUserByOfferID(int offerID) {

    }

    // Insert a new offer in the database
    public int insertOffer(Offer offer) {

    }

    // Update the selected offer:
    // update pokemon (boxID), set "checked" to false and update the timestamp
    public void updateOffer(int offerID, Offer offer) {

    }

    // Delete the selected offer from the database
    public void deleteUserOfferByID(int offerID) {

    }

}
