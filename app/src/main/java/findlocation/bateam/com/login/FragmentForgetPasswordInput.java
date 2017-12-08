package findlocation.bateam.com.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;

/**
 * Created by acv on 12/8/17.
 */

public class FragmentForgetPasswordInput extends BaseFragment {

    @BindView(R.id.edt_new_pass)
    EditText mEdtNewPass;
    @BindView(R.id.edt_new_pass_reinput)
    EditText mEdtNewPassReInput;
    @BindView(R.id.btn_send_email)
    Button mBtnSendEmail;

    @OnClick(R.id.btn_send_email)
    public void onClickSendEmail() {
        String newPass = mEdtNewPass.getText().toString().trim();
        String newPassReInput = mEdtNewPassReInput.getText().toString().trim();

        FragmentForgetPasswordComplete fragmentForgetPasswordComplete = new FragmentForgetPasswordComplete();
        addFragment(fragmentForgetPasswordComplete, Constants.FRAGMENT_FORGET_PASSWORD_COMPLETE);

    }

    @Override
    protected void onBackPressFragment() {
        finishFragment();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_forget_pass_word_input;
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
