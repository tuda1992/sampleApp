package findlocation.bateam.com.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.MainActivity;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;

/**
 * Created by acv on 12/7/17.
 */

public class FragmentSignUpApprove extends BaseFragment {

    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.btn_send_code)
    Button mBtnSendCode;

    @OnClick(R.id.btn_send_code)
    public void onClicKSendCode() {
        String code = mEdtCode.getText().toString().trim();
//        if (TextUtils.isEmpty(code)) {
//            return;
//        }
        FragmentSignUpComplete fragmentSignUpComplete = new FragmentSignUpComplete();
        addFragment(fragmentSignUpComplete, Constants.FRAGMENT_SIGN_UP_COMPLETE);
    }

    @Override
    protected void onBackPressFragment() {
        finishFragment();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_sign_up_approve;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {

    }

    @Override
    protected void getData() {

    }
}
