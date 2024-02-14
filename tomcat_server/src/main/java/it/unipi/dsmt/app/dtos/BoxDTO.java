package it.unipi.dsmt.app.dtos;

// To represent box slot info that are transferred in the web app
public class BoxDTO {
    private int boxID;
    private String pokemonName;
    private String pokemonType;
    private boolean listed;
    private String imageUrl;

    public BoxDTO(int boxID, String pokemonName, String pokemonType, boolean listed, String imageUrl) {
        this.boxID = boxID;
        this.pokemonName = pokemonName;
        this.pokemonType = pokemonType;
        this.listed = listed;
        this.imageUrl = imageUrl;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
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
        return "BoxDTO{" +
                "boxID=" + boxID +
                ", pokemonName='" + pokemonName + '\'' +
                ", pokemonType='" + pokemonType + '\'' +
                ", listed=" + listed +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
