package it.unipi.dsmt.app.entities;

import java.sql.Timestamp;

// Class for listing info
// TODO NELLA NUOVA VERSIONE, HO SOLO IL BOX ID, CHE USO PER PRENDERE LE ALTRE INFO DEL POKEMON
public class Listing {
    private int boxID;
    private String pokemonName;
    private String pokemonType;
    private String username;
    private boolean status;
    private String winner;
    private Timestamp timestamp;
    private String imageURL;

    public Listing(int boxID, String pokemonName, String pokemonType, String username, boolean status, String winner, Timestamp timestamp, String imageURL) {
        this.boxID = boxID;
        this.pokemonName = pokemonName;
        this.pokemonType = pokemonType;
        this.username = username;
        this.status = status;
        this.winner = winner;
        this.timestamp = timestamp;
        this.imageURL = imageURL;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public String getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(String pokemonType) {
        this.pokemonType = pokemonType;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "boxID=" + boxID +
                ", pokemonName='" + pokemonName + '\'' +
                ", pokemonType='" + pokemonType + '\'' +
                ", username='" + username + '\'' +
                ", status=" + status +
                ", winner='" + winner + '\'' +
                ", timestamp=" + timestamp +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
