package samuel.example.com.thed.model.container;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samuel on 5/30/2017.
 */

public class Product implements Parcelable{
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

    public Product (){}
    public Product(Integer id, String name, String productDescription, Image image, Double price) {
        this.id = id;
        this.name = name;
        this.productDescription = productDescription;
        this.image = image;
        this.price = price;
    }

    protected Product(Parcel in) {
        name = in.readString();
        productDescription = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(productDescription);
    }
}
