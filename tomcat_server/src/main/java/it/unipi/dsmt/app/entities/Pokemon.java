package it.unipi.dsmt.app.entities;

// Class for pokemon info
public class Pokemon {
    private String name;
    private String imageURL;

    public Pokemon(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
