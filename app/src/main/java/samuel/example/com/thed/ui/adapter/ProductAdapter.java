package samuel.example.com.thed.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import samuel.example.com.thed.R;
import samuel.example.com.thed.model.Product;
import samuel.example.com.thed.ui.fragment.ProductDetailsFragment;

/**
 * Created by samuel on 5/30/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.RecyclerViewAdapterHolder>{
    private  List<Product> productList ;
    private Activity myActivity;

    public void setApiResponse (List<Product> productList ,   Activity myActivity)
    {
        this.productList =productList;
        this.myActivity =myActivity;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        int layoutPhotoItem = R.layout.product_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutPhotoItem ,parent ,shouldAttachToParentImmediately);
        return new RecyclerViewAdapterHolder(view);    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterHolder holder, int position) {
        final Product product = productList.get(position);
        holder.productPrice.setText("$"+product.getPrice().toString());
        holder.productDescription.setText(product.getProductDescription());
        String imageUrl = product.getImage().getLink();
        imageUrl = imageUrl.replace("http", "https");
        final float scale = myActivity.getResources().getDisplayMetrics().density;
        int dpWidthInPx  = (int) (200 * scale);
        int dpHeightInPx = (int) (150 * scale);
          holder.productImage.getLayoutParams().height =  dpHeightInPx;
          holder.productImage.getLayoutParams().width =   dpWidthInPx;

        Picasso.with(myActivity)
                .load(imageUrl)
              /*  .placeholder(R.drawable.p1)
                .error(R.drawable.p1)*/
                .into(holder.productImage);

        holder.productDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                Bundle bundle =new Bundle();
                bundle.putParcelable("sasa",product);
                productDetailsFragment.setArguments(bundle);
                ((FragmentActivity)myActivity).getSupportFragmentManager()
                        .beginTransaction().addToBackStack("displayPhotoFragment").replace(R.id.container ,productDetailsFragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(productList!=null)
        {
            return productList.size();
        }
        return 0;
    }

    public class RecyclerViewAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView productImage  ;
        private TextView productPrice ;
        private TextView productDescription ;


        public RecyclerViewAdapterHolder(View itemView) {
            super(itemView);
            productImage= (ImageView) itemView.findViewById(R.id.product_image);
            productPrice= (TextView) itemView.findViewById(R.id.product_price);
            productDescription= (TextView) itemView.findViewById(R.id.product_descr);

        }
    }

}
