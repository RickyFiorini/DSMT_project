package it.unipi.dsmt.app.daos;

import it.unipi.dsmt.app.dtos.BoxDTO;
import it.unipi.dsmt.app.dtos.PokemonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Class to access user box info in the database
public class BoxDAO {
    private Connection boxConnection = null;

    public BoxDAO(Connection db) {
        boxConnection = db;
    }

    // Retrieve pokemon's information of the current user
    public List<PokemonDTO> getPokemonBoxFromUsername(String username) throws SQLException {
        ArrayList<PokemonDTO> result = new ArrayList<>();
        String sqlString = "SELECT p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
                "FROM box b " +
                "JOIN pokemon p ON b.pokemonID = p.ID " +
                "WHERE b.username=?";
        PreparedStatement statement = boxConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            PokemonDTO pokemon = new PokemonDTO(set.getString("pokemonName"),set.getString("primaryType"),
                    set.getString("secondaryType"),set.getString("imageURL"), set.getInt("attack"),set.getInt("defense"));
            result.add(pokemon);
        }
        return result;
    }
    public List<BoxDTO> getBox(String username) throws SQLException {
        ArrayList<BoxDTO> result = new ArrayList<>();
        String sqlString = "SELECT pokemonID " +
                "FROM box " +
                "WHERE username= ? ";
        PreparedStatement statement = boxConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            BoxDTO box = new BoxDTO(set.getString("username"),set.getInt("pokemonID"),
                    set.getBoolean("listed"));
            result.add(box);
        }
        return result;
    }
    public List<PokemonDTO> getPokemonBoxFromUsernameByAttack(String username) throws SQLException {
        ArrayList<PokemonDTO> result = new ArrayList<>();
        String sqlString = "SELECT p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
                "FROM box b " +
                "JOIN pokemon p ON b.pokemonID = p.ID " +
                "WHERE b.username=? " +
                "ORDER BY p.attack DESC";
        PreparedStatement statement = boxConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            PokemonDTO pokemon = new PokemonDTO(set.getString("pokemonName"),set.getString("primaryType"),
                    set.getString("secondaryType"),set.getString("imageURL"), set.getInt("attack"),set.getInt("defense"));
            result.add(pokemon);
        }
        return result;
    }
    public List<PokemonDTO> getPokemonBoxFromUsernameByDefense(String username) throws SQLException {
        ArrayList<PokemonDTO> result = new ArrayList<>();
        String sqlString = "SELECT p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
                "FROM box b " +
                "JOIN pokemon p ON b.pokemonID = p.ID " +
                "WHERE b.username=? " +
                "ORDER BY p.defense DESC";
        PreparedStatement statement = boxConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            PokemonDTO pokemon = new PokemonDTO(set.getString("pokemonName"),set.getString("primaryType"),
                    set.getString("secondaryType"),set.getString("imageURL"), set.getInt("attack"),set.getInt("defense"));
            result.add(pokemon);
        }
        return result;
    }

    public List<PokemonDTO> getPokemonBoxFromUsernameByPrimaryType(String username,String primaryType) throws SQLException {
        ArrayList<PokemonDTO> result = new ArrayList<>();
        String sqlString = "SELECT p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
                "FROM box b " +
                "JOIN pokemon p ON b.pokemonID = p.ID " +
                "WHERE b.username=?  AND p.primaryType=? ";
        PreparedStatement statement = boxConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        statement.setString(2, primaryType);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            PokemonDTO pokemon = new PokemonDTO(set.getString("pokemonName"),set.getString("primaryType"),
                    set.getString("secondaryType"),set.getString("imageURL"), set.getInt("attack"),set.getInt("defense"));
            result.add(pokemon);
        }
        return result;
    }

    public List<PokemonDTO> getPokemonBoxFromUsernameBySecondaryType(String username,String secondaryType) throws SQLException {
        ArrayList<PokemonDTO> result = new ArrayList<>();
        String sqlString = "SELECT p.pokemonName, p.primaryType, p.secondaryType, p.attack, p.defense, p.imageURL " +
                "FROM box b " +
                "JOIN pokemon p ON b.pokemonID = p.ID " +
                "WHERE b.username=?  AND p.secondaryType=?  ";
        PreparedStatement statement = boxConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        statement.setString(2, secondaryType);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            PokemonDTO pokemon = new PokemonDTO(set.getString("pokemonName"),set.getString("primaryType"),
                    set.getString("secondaryType"),set.getString("imageURL"), set.getInt("attack"),set.getInt("defense"));
            result.add(pokemon);
        }
        return result;
    }

    // Retrieve boxID
    public int getPokemonBoxIdFromUsername(String username,int ID) throws SQLException {
        String sqlString = "SELECT ID " +
                "FROM box" +
                "WHERE username=? AND pokemonID=?";
        PreparedStatement statement = boxConnection.prepareStatement(sqlString);
        statement.setString(1, username);
        statement.setInt(2, ID);
        ResultSet set = statement.executeQuery();
        if (!set.next())
            throw new SQLException("Invalid parameter");
        return set.getInt("ID");
    }




}









