package findlocation.bateam.com.drawline;

import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import findlocation.bateam.com.R;

/**
 * Created by doanhtu on 1/9/18.
 */

public class RouteDrawerTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

    private PolylineOptions lineOptions;
    private GoogleMap mMap;
    private int routeColor;
    private CameraUpdate cu;
    private int width,height;

    public RouteDrawerTask(GoogleMap mMap,int w,int h) {
        this.mMap = mMap;
        this.width = w;
        this.height = h;
    }

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try {
            jObject = new JSONObject(jsonData[0]);
            Log.d("RouteDrawerTask", jsonData[0]);
            DataRouteParser parser = new DataRouteParser();
            Log.d("RouteDrawerTask", parser.toString());

            // Starts parsing data
            routes = parser.parse(jObject);
            Log.d("RouteDrawerTask", "Executing routes");
            Log.d("RouteDrawerTask", routes.toString());

        } catch (Exception e) {
            Log.d("RouteDrawerTask", e.toString());
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        if (result != null)
            drawPolyLine(result);
    }

    private void drawPolyLine(List<List<HashMap<String, String>>> result) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        ArrayList<LatLng> points = null;
        lineOptions = null;
        Log.d("drawPolyLine", "result = " + result);

        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
                builder.include(position);
            }

            LatLngBounds bounds = builder.build();

            int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

            cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(15);
            routeColor = ContextCompat.getColor(DrawRouteMaps.getContext(), R.color.colorRouteLine);
            if (routeColor == 0)
                lineOptions.color(0xFF0A8F08);
            else
                lineOptions.color(routeColor);
        }

        // Drawing polyline in the Google Map for the i-th route
        if (lineOptions != null && mMap != null) {
            mMap.addPolyline(lineOptions);
            if (points != null) {
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(points.get((int) points.size() / 2)).zoom(12.5f).build();
//                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000, null);
                mMap.animateCamera(cu);
            }

        } else {
            Log.d("onPostExecute", "without Polylines draw");
        }
    }

}
