package it.unipi.dsmt.app.dtos;

import java.sql.Timestamp;

// To represent message info that are transfered in the web app
public class MessageDTO {
    String content;
    String sender;
    Timestamp timestamp;

    public MessageDTO(String content, String sender, Timestamp timestamp) {
        this.content = content;
        this.sender = sender;
        this.timestamp = timestamp;
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

    @Override
    public String toString() {
        return "{" +
                " content='" + getContent() + "'" +
                ", sender='" + getSender() + "'" +
                ", creationTime='" + getTimestamp() + "'" +
                "}";
    }

}
