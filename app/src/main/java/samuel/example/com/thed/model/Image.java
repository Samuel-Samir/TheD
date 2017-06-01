package samuel.example.com.thed.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samuel on 5/30/2017.
 */

public class Image implements Parcelable {
    @SerializedName("link")
    private String link;
    @SerializedName("height")
    private Integer height ;
    @SerializedName("width")
    private Integer width ;

    public Image (){};
    public Image(String link, Integer height, Integer width) {
        this.link = link;
        this.height = height;
        this.width = width;
    }

    protected Image(Parcel in) {
        link = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(link);
    }
}
