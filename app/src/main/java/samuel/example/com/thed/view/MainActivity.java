package samuel.example.com.thed.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import samuel.example.com.thed.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container ,new ProductListFragment())
                    .commit();
        }
    }
}
