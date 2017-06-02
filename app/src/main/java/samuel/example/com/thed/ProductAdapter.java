package samuel.example.com.thed;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import samuel.example.com.thed.model.container.Product;

/**
 * Created by samuel on 5/30/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.RecyclerViewAdapterHolder>{
    private  List<Product> productList ;
    private RecyclerViewCallback  recyclerViewCallback;

    public void setApiResponse (List<Product> productList )
    {
        this.productList =productList;
        notifyDataSetChanged();
    }

    public void setRecyclerViewCallback(RecyclerViewCallback recyclerViewCallback) {
        this.recyclerViewCallback = recyclerViewCallback;
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
    public void onBindViewHolder(RecyclerViewAdapterHolder holder, final int position) {
        final Product product = productList.get(position);
        holder.productPrice.setText("$"+product.getPrice().toString());
        holder.productDescription.setText(product.getProductDescription());
        String imageUrl = product.getImage().getLink();
        imageUrl = imageUrl.replace("http", "https");

        final float scale = holder.context.getResources().getDisplayMetrics().density;
        int dpWidthInPx  = (int) (200 * scale);
        int dpHeightInPx = (int) (150 * scale);
        holder.productImage.getLayoutParams().height =  dpHeightInPx;
        holder.productImage.getLayoutParams().width =   dpWidthInPx;

        Picasso.with(holder.context)
                .load(imageUrl)
              /*  .placeholder(R.drawable.p1)
                .error(R.drawable.p1)*/
                .into(holder.productImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewCallback.onItemClick(position);
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
        private CardView cardView ;
        private ImageView productImage  ;
        private TextView productPrice ;
        private TextView productDescription ;
        private Context context ;


        public RecyclerViewAdapterHolder(View itemView) {
            super(itemView);
            context =  itemView.getContext() ;
            cardView =(CardView) itemView.findViewById(R.id.card_view);
            productImage= (ImageView) itemView.findViewById(R.id.product_image);
            productPrice= (TextView) itemView.findViewById(R.id.product_price);
            productDescription= (TextView) itemView.findViewById(R.id.product_descr);

        }

    }

    public interface RecyclerViewCallback {
        void onItemClick(int  position);
    }

}
