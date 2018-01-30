package findlocation.bateam.com.listener;


import org.json.JSONObject;

/**
 * Created by mac on 10/24/17.
 */

public interface JsonObjectCallBackListener {

    void onResponse(JSONObject jsonObject);

    void onError(String messageError);

}
