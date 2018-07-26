package findlocation.bateam.com.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;

public class FragmentSignUpFbGmailInfo extends BaseFragment {

    @BindView(R.id.edt_user_name)
    EditText mEdtUserName;
    @BindView(R.id.tv_email)
    TextView mTvEmail;

    private String email;

    @OnClick(R.id.btn_send)
    public void onClickBtnSend(){
        FragmentSignUpApprove fragmentSignUpApprove = new FragmentSignUpApprove();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_EMAIL, email);
        fragmentSignUpApprove.setArguments(bundle);
        replaceFragment(fragmentSignUpApprove, Constants.FRAGMENT_SIGN_UP_APPROVE);
    }

    @Override
    protected void onBackPressFragment() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_sign_up_fb_gmail_info;
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
