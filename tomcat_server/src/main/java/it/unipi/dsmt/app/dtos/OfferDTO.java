package it.unipi.dsmt.app.dtos;

import java.sql.Timestamp;

// To represent the offer info that are transferred in the web app
public class OfferDTO {
    private int offerID;
    private int boxID;
    private String username;
    private String pokemonName;
    private String primaryType;
    private String secondaryType;
    private int attack;
    private int defense;
    private String imageURL;
    private Timestamp timestamp;
    private String trader;

    public OfferDTO(int offerID,String trader, String username, Timestamp timestamp,String pokemonName,String primaryType,String secondaryType,int attack,int defense, String imageURL) {
        this.trader = trader;
        this.username = username;
        this.timestamp = timestamp;
        this.pokemonName=pokemonName;
        this.primaryType=primaryType;
        this.secondaryType=secondaryType;
        this.attack=attack;
        this.defense=defense;
        this.imageURL=imageURL;
        this.offerID=offerID;
    }
    public OfferDTO(int offerID,int boxID,String trader, String username, Timestamp timestamp,String pokemonName,String primaryType,String secondaryType,int attack,int defense, String imageURL) {
        this.trader = trader;
        this.username = username;
        this.timestamp = timestamp;
        this.pokemonName=pokemonName;
        this.primaryType=primaryType;
        this.secondaryType=secondaryType;
        this.attack=attack;
        this.defense=defense;
        this.imageURL=imageURL;
        this.offerID=offerID;
        this.boxID=boxID;
    }

    public int getOfferID() {
        return offerID;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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


    @Override
    public String toString() {
        return "OfferDTO{" +
                "offerID=" + offerID +
                ", trader='" + trader + '\'' +
                ", pokemonName='" + pokemonName + '\'' +
                ", primaryType='" + primaryType + '\'' +
                ", secondaryType='" + secondaryType + '\'' +
                ", attack='" + attack + '\'' +
                ", defense='" + defense + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", username='" + username + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
