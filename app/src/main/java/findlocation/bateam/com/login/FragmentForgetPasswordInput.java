package findlocation.bateam.com.login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.api.FastNetworking;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.StringCallBackListener;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.NetworkUtil;
import findlocation.bateam.com.util.PatternUtil;

/**
 * Created by acv on 12/8/17.
 */

public class FragmentForgetPasswordInput extends BaseFragment {

    @BindView(R.id.edt_new_pass)
    EditText mEdtNewPass;
    @BindView(R.id.edt_new_pass_reinput)
    EditText mEdtNewPassReInput;
    @BindView(R.id.edt_old_pass)
    EditText mEdtOldPass;
    @BindView(R.id.btn_send_email)
    FloatingActionButton mBtnSendEmail;
    @BindString(R.string.error_dialog_password_null)
    String mStrPasswordNull;
    @BindString(R.string.error_dialog_password_error)
    String mStrPasswordError;
    @BindString(R.string.error_dialog_re_password_null)
    String mStrRePasswordNull;
    @BindString(R.string.error_dialog_re_password_error)
    String mStrRePasswordError;
    @BindString(R.string.error_dialog_password_repassword_not_equal)
    String mStrPasswordNotEqual;
    @BindString(R.string.error_dialog_old_password_null)
    String mStrOldPasswordNull;


    String mEmail;


    @OnClick(R.id.btn_send_email)
    public void onClickSendEmail() {

        if (!NetworkUtil.isHaveInternet(getActivity())) {
            DialogUtil.showDialogErrorInternet(getActivity(), null);
            return;
        }

        String oldPass = mEdtOldPass.getText().toString().trim();
        String newPass = mEdtNewPass.getText().toString().trim();
        String newPassReInput = mEdtNewPassReInput.getText().toString().trim();

        // Validate

        if (TextUtils.isEmpty(oldPass)) {
            DialogUtil.showDialogError(getActivity(), mStrOldPasswordNull, null);
            return;
        }

        if (TextUtils.isEmpty(newPass)) {
            DialogUtil.showDialogError(getActivity(), mStrPasswordNull, null);
            return;
        }

        if (!PatternUtil.checkPasswordCharacter(newPass)) {
            DialogUtil.showDialogError(getActivity(), mStrPasswordError, null);
            return;
        }

        if (TextUtils.isEmpty(newPassReInput)) {
            DialogUtil.showDialogError(getActivity(), mStrRePasswordNull, null);
            return;
        }

        if (!PatternUtil.checkPasswordCharacter(newPassReInput)) {
            DialogUtil.showDialogError(getActivity(), mStrRePasswordError, null);
            return;
        }

        if (!newPass.equalsIgnoreCase(newPassReInput)) {
            DialogUtil.showDialogError(getActivity(), mStrPasswordNotEqual, null);
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Email", mEmail);
            jsonObject.put("NewPassword", newPass);
            jsonObject.put("OldPassword", oldPass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callApiUpdatePw(jsonObject);

    }

    private void callApiUpdatePw(JSONObject jsonObject) {
        FastNetworking fastNetworking = new FastNetworking(getActivity(), new StringCallBackListener() {
            @Override
            public void onResponse(String string) {
                if (string.contains("Thay đổi Mật khẩu thành công")) {
                    DialogUtil.showDialogUpdateSuccess(getActivity(), string, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FragmentForgetPasswordComplete fragmentForgetPasswordComplete = new FragmentForgetPasswordComplete();
                            addFragment(fragmentForgetPasswordComplete, Constants.FRAGMENT_FORGET_PASSWORD_COMPLETE);
                        }
                    });
                    return;
                }
                DialogUtil.showDialogUpdateFail(getActivity(),string,null);
            }

            @Override
            public void onError(String messageError) {

            }
        });
        fastNetworking.callApiUpdatePw(jsonObject);
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
        Bundle b = getArguments();
        if (b != null) {
            mEmail = b.getString(Constants.BUNDLE_EMAIL, null);
        }
    }

    @Override
    protected void getData() {

    }
}
