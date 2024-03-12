package it.unipi.dsmt.app.dtos;

// To represent pokemon info that are transfered in the web app
public class PokemonDTO {
    private int pokemonID;
    private String pokemonName;
    private String primaryType;
    private String secondaryType;
    private int attack;
    private int defense;
    private String imageURL;

    public PokemonDTO(String pokemonName, String primaryType,String secondaryType, String imageURL,int attack, int defense) {
        this.pokemonName = pokemonName;
        this.primaryType= primaryType;
        this.secondaryType=secondaryType;
        this.imageURL = imageURL;
        this.attack=attack;
        this.defense=defense;
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

    public int getAttack() {return attack;}

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {return defense;}

    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public String toString() {
        return "PokemonDTO{" +
                "pokemonID=" + pokemonID +
                ", pokemonName='" + pokemonName + '\'' +
                ", primaryType='" + primaryType + '\'' +
                ", secondaryType='" + secondaryType + '\'' +
                ", attack='" + attack + '\'' +
                ", defense='" + defense + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
