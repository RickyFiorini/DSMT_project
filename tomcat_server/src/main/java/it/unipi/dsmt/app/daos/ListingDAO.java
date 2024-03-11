package it.unipi.dsmt.app.daos;

import it.unipi.dsmt.app.dtos.ListingDTO;
import it.unipi.dsmt.app.entities.Listing;

import java.sql.Connection;
import java.util.List;

// Class to access listing info in the database
public class ListingDAO {
    private Connection listingConnection = null;

    public ListingDAO(Connection db) {
        listingConnection = db;
    }

    // TODO LISTING TABLE QUERY

    // Retrieve all the listings of the database
    public List<ListingDTO> getListings() {
        return null;
    }

    // Retrieve the listings of the current user
    public List<ListingDTO> getListingsByUsername(String currentUsername) {
        return null;
    }

    // Retrieve the selected listing info
    public ListingDTO getListingInfo(int listingID) {
        return null;
    }

    // Check if the current user is the owner of the selected listing
    public void validateListing(String currentUsername, int listingID) {

    }

    // Set the winner of the selected listing
    public void setWinner(int listingID, String winner) {

    }

    // Insert a new listing into the database
    public void insertListing(Listing listing) {

    }

    // Delete the selected listing from the database
    public void deleteListing(int listingID) {

    }

}
