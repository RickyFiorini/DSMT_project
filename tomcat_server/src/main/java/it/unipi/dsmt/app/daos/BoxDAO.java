package it.unipi.dsmt.app.daos;

import java.sql.Connection;

// Class to access user box info in the database
public class BoxDAO {
    private Connection boxConnection = null;

    public BoxDAO(Connection db) {
        boxConnection = db;
    }

    // TODO BOX TABLE QUERY
}
