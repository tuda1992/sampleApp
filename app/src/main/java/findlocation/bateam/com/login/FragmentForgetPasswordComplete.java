package findlocation.bateam.com.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.MainActivity;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;

/**
 * Created by acv on 12/8/17.
 */

public class FragmentForgetPasswordComplete extends BaseFragment {

    @BindView(R.id.tv_notify)
    TextView mTvNotify;
    @BindView(R.id.btn_next)
    FloatingActionButton mBtnNext;
    @BindString(R.string.text_forget_password_complete_notify)
    String mStrNotify;

    @OnClick(R.id.btn_next)
    public void onClickNext() {
        FragmentSignIn fragmentSignIn = new FragmentSignIn();
        replaceFragment(fragmentSignIn, Constants.FRAGMENT_SIGN_IN);
    }

    @Override
    protected void onBackPressFragment() {
        getActivity().finish();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_sign_up_complete;
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
