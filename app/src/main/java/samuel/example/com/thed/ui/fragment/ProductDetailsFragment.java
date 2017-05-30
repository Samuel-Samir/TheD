package samuel.example.com.thed.ui.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import samuel.example.com.thed.R;
import samuel.example.com.thed.model.Product;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private Product product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_details, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ActionBar actionBar =((AppCompatActivity)getActivity()). getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout)  rootView.findViewById(R.id.collapsing_toolbar);
        Bundle arguments = getArguments();
        if (arguments != null)
        {
            product = (Product) arguments.getParcelable("sasa");
            if (product!=null)
            {
                collapsingToolbarLayout.setTitle(product.getName());
                TextView textView = (TextView)rootView.findViewById(R.id.product_description);
                textView.setText(product.getProductDescription());
                ImageView imageView = (ImageView) rootView.findViewById(R.id.profile_image) ;
                String imageUrl = product.getImage().getLink();
                imageUrl = imageUrl.replace("http", "https");
                Picasso.with(getActivity())
                        .load(imageUrl)
                        .into(imageView);

            }
        }

        return  rootView ;
    }





}
