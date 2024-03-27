package it.unipi.dsmt.app.dtos;

// To represent box slot info that are transferred in the web app
public class BoxDTO {
    private int boxID;
    private String pokemonName;
    private String primaryType;
    private String secondaryType;
    private int attack;
    private int defense;
    private String imageURL;
    private boolean listed;

    private String username;

    public BoxDTO(int boxID,String username,String pokemonName,String primaryType,String secondaryType,int attack,int defense, String imageURL) {
        this.pokemonName=pokemonName;
        this.primaryType=primaryType;
        this.secondaryType=secondaryType;
        this.attack=attack;
        this.defense=defense;
        this.imageURL=imageURL;

        this.username = username;
        this.boxID=boxID;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
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

    public void setSecondaryType(String secondaryType) {
        this.secondaryType = secondaryType;
    }
    public String getSecondaryType() {
        return secondaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }
    public String getPrimaryType() {
        return primaryType;
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
        return "BoxDTO{" +
                "boxID=" + boxID +
                ", pokemonName='" + pokemonName + '\'' +
                ", primaryType='" + primaryType + '\'' +
                ", secondaryType='" + secondaryType + '\'' +
                ", attack='" + attack + '\'' +
                ", defense='" + defense + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", listed=" + listed +
                ", username='" + username + '\'' +
                '}';
    }
}
