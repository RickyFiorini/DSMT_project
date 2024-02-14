package it.unipi.dsmt.app.entities;

// Class to represent the pokemon info
public class Pokemon {
    private String pokemonName;
    private String pokemonType;
    private String imageURL;

    public Pokemon(String pokemonName, String pokemonType, String imageURL) {
        this.pokemonName = pokemonName;
        this.pokemonType = pokemonType;
        this.imageURL = imageURL;
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
        return "Pokemon{" +
                "pokemonName='" + pokemonName + '\'' +
                ", pokemonType='" + pokemonType + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
