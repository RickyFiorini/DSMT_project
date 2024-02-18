package it.unipi.dsmt.app.entities;

// Class for user box info
// TODO AGGIORNARE LE ENTITIES/DTO CON LE NUOVE MODIFICHE FATTE NEL DISEGNO DELLE TABELLE
//  NELLA NUOVA VERSIONE, HO SOLO IL POKEMON ID, CHE USO PER PRENDERE LE ALTRE INFO DI UN POKEMON
public class Box {
    private String username;
    private String pokemonName;
    private String pokemonType;
    private boolean listed;
    private String imageUrl;

    public Box(String username, String pokemonName, String pokemonType, boolean listed, String imageUrl) {
        this.username = username;
        this.pokemonName = pokemonName;
        this.pokemonType = pokemonType;
        this.listed = listed;
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

    public boolean isListed() {
        return listed;
    }

    public void setListed(boolean listed) {
        this.listed = listed;
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
                ", listed=" + listed +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
