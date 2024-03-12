package it.unipi.dsmt.app.entities;

import it.unipi.dsmt.app.dtos.OfferDTO;

import java.sql.Timestamp;

public class Offer {
    private int offerID;
    private int listingID;
    private int boxID;
    private String trader;  //TODO CHECK se serve
    private boolean checked;
    private Timestamp timestamp;

    public Offer(int listingID, int boxID, String trader, boolean checked, Timestamp timestamp) {
        this.listingID = listingID;
        this.boxID = boxID;
        this.trader = trader;
        this.checked = checked;
        this.timestamp = timestamp;
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
                ", boxID=" + boxID +
                ", trader='" + trader + '\'' +
                ", checked=" + checked +
                ", timestamp=" + timestamp +
                '}';
    }
}
