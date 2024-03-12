package it.unipi.dsmt.app.entities;

// Class for user box info

public class Box {
    private String username;
    private int pokemonID;
    private boolean listed;

    public Box(String username, int pokemonID, boolean listed) {
        this.username = username;
        this.pokemonID=pokemonID;
        this.listed = listed;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isListed() {
        return listed;
    }

    public void setListed(boolean listed) {
        this.listed = listed;
    }

    public int getPokemonID() {return pokemonID;}

    public void setPokemonID(int id) {
        this.pokemonID = id;
    }

    @Override
    public String toString() {
        return "Box{" +
                "username='" + username + '\'' +
                ", pokemonID='" + pokemonID + '\'' +
                ", listed='" + listed + '\'' +
                '}';
    }
}
