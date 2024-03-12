package it.unipi.dsmt.app.dtos;

// To represent box slot info that are transferred in the web app
public class BoxDTO {
    private int boxID;
    private int pokemonID;
    private boolean listed;
    private String username;

    public BoxDTO(String username, int pokemonID, boolean listed) {
        this.pokemonID = pokemonID;
        this.listed = listed;
        this.username = username;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }

    public int getPokemonID() {
        return pokemonID;
    }

    public void setPokemonID(int pokemonID) {
        this.pokemonID = pokemonID;
    }


    public boolean isListed() {
        return listed;
    }

    public void setListed(boolean listed) {
        this.listed = listed;
    }

    public String username() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "BoxDTO{" +
                "boxID=" + boxID +
                ", pokemonID='" + pokemonID + '\'' +
                ", listed=" + listed +
                ", username='" + username + '\'' +
                '}';
    }
}
