package it.unipi.dsmt.app.dtos;

import java.sql.Timestamp;

// To represent listing info that are transferred in the web app
public class ListingDTO {
    private int ID;
    private int pokemonID;
    private String username;
    private String winner;
    private Timestamp timestamp;

    public ListingDTO(int ID, int pokemonID, String username, String winner, Timestamp timestamp) {
        this.ID = ID;
        this.pokemonID = pokemonID;
        this.username = username;
        this.winner = winner;
        this.timestamp = timestamp;
    }


    public int getPokemonID() {
        return pokemonID;
    }

    public void setPokemonID(String pokemonType) {
        this.pokemonID = pokemonID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "ListingDTO{" +
                ", pokemonID='" + pokemonID + '\'' +
                ", username='" + username + '\'' +
                ", winner='" + winner + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}


