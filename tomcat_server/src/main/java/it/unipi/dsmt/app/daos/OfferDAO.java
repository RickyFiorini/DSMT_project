package it.unipi.dsmt.app.daos;

import java.sql.Connection;

// Class to access offer info in the database
public class OfferDAO {
    private Connection offerConnection = null;

    public OfferDAO(Connection db) {
        offerConnection = db;
    }

    // TODO OFFER TABLE QUERY
}
