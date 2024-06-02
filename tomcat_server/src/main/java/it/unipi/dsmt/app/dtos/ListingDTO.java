package it.unipi.dsmt.app.dtos;

import java.sql.Timestamp;

// To represent listing info that are transferred in the web app
public class ListingDTO {
    private int listingID;
    private String pokemonName;
    private String primaryType;
    private String secondaryType;
    private int attack;
    private int defense;
    private String imageURL;
    private String username;
    private String winner;
    private Timestamp timestamp;

    public ListingDTO(int listingID,String username,String winner, Timestamp timestamp,String pokemonName,String primaryType,String secondaryType,int attack,int defense, String imageURL) {
        this.username = username;
        this.winner = winner;
        this.timestamp = timestamp;
        this.pokemonName=pokemonName;
        this.primaryType=primaryType;
        this.secondaryType=secondaryType;
        this.attack=attack;
        this.defense=defense;
        this.imageURL=imageURL;
        this.listingID=listingID;
    }



    public void setSecondaryType(String secondaryType) {
        this.secondaryType = secondaryType;
    }
    public String getSecondaryType() {
        return secondaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }
    public String getPrimaryType() {
        return primaryType;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getID() {
        return listingID;
    }

    public void setListingID(int id) {
        this.listingID = id;
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
                ", username='" + username + '\'' +
                ", pokemonName='" + pokemonName + '\'' +
                ", primaryType='" + primaryType + '\'' +
                ", secondaryType='" + secondaryType + '\'' +
                ", attack='" + attack + '\'' +
                ", defense='" + defense + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", winner='" + winner + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}


