package findlocation.bateam.com.login;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.api.FastNetworking;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.JsonObjectCallBackListener;
import findlocation.bateam.com.model.Response;
import findlocation.bateam.com.model.UserRegister;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.PatternUtil;

public class FragmentSignUpFbGmailInfo extends BaseFragment {

    @BindView(R.id.edt_user_name)
    EditText mEdtUserName;
    @BindView(R.id.edt_email)
    EditText mEdtEmail;
    @BindView(R.id.edt_telephone)
    EditText mEdtTelephone;

    private String mEmail;
    private String mAvatarUrl;
    private String mName;
    private String mId;
    private UserRegister mUserRegister = new UserRegister();
    private Gson mGson = new Gson();

    @BindString(R.string.error_dialog_email_null)
    String mStrUserNameNull;
    @BindString(R.string.error_dialog_email_error)
    String mStrUserNameError;
    @BindString(R.string.error_dialog_family_name_null)
    String mStrFamilyNameNull;
    @BindString(R.string.error_dialog_telephone_null)
    String mStrTelephoneNull;
    @BindString(R.string.error_dialog_telephone_error)
    String mStrTelephoneError;


    @OnClick(R.id.btn_send)
    public void onClickBtnSend() {

        if (TextUtils.isEmpty(mEdtUserName.getText().toString().trim())) {
            DialogUtil.showDialogError(getActivity(), mStrFamilyNameNull, null);
            return;
        }

        if (TextUtils.isEmpty(mEdtEmail.getText().toString().trim())) {
            DialogUtil.showDialogError(getActivity(), mStrUserNameNull, null);
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEdtEmail.getText().toString().trim()).matches()) {
            DialogUtil.showDialogError(getActivity(), mStrUserNameError, null);
            return;
        }

        if (TextUtils.isEmpty(mEdtTelephone.getText().toString().trim())) {
            DialogUtil.showDialogError(getActivity(), mStrTelephoneNull, null);
            return;
        }

        if (mEdtTelephone.getText().toString().trim().length() < 10) {
            DialogUtil.showDialogError(getActivity(), mStrTelephoneError, null);
            return;
        }

        mUserRegister.phoneNumber = mEdtTelephone.getText().toString().trim();
        mUserRegister.email = mEdtEmail.getText().toString().trim();
        mUserRegister.facebookAvatar = mAvatarUrl;
        mUserRegister.name = mEdtUserName.getText().toString().trim();
        mUserRegister.facebookId = mId;

        callApiRegister();

    }

    private void callApiRegister() {

        FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonObjectCallBackListener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Response response = mGson.fromJson(jsonObject.toString(), Response.class);
                if (response.message.contains("Đăng ký tài khoản thành công")) {
                    DialogUtil.showDialogSuccess(getActivity(), response.message, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FragmentSignUpApprove fragmentSignUpApprove = new FragmentSignUpApprove();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.BUNDLE_EMAIL, mEdtEmail.getText().toString().trim());
                            fragmentSignUpApprove.setArguments(bundle);
                            replaceFragment(fragmentSignUpApprove, Constants.FRAGMENT_SIGN_UP_APPROVE);
                        }
                    });
                } else {
                    DialogUtil.showDialogError(getActivity(), response.message, null);
                }
            }

            @Override
            public void onError(String messageError) {

            }
        });

        fastNetworking.callApiRegisterFbGmail(mUserRegister);
    }


    @Override
    protected void onBackPressFragment() {
        ((LoginActivity)getActivity()).setToolBarSignIn();
        finishFragment();
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
        ((LoginActivity)getActivity()).setToolBarSignUp();
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        Bundle b = getArguments();
        if (b != null) {
            mEmail = b.getString(Constants.BUNDLE_EMAIL, "");
            mAvatarUrl = b.getString(Constants.BUNDLE_AVATAR, "");
            mName = b.getString(Constants.BUNDLE_NAME, "");
            mId = b.getString(Constants.BUNDLE_ID, "");

            if (!TextUtils.isEmpty(mEmail)){
                mEdtEmail.setFocusable(false);
                mEdtEmail.setEnabled(false);
                mEdtEmail.setCursorVisible(false);
                mEdtEmail.setKeyListener(null);
                mEdtEmail.setBackgroundColor(Color.TRANSPARENT);
            }
            mEdtEmail.setText(mEmail);
            mEdtUserName.setText(mName);
        }


    }

    @Override
    protected void getData() {

    }
}
