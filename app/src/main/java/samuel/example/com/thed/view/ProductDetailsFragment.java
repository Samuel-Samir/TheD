package samuel.example.com.thed.view;



import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import samuel.example.com.thed.R;
import samuel.example.com.thed.model.container.Product;


public class ProductDetailsFragment extends Fragment {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private Product product;
    private TextView productDescription;
    private ImageView productPhoto;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_product_details, container, false);
       ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        productDescription = (TextView)rootView.findViewById(R.id.product_description);
        productPhoto = (ImageView) rootView.findViewById(R.id.profile_image) ;

        Bundle arguments = getArguments();
        if (arguments != null)
        {
            product = (Product) arguments.getParcelable(ProductListFragment.BUNDLE_ARGUMENT);
            if (product!=null)
            {
                collapsingToolbarLayout.setTitle(product.getName());
                productDescription.setText(product.getProductDescription());
                String imageUrl = product.getImage().getLink();
                imageUrl = imageUrl.replace("http", "https");
                Picasso.with(getActivity())
                        .load(imageUrl)
                        .into(productPhoto);

            }
        }

        return  rootView ;
    }






}
