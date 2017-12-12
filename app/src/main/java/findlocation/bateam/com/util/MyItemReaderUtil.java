package findlocation.bateam.com.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import findlocation.bateam.com.model.MyClusterItem;

/**
 * Created by acv on 12/12/17.
 */

public class MyItemReaderUtil {

    /*
    * This matches only once in whole input,
    * so Scanner.next returns whole InputStream as a String.
    * http://stackoverflow.com/a/5445161/2183804
    */
    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public List<MyClusterItem> read(InputStream inputStream) throws JSONException {
        List<MyClusterItem> items = new ArrayList<MyClusterItem>();
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            String title = null;
            String snippet = null;
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            if (!object.isNull("title")) {
                title = object.getString("title");
            }
            if (!object.isNull("snippet")) {
                snippet = object.getString("snippet");
            }
            items.add(new MyClusterItem(lat, lng, title, snippet));
        }
        return items;
    }

}
