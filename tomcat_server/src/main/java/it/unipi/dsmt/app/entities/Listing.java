package it.unipi.dsmt.app.entities;

import java.sql.Timestamp;

// Class for listing info
public class Listing {
    private String pokemonID;
    private String username;
    private boolean status;
    private Timestamp creationTime;

    public Listing(String pokemonID, String username, boolean status, Timestamp creationTime) {
        this.pokemonID = pokemonID;
        this.username = username;
        this.status = status;
        this.creationTime = creationTime;
    }

    public String getPokemonID() {
        return pokemonID;
    }

    public void setPokemonID(String pokemonID) {
        this.pokemonID = pokemonID;
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

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "pokemonID='" + pokemonID + '\'' +
                ", username='" + username + '\'' +
                ", status=" + status +
                ", creationTime=" + creationTime +
                '}';
    }
}
