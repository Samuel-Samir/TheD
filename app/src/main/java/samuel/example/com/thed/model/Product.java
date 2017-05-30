package samuel.example.com.thed.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samuel on 5/30/2017.
 */

public class Product {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("productDescription")
    private String productDescription;
    @SerializedName("image")
    private Image image;
    @SerializedName("price")
    private Double price;

    public Product(Integer id, String name, String productDescription, Image image, Double price) {
        this.id = id;
        this.name = name;
        this.productDescription = productDescription;
        this.image = image;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
