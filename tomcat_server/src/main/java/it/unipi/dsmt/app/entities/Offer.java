package it.unipi.dsmt.app.entities;


import java.sql.Timestamp;

public class Offer {
    private int listingID;
    private int boxID;
    private String trader;  //TODO CHECK se serve
    private Timestamp timestamp;

    public Offer(int listingID, int boxID, String trader, Timestamp timestamp) {
        this.listingID = listingID;
        this.boxID = boxID;
        this.trader = trader;
        this.timestamp = timestamp;
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
                ", timestamp=" + timestamp +
                '}';
    }
}
