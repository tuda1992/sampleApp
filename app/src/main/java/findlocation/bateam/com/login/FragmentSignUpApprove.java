package findlocation.bateam.com.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.MainActivity;
import findlocation.bateam.com.R;
import findlocation.bateam.com.api.FastNetworking;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.StringCallBackListener;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.NetworkUtil;

/**
 * Created by acv on 12/7/17.
 */

public class FragmentSignUpApprove extends BaseFragment {

    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.btn_send_code)
    FloatingActionButton mBtnSendCode;
    @BindString(R.string.error_dialog_code_null)
    String mStrCodeNull;
    @BindString(R.string.error_dialog_code_error)
    String mStrCodeError;

    String mEmail;

    @OnClick(R.id.btn_send_code)
    public void onClicKSendCode() {

        if (!NetworkUtil.isHaveInternet(getActivity())) {
            DialogUtil.showDialogErrorInternet(getActivity(), null);
            return;
        }

        String code = mEdtCode.getText().toString().trim();

        // Validate
        if (TextUtils.isEmpty(code)) {
            DialogUtil.showDialogError(getActivity(), mStrCodeNull, null);
            return;
        }

        if (code.length() < 6) {
            DialogUtil.showDialogError(getActivity(), mStrCodeError, null);
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Email", mEmail);
            jsonObject.put("ActivationCode", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callApiActiveUser(jsonObject);

    }

    private void callApiActiveUser(JSONObject jsonObject) {
        FastNetworking fastNetworking = new FastNetworking(getActivity(), new StringCallBackListener() {
            @Override
            public void onResponse(String string) {
                Log.d(TAG, "string = " + string);
                if (string.contains("Kích hoạt tài khoản thành công")) {
                    FragmentSignUpComplete fragmentSignUpComplete = new FragmentSignUpComplete();
                    addFragment(fragmentSignUpComplete, Constants.FRAGMENT_SIGN_UP_COMPLETE);
                }else {
                    DialogUtil.showDialogErrorActive(getActivity(), "Nhập sai mã kích hoạt!!!", null);
                }
            }

            @Override
            public void onError(String messageError) {
                Log.d(TAG, "messageError = " + messageError);
            }
        });
        fastNetworking.callApiActive(jsonObject);
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
        Bundle b = getArguments();
        if (b != null) {
            mEmail = b.getString(Constants.BUNDLE_EMAIL, "");
        }
    }

    @Override
    protected void getData() {

    }
}
