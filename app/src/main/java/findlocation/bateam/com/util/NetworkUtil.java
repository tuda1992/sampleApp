package findlocation.bateam.com.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;

/**
 * Created by acv on 12/12/17.
 */

public class NetworkUtil {

    public static boolean isHaveInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static void openNetworkSim(Context context, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void openWIFISetting(Context context, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void openNetworkSetting(Context context, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

}
