package it.unipi.dsmt.app.entities;

import java.sql.Timestamp;

// Class for the notification info
public class Notification {
    private int chatID;
    private int listingID;
    private String sender;
    private String pokemon;
    private Timestamp timestamp;

    public Notification(int chatID, int listingID, String sender, String pokemon, Timestamp timestamp) {
        this.chatID = chatID;
        this.listingID = listingID;
        this.sender = sender;
        this.pokemon = pokemon;
        this.timestamp = timestamp;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public int getListingID() {
        return listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPokemon() {
        return pokemon;
    }

    public void setPokemon(String pokemon) {
        this.pokemon = pokemon;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "chatID=" + chatID +
                ", listingID=" + listingID +
                ", sender='" + sender + '\'' +
                ", pokemon='" + pokemon + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
