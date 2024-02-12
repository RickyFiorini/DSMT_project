package it.unipi.dsmt.app.entities;

import java.sql.Date;

// Class for the chat info
public class Chat {
    private String user1;
    private String user2;
    private int listingID;
    private Date timestamp;

    public Chat(String user1, String user2, int listingID, Date timestamp) {
        this.user1 = user1;
        this.user2 = user2;
        this.listingID = listingID;
        this.timestamp = timestamp;
    }

    public String getUser1() {
        return this.user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return this.user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public int getListingID() {
        return listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "user1='" + user1 + '\'' +
                ", user2='" + user2 + '\'' +
                ", listingID='" + listingID + '\'' +
                ", creationTime=" + timestamp +
                '}';
    }
}
