package samuel.example.com.thed;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by samuel on 5/31/2017.
 */

public class Utilities {

    public static boolean checkInternetConnection(Context context)
    {
        ConnectivityManager con_manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
