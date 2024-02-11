package it.unipi.dsmt.app.entities;

import java.sql.Timestamp;

// Class for the message info
public class Message {
    private String sender;
    private String content;
    private int chatID;
    private Timestamp timestamp;

    public Message(String content, String sender, Timestamp timestamp, int chatID) {
        this.content = content;
        this.sender = sender;
        this.timestamp = timestamp;
        this.chatID = chatID;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getChatID() {
        return this.chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

}
