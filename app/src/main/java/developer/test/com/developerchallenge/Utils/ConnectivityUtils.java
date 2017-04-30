package developer.test.com.developerchallenge.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

/**
 * Created by Kamil on 4/30/2017.
 */

public class ConnectivityUtils {
    private static boolean hasConnectivity(Context context, boolean roamingOK) {
        boolean hasConnectivity = true;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        hasConnectivity = info != null && (info.isConnected() || (roamingOK && info.isRoaming()));
        return hasConnectivity;
    }

    public static void checkConnectivityAndDisplayDialog(Context context) {
        if (!hasConnectivity(context, true)) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("No Network Connection");
            alertDialog.setMessage("Internet access is necessary for the use of this app.");
            alertDialog.show();
        }
    }
}
