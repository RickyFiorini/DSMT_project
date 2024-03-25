package it.unipi.dsmt.app.entities;

import java.sql.Timestamp;

// Class for listing info
// TODO NELLA NUOVA VERSIONE, HO SOLO IL BOX ID, CHE USO PER PRENDERE LE ALTRE INFO DEL POKEMON
public class Listing {
    private int boxID;
    private boolean status_listing;
    private String winner;
    private Timestamp timestamp;


    public Listing(int boxID,boolean status, String winner, Timestamp timestamp) {
        this.boxID = boxID;
        this.status_listing = status;
        this.winner = winner;
        this.timestamp = timestamp;

    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }

    public boolean isStatus_listing() {
        return status_listing;
    }

    public void setStatus_listing(boolean status) {
        this.status_listing = status;
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
        return "Listing{" +
                "boxID=" + boxID +
                ", status=" + status_listing +
                ", winner='" + winner + '\'' +
                ", timestamp=" + timestamp + '\'' +
                '}';
    }
}
