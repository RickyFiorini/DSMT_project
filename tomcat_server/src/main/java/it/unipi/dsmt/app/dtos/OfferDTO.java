package it.unipi.dsmt.app.dtos;

import java.sql.Timestamp;

public class OfferDTO {
    private int listingID;
    private String trader;
    private String pokemon;
    private boolean checked;
    private Timestamp timestamp;

    public OfferDTO(int listingID, String trader, String pokemon, boolean checked, Timestamp timestamp) {
        this.listingID = listingID;
        this.trader = trader;
        this.pokemon = pokemon;
        this.checked = checked;
        this.timestamp = timestamp;
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

    public String getPokemon() {
        return pokemon;
    }

    public void setPokemon(String pokemon) {
        this.pokemon = pokemon;
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

    @Override
    public String toString() {
        return "Offer{" +
                "listingID=" + listingID +
                ", trader='" + trader + '\'' +
                ", pokemon='" + pokemon + '\'' +
                ", checked=" + checked +
                ", timestamp=" + timestamp +
                '}';
    }
}
