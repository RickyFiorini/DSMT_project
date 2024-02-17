package it.unipi.dsmt.app.dtos;

import java.sql.Timestamp;

// TODO MANCANO LE STATS DEL POKEMON
public class OfferDTO {
    private int offerID;
    private int listingID;
    private String trader;
    private String pokemonName;
    private String pokemonType;
    private boolean checked;
    private Timestamp timestamp;
    private String imageURL;

    public OfferDTO(int offerID, int listingID, String trader, String pokemonName, String pokemonType, boolean checked, Timestamp timestamp, String imageURL) {
        this.offerID = offerID;
        this.listingID = listingID;
        this.trader = trader;
        this.pokemonName = pokemonName;
        this.pokemonType = pokemonType;
        this.checked = checked;
        this.timestamp = timestamp;
        this.imageURL = imageURL;
    }

    public int getOfferID() {
        return offerID;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public int getListingID() {
        return listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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
        return "OfferDTO{" +
                "offerID=" + offerID +
                ", listingID=" + listingID +
                ", trader='" + trader + '\'' +
                ", pokemonName='" + pokemonName + '\'' +
                ", pokemonType='" + pokemonType + '\'' +
                ", checked=" + checked +
                ", timestamp=" + timestamp +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
