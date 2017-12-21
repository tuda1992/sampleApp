package findlocation.bateam.com.findlocation;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseActivity;
import findlocation.bateam.com.constant.Constants;

/**
 * Created by acv on 12/21/17.
 */

public class SearchPlaceActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.edt_input)
    EditText mEdtInput;

    private HashMap<String,String> mQuerryParams = new HashMap<>();
    private final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

    String urlStart = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670,151.1957&radius=500&name=";
    String urlEnd = "&key=AIzaSyBR_hAqjfMaG8xIs-xTl-bbw6Xz0-zQ7J0";

    @OnClick(R.id.iv_clear)
    public void onClickClear() {
        mEdtInput.setText("");
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_search_place;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void initListeners() {
        mEdtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "charSequence : " + charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void callApiSearch(HashMap<String, String> queryParams) {
        AndroidNetworking.get(BASE_URL)
                .addQueryParameter(queryParams)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse : " + response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError : " + anError.getErrorBody().toString());
                    }
                });
    }

    @Override
    protected void initDatas(Bundle saveInstanceState) {
        mQuerryParams.put(Constants.LOCATION,"-33.8670,151.1957");
        mQuerryParams.put(Constants.RADIUS,"5000");
        mQuerryParams.put(Constants.LOCATION,"-33.8670,151.1957");
        mQuerryParams.put(Constants.LOCATION,"-33.8670,151.1957");

    }

    @Override
    protected void getData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finishActivityAnim(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
