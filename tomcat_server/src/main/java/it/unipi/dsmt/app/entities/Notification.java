package it.unipi.dsmt.app.entities;

import java.sql.Timestamp;

// Class for the notification info
public class Notification {
    private String user;
    private String sender;
    private int chatID;
    private Timestamp timestamp;

    public Notification(String user, String sender, int chatID, Timestamp timestamp) {
        this.user = user;
        this.sender = sender;
        this.chatID = chatID;
        this.timestamp = timestamp;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getChatID() {
        return this.chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{" +
                " user='" + getUser() + "'" +
                ", sender='" + getSender() + "'" +
                ", chatID='" + getChatID() + "'" +
                ", creationTime='" + getTimestamp() + "'" +
                "}";
    }

}
