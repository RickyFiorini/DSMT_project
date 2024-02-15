package it.unipi.dsmt.app.daos;

import java.sql.Connection;

// Class to access pokemon info in the database
public class PokemonDAO {
    private Connection offerConnection = null;

    public PokemonDAO(Connection db) {
        offerConnection = db;
    }

}
