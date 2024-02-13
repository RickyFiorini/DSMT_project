package it.unipi.dsmt.app.dtos;

import java.sql.Timestamp;

public class ListingDTO {
    private int listingID;
    private String pokemonName;
    private String pokemonType;
    private String username;
    private boolean status;
    private String winner;
    private Timestamp timestamp;
    private String imageURL;

    public ListingDTO(int listingID, String pokemonName, String pokemonType, String username, boolean status, String winner, Timestamp timestamp, String imageURL) {
        this.listingID = listingID;
        this.pokemonName = pokemonName;
        this.pokemonType = pokemonType;
        this.username = username;
        this.status = status;
        this.winner = winner;
        this.timestamp = timestamp;
        this.imageURL = imageURL;
    }

    public int getListingID() {
        return listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
