package findlocation.bateam.com.listener;


import org.json.JSONArray;

/**
 * Created by mac on 10/24/17.
 */

public interface JsonArrayCallBackListener {

    void onResponse(JSONArray jsonArray);

    void onError(String messageError);

}
