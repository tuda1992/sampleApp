package findlocation.bateam.com.findjob;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.adapter.CustomSpinnerAdapter;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.NetworkUtil;

/**
 * Created by acv on 12/4/17.
 */

public class FragmentFindJob extends BaseFragment {

    @BindView(R.id.spn_address)
    Spinner mSpnAddress;
    @BindView(R.id.spn_time)
    Spinner mSpnTime;
    @BindView(R.id.spn_type)
    Spinner mSpnType;
    @BindView(R.id.btn_find_job)
    Button mBtnFindJob;

    @OnClick(R.id.btn_find_job)
    public void onClickFindJob(){

        if (!NetworkUtil.isHaveInternet(getActivity())) {
            DialogUtil.showDialogErrorInternet(getActivity(), null);
            return;
        }

    }

    private CustomSpinnerAdapter mAdapterAddress;
    private CustomSpinnerAdapter mAdapterTime;
    private CustomSpinnerAdapter mAdapterType;
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

        // Address
        mArrAddress.add("Chọn nơi muốn làm việc");
        mArrAddress.add("Hà Nội");
        mArrAddress.add("Đà Nẵng");
        mArrAddress.add("Nha Trang");
        mArrAddress.add("Hải Phòng");
        mArrAddress.add("Thái Bình");
        mArrAddress.add("Nam Định");
        mAdapterAddress = new CustomSpinnerAdapter(getActivity(), mArrAddress,true);
        mSpnAddress.setAdapter(mAdapterAddress);

        // Time
        mArrTime.add("Chọn ca làm việc");
        mArrTime.add("Hành chính");
        mArrTime.add("Ca sáng");
        mArrTime.add("Ca chiều");
        mArrTime.add("Ca đêm");
        mArrTime.add("Toàn thời gian cố định");
        mAdapterTime = new CustomSpinnerAdapter(getActivity(), mArrTime,true);
        mSpnTime.setAdapter(mAdapterTime);

        // Type
        mArrType.add("Chọn loại hình công việc");
        mArrType.add("IT - Phần mềm");
        mArrType.add("IT - Phần cứng");
        mArrType.add("Ngân hàng");
        mArrType.add("Điện");
        mArrType.add("Sale");
        mArrType.add("Marketing");
        mAdapterType = new CustomSpinnerAdapter(getActivity(), mArrType,true);
        mSpnType.setAdapter(mAdapterType);


    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {

    }

    @Override
    protected void getData() {

    }
}
