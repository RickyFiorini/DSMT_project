package it.unipi.dsmt.app.dtos;

import java.sql.Timestamp;

// To represent listing info that are transferred in the web app
public class ListingDTO {
    private int ID;
    private int pokemonID;
    private String username;
    private boolean status;
    private String winner;
    private Timestamp timestamp;

    public ListingDTO(int pokemonID, String username, boolean status, String winner, Timestamp timestamp) {
        this.pokemonID=pokemonID;
        this.username = username;
        this.status = status;
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

    public boolean isStatus() {
        return status;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
                ", status=" + status +
                ", winner='" + winner + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}


