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

public class FragmentForgetPassword extends BaseFragment {

    @BindView(R.id.edt_email)
    EditText mEdtEmail;
    @BindView(R.id.btn_send_email)
    Button mBtnSendMail;

    @OnClick(R.id.btn_send_email)
    public void onClickSendMail() {
        String email = mEdtEmail.getText().toString().trim();

        FragmentForgetPasswordCode fragmentForgetPasswordCode = new FragmentForgetPasswordCode();
        addFragment(fragmentForgetPasswordCode, Constants.FRAGMENT_FORGET_PASSWORD_CODE);
    }

    @Override
    protected void onBackPressFragment() {
        ((LoginActivity) getActivity()).goFragmentSignIn();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_forget_pass_word;
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
