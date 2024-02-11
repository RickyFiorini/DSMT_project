package it.unipi.dsmt.app.entities;

// Class for user box info
public class Box {
    private String username;
    private String pokemonName;
    private String pokemonType;
    private String imageUrl;

    public Box(String username, String pokemonName, String pokemonType, String imageUrl) {
        this.username = username;
        this.pokemonName = pokemonName;
        this.pokemonType = pokemonType;
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public String getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(String pokemonType) {
        this.pokemonType = pokemonType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Box{" +
                "username='" + username + '\'' +
                ", pokemonName='" + pokemonName + '\'' +
                ", pokemonType='" + pokemonType + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
