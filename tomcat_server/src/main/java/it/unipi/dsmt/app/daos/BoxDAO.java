package it.unipi.dsmt.app.daos;

import it.unipi.dsmt.app.dtos.BoxDTO;
import it.unipi.dsmt.app.dtos.PokemonDTO;

import java.sql.Connection;
import java.util.List;

// Class to access user box info in the database
public class BoxDAO {
    private Connection boxConnection = null;

    public BoxDAO(Connection db) {
        boxConnection = db;
    }

    // TODO BOX TABLE QUERY

    // Retrieve the box of the current user
    public List<BoxDTO> getBox(String username) {

    }

}
