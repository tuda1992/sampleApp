package findlocation.bateam.com.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

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
import findlocation.bateam.com.listener.JsonObjectCallBackListener;
import findlocation.bateam.com.model.UserLogin;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.NetworkUtil;
import findlocation.bateam.com.util.PatternUtil;
import findlocation.bateam.com.util.PrefUtil;

/**
 * Created by acv on 12/4/17.
 */

public class FragmentSignIn extends BaseFragment {

    @BindView(R.id.tv_forgot_pass)
    TextView mTvForgetPass;
    @BindView(R.id.gr_radio)
    RadioGroup mRg;
    @BindView(R.id.rd_user)
    RadioButton mRdUser;
    @BindView(R.id.rd_master)
    RadioButton mRdMaster;
    @BindView(R.id.edt_user_name)
    EditText mEdtUserName;
    @BindView(R.id.edt_user_password)
    EditText mEdtUserPass;
    @BindView(R.id.btn_signin)
    Button mBtnSignIn;
    @BindView(R.id.btn_signup)
    Button mBtnSignUp;
    @BindView(R.id.cb_save_password)
    CheckBox mCbSavePassword;

    @BindString(R.string.error_dialog_email_null)
    String mStrUserNameNull;
    @BindString(R.string.error_dialog_email_error)
    String mStrUserNameError;
    @BindString(R.string.error_dialog_password_null)
    String mStrPasswordNull;
    @BindString(R.string.error_dialog_password_error)
    String mStrPasswordError;

    private Gson mGson;

    @OnClick(R.id.btn_signin)
    public void onClickSignIn() {

        if (!NetworkUtil.isHaveInternet(getActivity())) {
            DialogUtil.showDialogErrorInternet(getActivity(), null);
            return;
        }

        String userName = mEdtUserName.getText().toString();
        String userPass = mEdtUserPass.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            DialogUtil.showDialogError(getActivity(), mStrUserNameNull, null);
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
            DialogUtil.showDialogError(getActivity(), mStrUserNameError, null);
            return;
        }

        if (TextUtils.isEmpty(userPass)) {
            DialogUtil.showDialogError(getActivity(), mStrPasswordNull, null);
            return;
        }

        if (!PatternUtil.checkPasswordCharacter(userPass)) {
            DialogUtil.showDialogError(getActivity(), mStrPasswordError, null);
            return;
        }

        int selectedId = mRg.getCheckedRadioButtonId();
        boolean isChecked = mCbSavePassword.isChecked();
        Bundle bundle = new Bundle();
        if (selectedId == R.id.rd_user) {
            Log.d(TAG, "Login By User isChecked = " + isChecked);
            bundle.putBoolean(Constants.BUNDLE_IS_MASTER, false);
        } else {
            Log.d(TAG, "Login By Master isChecked = " + isChecked);
            bundle.putBoolean(Constants.BUNDLE_IS_MASTER, true);
        }

        if (isChecked) {
            PrefUtil.setSharedPreferenceSaveData(getActivity());
        }

        try {
            callApiLogin(userName, userPass, bundle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void callApiLogin(String un, String pw, final Bundle bundle) throws JSONException {

        UserLogin userLogin = new UserLogin();
        userLogin.userName = un;
        userLogin.password = pw;

        String json = mGson.toJson(userLogin);
        JSONObject jsonObject = new JSONObject(json);

        FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonObjectCallBackListener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                PrefUtil.setSharedPreferenceUserInfo(getActivity(), jsonObject.toString());

                startActivityAnim(MainActivity.class, bundle);
                finishActivityAnim();
            }

            @Override
            public void onError(String messageError) {

            }
        });

        fastNetworking.callApiLogin(jsonObject);

    }

    @OnClick(R.id.btn_signup)
    public void onClickSignUp() {
        ((LoginActivity) getActivity()).goFragmentSignUp();
    }

    @OnClick(R.id.tv_forgot_pass)
    public void onClickForgotPassword() {
        ((LoginActivity) getActivity()).goFragmentForgetPassword();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initViews(View view) {
        mTvForgetPass.setPaintFlags(mTvForgetPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        mGson = new Gson();
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onBackPressFragment() {
        getActivity().finish();
    }

}
