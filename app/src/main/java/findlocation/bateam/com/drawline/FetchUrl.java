package findlocation.bateam.com.drawline;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by doanhtu on 1/9/18.
 */

public class FetchUrl {

    public static String getUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }

}
