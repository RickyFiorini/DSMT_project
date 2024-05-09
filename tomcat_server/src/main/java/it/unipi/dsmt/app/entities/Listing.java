package it.unipi.dsmt.app.entities;

import java.sql.Timestamp;

// Class for listing info
// TODO NELLA NUOVA VERSIONE, HO SOLO IL BOX ID, CHE USO PER PRENDERE LE ALTRE INFO DEL POKEMON
public class Listing {
    private int boxID;
    private int winnerID;
    private Timestamp timestamp;


    public Listing(int boxID, int winnerID, Timestamp timestamp) {
        this.boxID = boxID;
        this.winnerID = winnerID;
        this.timestamp = timestamp;

    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }

    public int getWinner() {
        return winnerID;
    }

    public void setWinner(int winner) {
        this.winnerID = winner;
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
                ", winner='" + winnerID + '\'' +
                ", timestamp=" + timestamp + '\'' +
                '}';
    }
}
