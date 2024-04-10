package it.unipi.dsmt.app.daos;

import it.unipi.dsmt.app.dtos.PokemonDTO;
import it.unipi.dsmt.app.dtos.UserProfileDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Class to access pokemon info in the database
public class PokemonDAO {
    private Connection pokemonConnection = null;

    public PokemonDAO(Connection db) {
        pokemonConnection = db;
    }

    //return pokemon attributes from name
    public PokemonDTO getPokemonFromName(String name) throws SQLException {
        String sqlString = "SELECT ID,primaryType,secondaryType,attack,defense,imageURL FROM pokemon WHERE PokemonName=?";
        PreparedStatement statement = pokemonConnection.prepareStatement(sqlString);
        statement.setString(1, name);
        ResultSet set = statement.executeQuery();
        if(set.next()) {
            PokemonDTO pokemon = new PokemonDTO(set.getInt("ID"), set.getString("pokemonName"), set.getString("primaryType"), set.getString("secondaryType"),
                    set.getString("imageURL"), set.getInt("attack"), set.getInt("defense"));
            return pokemon;
        }
        return null;
    }

    //return pokemon attributes from name
    public PokemonDTO getPokemonFromID(int ID) throws SQLException {
        String sqlString = "SELECT ID,PokemonName,primaryType,secondaryType,attack,defense,imageURL FROM pokemon WHERE ID=?";
        PreparedStatement statement = pokemonConnection.prepareStatement(sqlString);
        statement.setInt(1, ID);
        ResultSet set = statement.executeQuery();
       if(set.next()) {
           PokemonDTO pokemon = new PokemonDTO(set.getInt("ID"), set.getString("pokemonName"), set.getString("primaryType"), set.getString("secondaryType"),
                   set.getString("imageURL"), set.getInt("attack"), set.getInt("defense"));
           return pokemon;
       }
       return null;
    }






}
