package findlocation.bateam.com.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.adapter.CustomSpinnerAdapter;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;

/**
 * Created by acv on 12/7/17.
 */

public class FragmentSignUpInfo extends BaseFragment {

    @BindView(R.id.edt_fullname)
    EditText mEdtFullName;
    @BindView(R.id.edt_school)
    EditText mEdtSchool;
    @BindView(R.id.edt_class)
    EditText mEdtClass;
    @BindView(R.id.edt_grade)
    EditText mEdtGrade;
    @BindView(R.id.edt_email)
    EditText mEdtEmail;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;
    @BindView(R.id.edt_telephone)
    EditText mEdtTelephone;
    @BindView(R.id.tv_dob)
    TextView mTvDob;
    @BindView(R.id.spn_sex)
    Spinner mSpnSex;
    @BindView(R.id.spn_address)
    Spinner mSpnAddress;
    @BindView(R.id.btn_send)
    Button mBtnSend;
    @BindView(R.id.cb_save_fb)
    CheckBox mCbFb;
    @BindString(R.string.text_button_date_picker_ok)
    String mStrOk;
    @BindString(R.string.text_button_date_picker_no)
    String mStrNo;
    @BindString(R.string.text_sex_men)
    String mStrMen;
    @BindString(R.string.text_sex_women)
    String mStrWomen;

    private DatePickerDialog.OnDateSetListener mListenerPickter;
    private Calendar mCalendar;
    private CallbackManager mCallBackManager;
    private CustomSpinnerAdapter mAdapter;
    private CustomSpinnerAdapter mAdapterAddress;
    private List<String> mArrSex = new ArrayList<>();
    private List<String> mArrAddress = new ArrayList<>();

    @OnClick(R.id.btn_send)
    public void onClickSend() {
        boolean isSaveFb = mCbFb.isChecked();
        if (isSaveFb) {
            loginFacebook();
            return;
        }

        FragmentSignUpApprove fragmentSignUpApprove = new FragmentSignUpApprove();
        addFragment(fragmentSignUpApprove, Constants.FRAGMENT_SIGN_UP_APPROVE);
    }

    @OnClick(R.id.tv_dob)
    public void onClickDob() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), mListenerPickter, mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, mStrOk, datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, mStrNo, datePickerDialog);
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }


    @Override
    protected void onBackPressFragment() {
        finishFragment();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_sign_up_info;
    }

    @Override
    protected void initListeners() {
    }

    @Override
    protected void initViews(View view) {
        mArrSex.add(mStrMen);
        mArrSex.add(mStrWomen);
        mAdapter = new CustomSpinnerAdapter(getActivity(), mArrSex);
        mSpnSex.setAdapter(mAdapter);

        mArrAddress.add("Hà Nội");
        mArrAddress.add("Đà Nẵng");
        mArrAddress.add("Nha Trang");
        mArrAddress.add("Hải Phòng");
        mArrAddress.add("Thái Bình");
        mArrAddress.add("Nam Định");
        mAdapterAddress = new CustomSpinnerAdapter(getActivity(), mArrAddress);
        mSpnAddress.setAdapter(mAdapterAddress);
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        // Call back Facebook
        mCallBackManager = CallbackManager.Factory.create();

        mSpnSex.setSelection(0);
        mSpnAddress.setSelection(0);

        mCalendar = Calendar.getInstance();
        mListenerPickter = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                mTvDob.setText(sdf.format(mCalendar.getTime()));
            }
        };
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
                        updateProfile();
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

    private void updateProfile() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();

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

                            FragmentSignUpApprove fragmentSignUpApprove = new FragmentSignUpApprove();
                            addFragment(fragmentSignUpApprove, Constants.FRAGMENT_SIGN_UP_APPROVE);

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

    @Override
    protected void getData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }
}
