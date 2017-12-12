package findlocation.bateam.com.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.MainActivity;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.NetworkUtil;

/**
 * Created by acv on 12/7/17.
 */

public class FragmentSignUpApprove extends BaseFragment {

    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.btn_send_code)
    Button mBtnSendCode;
    @BindString(R.string.error_dialog_code_null)
    String mStrCodeNull;
    @BindString(R.string.error_dialog_code_error)
    String mStrCodeError;

    @OnClick(R.id.btn_send_code)
    public void onClicKSendCode() {

        if (!NetworkUtil.isHaveInternet(getActivity())) {
            DialogUtil.showDialogErrorInternet(getActivity(), null);
        }

        String code = mEdtCode.getText().toString().trim();

        // Validate
        if (TextUtils.isEmpty(code)) {
            DialogUtil.showDialogError(getActivity(), mStrCodeNull, null);
            return;
        }

        if (code.length() < 4){
            DialogUtil.showDialogError(getActivity(), mStrCodeError, null);
            return;
        }

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
