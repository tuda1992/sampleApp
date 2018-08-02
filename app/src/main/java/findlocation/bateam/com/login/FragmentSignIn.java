package findlocation.bateam.com.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.MainActivity;
import findlocation.bateam.com.R;
import findlocation.bateam.com.api.FastNetworking;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.DialogOnClick;
import findlocation.bateam.com.listener.JsonObjectCallBackListener;
import findlocation.bateam.com.listener.StringCallBackListener;
import findlocation.bateam.com.model.ExternalLogin;
import findlocation.bateam.com.model.Response;
import findlocation.bateam.com.model.UserInfo;
import findlocation.bateam.com.model.UserLogin;
import findlocation.bateam.com.model.UserRegister;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.NetworkUtil;
import findlocation.bateam.com.util.PatternUtil;
import findlocation.bateam.com.util.PrefUtil;

/**
 * Created by acv on 12/4/17.
 */

public class FragmentSignIn extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener {

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
    @BindView(R.id.tv_sign_up)
    TextView mTvSignUp;

    @BindString(R.string.error_dialog_email_null)
    String mStrUserNameNull;
    @BindString(R.string.error_dialog_email_error)
    String mStrUserNameError;
    @BindString(R.string.error_dialog_password_null)
    String mStrPasswordNull;
    @BindString(R.string.error_dialog_password_error)
    String mStrPasswordError;

    private Gson mGson;
    private int mIntOut;
    private CallbackManager mCallBackManager;
    private UserRegister mUserRegister = new UserRegister();
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;


    @OnClick(R.id.tv_forgot_pass)
    public void onClickForgotPassword() {
        ((LoginActivity) getActivity()).goFragmentForgetPassword();
    }

    @OnClick(R.id.tv_login_fb)
    public void onClickLoginFB() {
        loginFacebook();
    }

    @OnClick(R.id.tv_login_gmail)
    public void onClickLoginGmail() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

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

//        if (!PatternUtil.checkPasswordCharacter(userPass)) {
//            DialogUtil.showDialogError(getActivity(), mStrPasswordError, null);
//            return;
//        }

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

        final UserLogin userLogin = new UserLogin();
        userLogin.userName = un;
        userLogin.password = pw;

        final String json = mGson.toJson(userLogin);
        JSONObject jsonObject = new JSONObject(json);

        FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonObjectCallBackListener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                UserInfo userLoginSuccess = mGson.fromJson(jsonObject.toString(), UserInfo.class);
                if (TextUtils.isEmpty(userLoginSuccess.securityToken)) {
                    if (userLoginSuccess.responseCode.contains("ACCOUNT_NOT_ACTIVE")) {
                        DialogUtil.showDialogErrorNotActive(getActivity(), userLoginSuccess.message, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FragmentSignUpApprove fragmentSignUpApprove = new FragmentSignUpApprove();
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.BUNDLE_EMAIL, userLogin.userName);
                                fragmentSignUpApprove.setArguments(bundle);
                                replaceFragment(fragmentSignUpApprove, Constants.FRAGMENT_SIGN_UP_APPROVE);
                            }
                        });

                    } else {
                        DialogUtil.showDialogError(getActivity(), userLoginSuccess.message, null);
                    }
                    return;
                }

                PrefUtil.setSharedPreferenceUserInfo(getActivity(), jsonObject.toString());
                PrefUtil.setSharedPreferenceUserLogin(getActivity(), json);
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

    @OnClick(R.id.tv_sign_up)
    public void onClickSignUpNew() {
        ((LoginActivity) getActivity()).goFragmentSignUp();
    }

    @OnClick(R.id.tv_login_email)
    public void onClickGoToLoginEmail() {
        DialogUtil.showDialogLogin(getActivity(),new DialogOnClick() {
            @Override
            public void onClickLogin(String un, String pw) {
                int selectedId = mRg.getCheckedRadioButtonId();
                Bundle bundle = new Bundle();
                if (selectedId == R.id.rd_user) {
                    bundle.putBoolean(Constants.BUNDLE_IS_MASTER, false);
                } else {
                    bundle.putBoolean(Constants.BUNDLE_IS_MASTER, true);
                }

                PrefUtil.setSharedPreferenceSaveData(getActivity());

                try {
                    callApiLogin(un, pw, bundle);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClickForgotPass() {
                ((LoginActivity) getActivity()).goFragmentForgetPassword();
            }
        });
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
        mTvSignUp.setPaintFlags(mTvForgetPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        // Call back Facebook
        mCallBackManager = CallbackManager.Factory.create();


        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        mGson = new Gson();

        if (PrefUtil.getSharedPreferenceUserLogin(getActivity()) != null) {
            UserLogin userLogin = PrefUtil.getSharedPreferenceUserLogin(getActivity());
            mEdtUserName.setText(userLogin.userName);
            mEdtUserPass.setText(userLogin.password);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    private void loginFacebook() {
        // FB logout before login
//        LoginManager.getInstance().logOut();

        // FB login
        LoginManager.getInstance().registerCallback(mCallBackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Handle callback
                        updateProfile(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d(TAG, "onError " + exception.toString());
                    }
                });
        LoginManager.getInstance().logInWithReadPermissions(
                this,
                Arrays.asList("public_profile", "email")
        );
    }

    private void updateProfile(LoginResult loginResult) {
        AccessToken accesstoken = loginResult.getAccessToken();
        // Facbook Email address
        GraphRequest request = GraphRequest.newMeRequest(accesstoken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            final JSONObject jsonObject = new JSONObject();
                            String id = "";
                            String name = "";
                            String email = "";
                            String profilePicUrl = "";
//                            if (accesstoken != null) {
//                                id = accesstoken.getUserId();
//                                Log.d(TAG, "id = " + id);
//                            }

                            if (object.has("id")) {
                                id = object.getString("id");
                                Log.d(TAG, "id = " + id);
                            }

                            if (object.has("name")) {
                                name = object.getString("name");
                                Log.d(TAG, "name = " + name);
                            }
                            if (object.has("email")) {
                                email = object.getString("email");
                                Log.d(TAG, "email = " + email);
                            }

                            if (object.has("picture")) {
                                profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                Log.d(TAG, "profilePicUrl = " + profilePicUrl);
                            }

                            mUserRegister.email = email;
                            mUserRegister.facebookAvatar = profilePicUrl;
                            mUserRegister.name = name;
                            mUserRegister.facebookId = id;


                            try {
                                callApiExternalLogin();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            callApiRegister(email, profilePicUrl, name, id);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void callApiRegister(final String email, final String avatarUrl, final String name, final String id) {

        FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonObjectCallBackListener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Response response = mGson.fromJson(jsonObject.toString(), Response.class);

                switch (response.code) {
                    case "PHONE_NUMBER_EMPTY":
                    case "EMAIL_EMPTY":
                        FragmentSignUpFbGmailInfo fragmentSignUpFbGmailInfo = new FragmentSignUpFbGmailInfo();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.BUNDLE_EMAIL, email);
                        bundle.putString(Constants.BUNDLE_AVATAR, avatarUrl);
                        bundle.putString(Constants.BUNDLE_ID, id);
                        bundle.putString(Constants.BUNDLE_NAME, name);
                        fragmentSignUpFbGmailInfo.setArguments(bundle);
                        replaceFragment(fragmentSignUpFbGmailInfo, Constants.FRAGMENT_SIGN_UP_FB_GMAIL_INFO);
                        break;
                    case "ACCOUNT_EXISTED":

                        break;
                    default:
                        DialogUtil.showDialogError(getActivity(), response.message, null);
                        break;
                }
            }

            @Override
            public void onError(String messageError) {

            }
        });

        fastNetworking.callApiRegisterFbGmail(mUserRegister);
    }

    private void callApiExternalLogin() throws JSONException {

        if (TextUtils.isEmpty(mUserRegister.email)) {
            FragmentSignUpFbGmailInfo fragmentSignUpFbGmailInfo = new FragmentSignUpFbGmailInfo();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_EMAIL, "");
            bundle.putString(Constants.BUNDLE_AVATAR, mUserRegister.facebookAvatar);
            bundle.putString(Constants.BUNDLE_ID, mUserRegister.facebookId);
            bundle.putString(Constants.BUNDLE_NAME, mUserRegister.name);
            fragmentSignUpFbGmailInfo.setArguments(bundle);
            replaceFragment(fragmentSignUpFbGmailInfo, Constants.FRAGMENT_SIGN_UP_FB_GMAIL_INFO);
            return;
        }


        ExternalLogin externalLogin = new ExternalLogin();
        externalLogin.email = mUserRegister.email;

        final String json = mGson.toJson(externalLogin);
        JSONObject jsonObject = new JSONObject(json);

        FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonObjectCallBackListener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                UserInfo userLoginSuccess = mGson.fromJson(jsonObject.toString(), UserInfo.class);
                switch (userLoginSuccess.responseCode) {
                    case "ACCOUNT_NOT_ACTIVE":
                        DialogUtil.showDialogErrorNotActive(getActivity(), userLoginSuccess.message, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FragmentSignUpApprove fragmentSignUpApprove = new FragmentSignUpApprove();
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.BUNDLE_EMAIL, mUserRegister.email);
                                bundle.putBoolean(Constants.BUNDLE_NOT_ACTIVE, true);
                                fragmentSignUpApprove.setArguments(bundle);
                                replaceFragment(fragmentSignUpApprove, Constants.FRAGMENT_SIGN_UP_APPROVE);
                            }
                        });
                        break;
                    case "ACCOUNT_NOT_EXIST":
                        callApiRegister(mUserRegister.email, mUserRegister.facebookAvatar, mUserRegister.name, mUserRegister.id);
                        break;
                    case "SUCCESS":

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


                        PrefUtil.setSharedPreferenceSaveData(getActivity());


                        PrefUtil.setSharedPreferenceUserInfo(getActivity(), jsonObject.toString());
                        startActivityAnim(MainActivity.class, bundle);
                        finishActivityAnim();
                        break;
                    default:
                        DialogUtil.showDialogError(getActivity(), userLoginSuccess.message, null);
                        break;
                }
            }

            @Override
            public void onError(String messageError) {

            }
        });

        fastNetworking.callApiLoginFbGmail(jsonObject);
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onBackPressFragment() {
        mIntOut++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIntOut = 0;
            }
        }, 500);
        if (mIntOut == 2) {
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), "Ấn back 2 lần để thoát ứng dụng", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();


            String personName = acct.getDisplayName();
            String personPhotoUrl = "";
            if (acct.getPhotoUrl() != null)
                personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.d(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

            mUserRegister.email = email;
            mUserRegister.facebookAvatar = personPhotoUrl;
            mUserRegister.name = personName;
            mUserRegister.facebookId = acct.getId();


            try {
                callApiExternalLogin();
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            callApiRegister(email, personPhotoUrl, personName, acct.getId());

        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
