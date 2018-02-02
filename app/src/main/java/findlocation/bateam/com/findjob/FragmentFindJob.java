package findlocation.bateam.com.findjob;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.MainActivity;
import findlocation.bateam.com.R;
import findlocation.bateam.com.adapter.CustomSpinnerAdapter;
import findlocation.bateam.com.adapter.SexSpinnerAdapter;
import findlocation.bateam.com.api.FastNetworking;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.JsonArrayCallBackListener;
import findlocation.bateam.com.listener.JsonObjectCallBackListener;
import findlocation.bateam.com.model.Cities;
import findlocation.bateam.com.model.JobFilter;
import findlocation.bateam.com.model.JobModel;
import findlocation.bateam.com.model.PlaceModel;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.NetworkUtil;

/**
 * Created by acv on 12/4/17.
 */

public class FragmentFindJob extends BaseFragment {

    private Gson mGson = new Gson();

    @BindView(R.id.spn_address)
    Spinner mSpnAddress;
    @BindView(R.id.spn_time)
    Spinner mSpnTime;
    @BindView(R.id.spn_type)
    Spinner mSpnType;
    @BindView(R.id.btn_find_job)
    Button mBtnFindJob;

    @OnClick(R.id.btn_find_job)
    public void onClickFindJob() {
        if (!NetworkUtil.isHaveInternet(getActivity())) {
            DialogUtil.showDialogErrorInternet(getActivity(), null);
            return;
        }

        JobModel item = new JobModel();
        item.workingArea = mArrAddress.get(mSpnAddress.getSelectedItemPosition());
        item.jobType = mArrType.get(mSpnType.getSelectedItemPosition());
        item.industry = mArrTime.get(mSpnTime.getSelectedItemPosition());

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_JOB_ITEM, item);

        startActivityAnim(FindJobActivity.class, bundle);
    }


    private SexSpinnerAdapter mAdapterAddress;
    private SexSpinnerAdapter mAdapterTime;
    private SexSpinnerAdapter mAdapterType;
    private List<String> mArrAddress = new ArrayList<>();
    private List<String> mArrTime = new ArrayList<>();
    private List<String> mArrType = new ArrayList<>();

    @Override
    protected void onBackPressFragment() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_find_job;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initViews(View view) {
        callApiGetJobFilter();
    }

    private void callApiGetJobFilter() {
        FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonObjectCallBackListener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                JobFilter jobFilter = mGson.fromJson(jsonObject.toString(), JobFilter.class);

                // Address
                if (jobFilter.workingAreas != null && jobFilter.workingAreas.size() > 0) {
                    mArrAddress.addAll(jobFilter.workingAreas);
                    mAdapterAddress = new SexSpinnerAdapter(getActivity(), mArrAddress, true);
                    mSpnAddress.setAdapter(mAdapterAddress);
                }

                // Time
                if (jobFilter.industries != null && jobFilter.industries.size() > 0) {
                    mArrTime.addAll(jobFilter.industries);
                    mAdapterTime = new SexSpinnerAdapter(getActivity(), mArrTime, true);
                    mSpnTime.setAdapter(mAdapterTime);
                }

                // Type
                if (jobFilter.jobTypes != null && jobFilter.jobTypes.size() > 0) {
                    mArrType.addAll(jobFilter.jobTypes);
                    mAdapterType = new SexSpinnerAdapter(getActivity(), mArrType, true);
                    mSpnType.setAdapter(mAdapterType);
                }
            }

            @Override
            public void onError(String messageError) {

            }
        });
        fastNetworking.callApiGetJobFilter(MainActivity.mUserInfo.securityToken);
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {

    }

    @Override
    protected void getData() {

    }
}
