package it.unipi.dsmt.app.dtos;

// To represent user profile info that are transfered in the web app
public class UserProfileDTO {
    private String username;
    private String name;
    private String surname;

    public UserProfileDTO(String username, String name, String surname) {
        this.username = username;
        this.name = name;
        this.surname = surname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "{" +
                " username='" + getUsername() + "'" +
                ", name='" + getName() + "'" +
                ", surname='" + getSurname() + "'" +
                "}";
    }

}
