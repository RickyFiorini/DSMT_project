package it.unipi.dsmt.app.dtos;

// To represent notification info that are transfered in the web app
public class NotificationDTO {
    private int chatID;
    private int listingID;
    private String sender;
    private String pokemon;

    public NotificationDTO(int chatID, int listingID, String sender, String pokemon) {
        this.chatID = chatID;
        this.listingID = listingID;
        this.sender = sender;
        this.pokemon = pokemon;
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

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "chatID=" + chatID +
                ", listingID=" + listingID +
                ", sender='" + sender + '\'' +
                ", pokemon='" + pokemon + '\'' +
                '}';
    }
}
