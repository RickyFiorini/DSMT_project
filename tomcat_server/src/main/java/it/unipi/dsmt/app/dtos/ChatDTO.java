package it.unipi.dsmt.app.dtos;

import java.sql.Date;

// To represent chat info that are transfered in the web app
public class ChatDTO {
    private int id_chat;
    private String listingID;
    private String username;
    private Date lastMessageTime;
    private boolean onlineState;

    public ChatDTO(int id_chat, String listingID, String username, Date lastMessageTime, boolean onlineState) {
        this.id_chat = id_chat;
        this.listingID = listingID;
        this.username = username;
        this.lastMessageTime = lastMessageTime;
        this.onlineState = onlineState;
    }

    public int getId_chat() {
        return this.id_chat;
    }

    public void setId_chat(int id_chat) {
        this.id_chat = id_chat;
    }

    public String getListingID() {
        return listingID;
    }

    public void setListingID(String listingID) {
        this.listingID = listingID;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLastMessageTime() {
        return this.lastMessageTime;
    }

    public void setLastMessageTime(Date lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public boolean isOnlineState() {
        return this.onlineState;
    }

    public boolean getOnlineState() {
        return this.onlineState;
    }

    public void setOnlineState(boolean onlineState) {
        this.onlineState = onlineState;
    }

}
