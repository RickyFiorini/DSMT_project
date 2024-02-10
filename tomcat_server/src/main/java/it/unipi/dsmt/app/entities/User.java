package it.unipi.dsmt.app.entities;

import java.sql.Date;

// Class for user info
public class User {

    private String username;
    private String password;
    private String name;
    private String surname;
    private Boolean online_flag;
    private Date creationTime;

    public User(String username, String password, String name, String surname, Boolean online_flag,
                Date creationTime) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.online_flag = online_flag;
        this.creationTime = creationTime;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean isOnline_flag() {
        return this.online_flag;
    }

    public Boolean getOnline_flag() {
        return this.online_flag;
    }

    public void setOnline_flag(Boolean online_flag) {
        this.online_flag = online_flag;
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "{" +
                " username='" + getUsername() + "'" +
                ", password='" + getPassword() + "'" +
                ", name='" + getName() + "'" +
                ", surname='" + getSurname() + "'" +
                ", online_flag='" + isOnline_flag() + "'" +
                ", creationTime='" + getCreationTime() + "'" +
                "}";
    }

}
