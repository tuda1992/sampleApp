package findlocation.bateam.com.login;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import findlocation.bateam.com.listener.StringCallBackListener;
import findlocation.bateam.com.model.EmailResend;
import findlocation.bateam.com.model.Response;
import findlocation.bateam.com.model.UserInfo;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.NetworkUtil;
import findlocation.bateam.com.util.PrefUtil;

/**
 * Created by acv on 12/7/17.
 */

public class FragmentSignUpApprove extends BaseFragment {

    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.btn_send_code)
    FloatingActionButton mBtnSendCode;
    @BindView(R.id.tv_count_down)
    TextView mTvCountDown;


    @BindString(R.string.error_dialog_code_null)
    String mStrCodeNull;
    @BindString(R.string.error_dialog_code_error)
    String mStrCodeError;
    @BindString(R.string.string_count_down)
    String mStrCountDown;
    @BindString(R.string.string_resend)
    String mStrResend;

    @OnClick(R.id.tv_count_down)
    public void onClickResend() {
        Log.d(TAG, "Resend");
        mTvCountDown.setText(String.format(mStrCountDown, mIntCountDown));
        mTvCountDown.setPaintFlags(0);
        mCountDownTimer.start();

        try {
            callApiResend();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private int mIntCountDown = 30;
    private String mEmail;
    private boolean mIsActive;
    private Gson mGson = new Gson();
    private CountDownTimer mCountDownTimer = new CountDownTimer(31000, 1000) {
        @Override
        public void onTick(long l) {
            Log.d(TAG, "FUCK ALL");
            mTvCountDown.setEnabled(false);
            mIntCountDown--;
            if (mIntCountDown < 10) {
                mTvCountDown.setText(String.format(mStrCountDown, "0" + mIntCountDown));
                if (mIntCountDown == 0) {
                    mTvCountDown.setPaintFlags(mTvCountDown.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    mTvCountDown.setText(Html.fromHtml(mStrResend));
                    mTvCountDown.setEnabled(true);
                }
            } else {
                mTvCountDown.setText(String.format(mStrCountDown, "" + mIntCountDown));
            }
        }

        @Override
        public void onFinish() {
            mIntCountDown = 30;
        }
    };

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
                } else {
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
            mIsActive = b.getBoolean(Constants.BUNDLE_NOT_ACTIVE, false);
        }

        if (mIsActive) {
            try {
                callApiResend();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void callApiResend() throws JSONException {
        EmailResend emailResend = new EmailResend();
        emailResend.email = mEmail;

        final String json = mGson.toJson(emailResend);
        JSONObject jsonObject = new JSONObject(json);

        FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonObjectCallBackListener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Response response = mGson.fromJson(jsonObject.toString(), Response.class);
                Log.d(TAG, response + "");

            }

            @Override
            public void onError(String messageError) {

            }
        });

        fastNetworking.callApiResend(jsonObject);
    }

    @Override
    protected void getData() {

        mTvCountDown.setText(String.format(mStrCountDown, mIntCountDown));
        mTvCountDown.setPaintFlags(0);

        mCountDownTimer.start();

        mEdtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "beforeTextChanged");


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged");
                if (mEdtCode.getText().toString().trim().length() == 0) {
                    mCountDownTimer.start();
                    mTvCountDown.setVisibility(View.VISIBLE);
                } else {
                    mIntCountDown = 30;
                    mCountDownTimer.cancel();
                    mTvCountDown.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "afterTextChanged");

            }
        });


    }

    public void updateActiveCode(String code) {
        mEdtCode.setText(code);
    }
}
