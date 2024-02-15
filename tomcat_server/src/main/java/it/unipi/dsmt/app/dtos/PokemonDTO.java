package it.unipi.dsmt.app.dtos;

// To represent pokemon info that are transfered in the web app
public class PokemonDTO {
    private int pokemonID;
    private String pokemonName;
    private String pokemonType;
    private String imageURL;

    public PokemonDTO(int pokemonID, String pokemonName, String pokemonType, String imageURL) {
        this.pokemonID = pokemonID;
        this.pokemonName = pokemonName;
        this.pokemonType = pokemonType;
        this.imageURL = imageURL;
    }

    public int getPokemonID() {
        return pokemonID;
    }

    public void setPokemonID(int pokemonID) {
        this.pokemonID = pokemonID;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "PokemonDTO{" +
                "pokemonID=" + pokemonID +
                ", pokemonName='" + pokemonName + '\'' +
                ", pokemonType='" + pokemonType + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
