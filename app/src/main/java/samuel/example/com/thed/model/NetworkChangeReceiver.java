package samuel.example.com.thed.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * Created by samuel on 6/2/2017.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

     static NetworkAvailable networkAvailable ;


    @Override
    public void onReceive(Context context, Intent intent) {


        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE );
        if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {
             networkAvailable.networkIsAvailable();

        }

    }

    public static void setNetworkAvailable(NetworkAvailable networkAvailabl)
    {
        networkAvailable =networkAvailabl;
    }

    public interface NetworkAvailable {
        void networkIsAvailable();
    }
}
