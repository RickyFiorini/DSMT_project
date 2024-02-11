package it.unipi.dsmt.app.daos;

import java.sql.Connection;

// Class to access listing info in the database
public class ListingDAO {
    private Connection listingConnection = null;

    public ListingDAO(Connection db) {
        listingConnection = db;
    }

    // TODO LISTING TABLE QUERY
}
