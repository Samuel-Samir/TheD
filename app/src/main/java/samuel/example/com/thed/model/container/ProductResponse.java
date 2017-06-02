package samuel.example.com.thed.model.container;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samuel on 5/30/2017.
 */

public class ProductResponse implements Parcelable {

    @SerializedName("data")
    private List<Product> data;
    @SerializedName("count")
    private Integer count;

    public ProductResponse (){}
    protected ProductResponse(Parcel in) {
        data = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Creator<ProductResponse> CREATOR = new Creator<ProductResponse>() {
        @Override
        public ProductResponse createFromParcel(Parcel in) {
            return new ProductResponse(in);
        }

        @Override
        public ProductResponse[] newArray(int size) {
            return new ProductResponse[size];
        }
    };

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }
}
