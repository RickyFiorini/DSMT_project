package it.unipi.dsmt.app.dtos;

import java.sql.Timestamp;

public class ListingDTO {
    private String pokemonName;
    private String pokemonType;
    private String username;
    private boolean status;
    private Timestamp timestamp;
    private String imageURL;

    public ListingDTO(String pokemonName, String pokemonType, String username, boolean status, Timestamp timestamp, String imageURL) {
        this.pokemonName = pokemonName;
        this.pokemonType = pokemonType;
        this.username = username;
        this.status = status;
        this.timestamp = timestamp;
        this.imageURL = imageURL;
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
