package findlocation.bateam.com.findjob;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import findlocation.bateam.com.MainActivity;
import findlocation.bateam.com.R;
import findlocation.bateam.com.adapter.FindJobAdapter;
import findlocation.bateam.com.api.FastNetworking;
import findlocation.bateam.com.base.BaseActivity;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.findlocation.InfoPlaceActivity;
import findlocation.bateam.com.listener.JsonArrayCallBackListener;
import findlocation.bateam.com.model.JobModel;
import findlocation.bateam.com.model.PlaceModel;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.JSONResourceReader;

/**
 * Created by doanhtu on 1/12/18.
 */

public class FindJobActivity extends BaseActivity implements FindJobAdapter.ICallBackItemClick {

    private FindJobAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<JobModel> mListData = new ArrayList<>();
    private JobModel mItem;
    private Gson mGson = new Gson();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;

    @BindString(R.string.title_find_job)
    String mStrTitle;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_find_job;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(mStrTitle);

        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvData.setLayoutManager(mLinearLayoutManager);
        mRvData.setHasFixedSize(true);
        mAdapter = new FindJobAdapter(this, mListData, this);
        mRvData.setAdapter(mAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mItem = bundle.getParcelable(Constants.BUNDLE_JOB_ITEM);
        }

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initDatas(Bundle saveInstanceState) {

    }

    @Override
    protected void getData() {

        if (mItem == null) {
            return;
        }

        try {
            callApiFindJob(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void callApiFindJob(final boolean isShowLoading) throws JSONException {

        String json = mGson.toJson(mItem);
        JSONObject jsonObject = new JSONObject(json);

        FastNetworking fastNetworking = new FastNetworking(this, new JsonArrayCallBackListener() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                if (jsonArray.toString().equalsIgnoreCase("[]") || TextUtils.isEmpty(jsonArray.toString())) {
                    DialogUtil.showDialogErrorJob(FindJobActivity.this, "Hiện tại chưa có công việc bạn cần tìm!!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishActivityAnim();
                        }
                    });
                    return;
                }

                Type listType = new TypeToken<List<JobModel>>() {
                }.getType();
                List<JobModel> jobModels = (List<JobModel>) mGson.fromJson(jsonArray.toString(), listType);
                if (!isShowLoading)
                    mListData.clear();

                mListData.addAll(jobModels);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String messageError) {

            }
        });
        fastNetworking.callApiJobInfo(jsonObject, MainActivity.mUserInfo.securityToken, isShowLoading);
    }


    @Override
    public void onItemClick(int position, JobModel item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_PLACE_ITEM, item);
        startActivityAnim(JobDetailActivity.class, bundle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivityAnim();
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
