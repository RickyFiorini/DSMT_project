package it.unipi.dsmt.app.entities;

// Class to represent the pokemon info
public class Pokemon {
    private String pokemonName;
    private String imageURL;
    private String primaryType;
    private String secondaryType;
    private int attack;
    private int defense;



    public Pokemon(String pokemonName, String imageURL, String primaryType, String secondaryType, int attack, int defense) {
        this.pokemonName = pokemonName;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
        this.attack = attack;
        this.defense = defense;
        this.imageURL = imageURL;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }
    public String getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(String secondaryType) {
        this.secondaryType = secondaryType;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "pokemonName='" + pokemonName + '\'' +
                ", primaryType='" + primaryType + '\'' +
                ", secondaryType='" + secondaryType + '\'' +
                ", attack='" + attack + '\'' +
                ", defense='" + defense + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
