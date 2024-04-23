package it.unipi.dsmt.app.entities;

import java.sql.Timestamp;

// Class for listing info
public class Listing {
    private int boxID;
    private String winner;
    private Timestamp timestamp;


    public Listing(int boxID, String winner, Timestamp timestamp) {
        this.boxID = boxID;
        this.winner = winner;
        this.timestamp = timestamp;

    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
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
                ", winner='" + winner + '\'' +
                ", timestamp=" + timestamp + '\'' +
                '}';
    }
}
