package findlocation.bateam.com.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.MainActivity;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;

/**
 * Created by acv on 12/8/17.
 */

public class FragmentSignUpComplete extends BaseFragment {

    @BindView(R.id.btn_next)
    FloatingActionButton mBtnNext;

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

    }

    @Override
    protected void getData() {

    }
}
