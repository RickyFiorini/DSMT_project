package it.unipi.dsmt.app.dtos;

import java.sql.Timestamp;

public class OfferDTO {
    private int offerID;
    private int pokemonID;
    private String username;
    private boolean checked;
    private Timestamp timestamp;
    private String trader;

    public OfferDTO(int offerID, int pokemonID, String trader, String username, boolean checked, Timestamp timestamp) {
        this.offerID = offerID;
        this.pokemonID = pokemonID;
        this.trader = trader;
        this.username = username;
        this.checked = checked;
        this.timestamp = timestamp;
    }

    public int getOfferID() {
        return offerID;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public int getPokemonID() {
        return pokemonID;
    }

    public void setPokemonID(int pokemonID) {
        this.pokemonID = pokemonID;
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
        return "OfferDTO{" +
                "offerID=" + offerID +
                ", pokemonID=" + pokemonID +
                ", trader='" + trader + '\'' +
                ", username='" + username + '\'' +
                ", checked=" + checked +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
