package samuel.example.com.thed.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samuel on 5/30/2017.
 */

public class ProductResponse {

    @SerializedName("data")
    private List<Product> data;
    @SerializedName("count")
    private Integer count;

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
}
