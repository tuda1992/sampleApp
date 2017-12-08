package findlocation.bateam.com.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;

/**
 * Created by acv on 12/8/17.
 */

public class FragmentForgetPasswordCode extends BaseFragment {

    @BindView(R.id.tv_notify)
    TextView mTvNotify;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.btn_send_code)
    Button mBtnSendCode;
    @BindString(R.string.text_forget_password_code_notify)
    String mStrNotify;

    @OnClick(R.id.btn_send_code)
    public void onClickSendCode() {
        String code = mEdtCode.getText().toString().trim();

        FragmentForgetPasswordInput fragmentForgetPasswordInput = new FragmentForgetPasswordInput();
        addFragment(fragmentForgetPasswordInput, Constants.FRAGMENT_FORGET_PASSWORD_INPUT);
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
        mTvNotify.setText(mStrNotify);
    }

    @Override
    protected void getData() {

    }
}
