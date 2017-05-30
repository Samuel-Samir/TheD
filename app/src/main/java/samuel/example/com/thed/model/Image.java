package samuel.example.com.thed.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samuel on 5/30/2017.
 */

public class Image {
    @SerializedName("link")
    private String link;
    @SerializedName("height")
    private Integer height ;
    @SerializedName("width")
    private Integer width ;

    public Image(String link, Integer height, Integer width) {
        this.link = link;
        this.height = height;
        this.width = width;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
