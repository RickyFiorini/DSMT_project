package it.unipi.dsmt.app.entities;

import java.sql.Timestamp;

// Class for listing info
// TODO NELLA NUOVA VERSIONE, HO SOLO IL BOX ID, CHE USO PER PRENDERE LE ALTRE INFO DEL POKEMON
public class Listing {
    private int boxID;
    private String username;
    private String winner;
    private Timestamp timestamp;


    public Listing(int boxID, String username, String winner, Timestamp timestamp) {
        this.boxID = boxID;
        this.username = username;
        this.winner = winner;
        this.timestamp = timestamp;

    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
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
        return "Listing{" +
                "boxID=" + boxID +
                "username=" + username +
                ", winner='" + winner + '\'' +
                ", timestamp=" + timestamp + '\'' +
                '}';
    }
}
