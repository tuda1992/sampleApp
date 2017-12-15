package findlocation.bateam.com.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.NetworkUtil;
import findlocation.bateam.com.util.PatternUtil;

/**
 * Created by acv on 12/7/17.
 */

public class FragmentSignUpInfo extends BaseFragment {

    @BindView(R.id.edt_family_name)
    EditText mEdtFamilyName;
    @BindView(R.id.edt_middle_name)
    EditText mEdtMiddleName;
    @BindView(R.id.edt_first_name)
    EditText mEdtFirstName;
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
    @BindView(R.id.edt_re_password)
    EditText mEdtRePassword;
    @BindView(R.id.edt_telephone)
    EditText mEdtTelephone;
    @BindView(R.id.edt_address)
    EditText mEdtAddress;
    @BindView(R.id.tv_dob)
    TextView mTvDob;
    @BindView(R.id.spn_sex)
    Spinner mSpnSex;
    @BindView(R.id.spn_address_town)
    Spinner mSpnAddressTown;
    @BindView(R.id.spn_address_district)
    Spinner mSpnAddressDistrict;
    @BindView(R.id.spn_address_city)
    Spinner mSpnAddressCity;
    @BindView(R.id.spn_address_country)
    Spinner mSpnAddressCountry;
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
    @BindString(R.string.error_dialog_email_null)
    String mStrUserNameNull;
    @BindString(R.string.error_dialog_email_error)
    String mStrUserNameError;
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
    @BindString(R.string.error_dialog_family_name_null)
    String mStrFamilyNameNull;
    @BindString(R.string.error_dialog_middle_name_null)
    String mStrMiddleNameNull;
    @BindString(R.string.error_dialog_first_name_null)
    String mStrFirstNameNull;
    @BindString(R.string.error_dialog_dob_null)
    String mStrDobNull;
    @BindString(R.string.error_dialog_telephone_null)
    String mStrTelephoneNull;
    @BindString(R.string.error_dialog_telephone_error)
    String mStrTelephoneError;


    private DatePickerDialog.OnDateSetListener mListenerPickter;
    private Calendar mCalendar;
    private CallbackManager mCallBackManager;
    private CustomSpinnerAdapter mAdapter;
    private CustomSpinnerAdapter mAdapterAddressCity;
    private CustomSpinnerAdapter mAdapterAddressTown;
    private CustomSpinnerAdapter mAdapterAddressDistrict;
    private CustomSpinnerAdapter mAdapterAddressCountry;
    private List<String> mArrSex = new ArrayList<>();
    private List<String> mArrAddressCity = new ArrayList<>();
    private List<String> mArrAddressTown = new ArrayList<>();
    private List<String> mArrAddressDistrict = new ArrayList<>();
    private List<String> mArrAddressCountry = new ArrayList<>();

    @OnClick(R.id.btn_send)
    public void onClickSend() {

        if (!NetworkUtil.isHaveInternet(getActivity())) {
            DialogUtil.showDialogErrorInternet(getActivity(), null);
            return;
        }

        boolean isSaveFb = mCbFb.isChecked();
        String sex = mArrSex.get(mSpnSex.getSelectedItemPosition());
        String town = mArrAddressTown.get(mSpnAddressTown.getSelectedItemPosition());
        String district = mArrAddressDistrict.get(mSpnAddressDistrict.getSelectedItemPosition());
        String city = mArrAddressCity.get(mSpnAddressCity.getSelectedItemPosition());
        String address = mEdtAddress.getText().toString();
        String familyName = mEdtFamilyName.getText().toString();
        String middleName = mEdtFamilyName.getText().toString();
        String firstName = mEdtFirstName.getText().toString();
        String schoolName = mEdtSchool.getText().toString();
        String className = mEdtClass.getText().toString();
        String grade = mEdtGrade.getText().toString();
        String email = mEdtEmail.getText().toString();
        String password = mEdtPassword.getText().toString();
        String rePassword = mEdtRePassword.getText().toString();
        String telephone = mEdtTelephone.getText().toString();
        String dob = mTvDob.getText().toString();

        // Validate

        if (TextUtils.isEmpty(familyName)) {
            DialogUtil.showDialogError(getActivity(), mStrFamilyNameNull, null);
            return;
        }

        if (TextUtils.isEmpty(middleName)) {
            DialogUtil.showDialogError(getActivity(), mStrMiddleNameNull, null);
            return;
        }

        if (TextUtils.isEmpty(firstName)) {
            DialogUtil.showDialogError(getActivity(), mStrFirstNameNull, null);
            return;
        }

        if (TextUtils.isEmpty(dob)) {
            DialogUtil.showDialogError(getActivity(), mStrDobNull, null);
            return;
        }

        if (TextUtils.isEmpty(email)) {
            DialogUtil.showDialogError(getActivity(), mStrUserNameNull, null);
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            DialogUtil.showDialogError(getActivity(), mStrUserNameError, null);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            DialogUtil.showDialogError(getActivity(), mStrPasswordNull, null);
            return;
        }

        if (!PatternUtil.checkPasswordCharacter(password)) {
            DialogUtil.showDialogError(getActivity(), mStrPasswordError, null);
            return;
        }

        if (TextUtils.isEmpty(rePassword)) {
            DialogUtil.showDialogError(getActivity(), mStrRePasswordNull, null);
            return;
        }

        if (!PatternUtil.checkPasswordCharacter(rePassword)) {
            DialogUtil.showDialogError(getActivity(), mStrRePasswordError, null);
            return;
        }

        if (!password.equalsIgnoreCase(rePassword)) {
            DialogUtil.showDialogError(getActivity(), mStrPasswordNotEqual, null);
            return;
        }

        if (TextUtils.isEmpty(telephone)) {
            DialogUtil.showDialogError(getActivity(), mStrTelephoneNull, null);
            return;
        }

        if (telephone.length() < 10) {
            DialogUtil.showDialogError(getActivity(), mStrTelephoneError, null);
            return;
        }


        // After Validate

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
        // Sex
        mArrSex.add(mStrMen);
        mArrSex.add(mStrWomen);
        mAdapter = new CustomSpinnerAdapter(getActivity(), mArrSex,false);
        mSpnSex.setAdapter(mAdapter);

        // City
        mArrAddressCity.add("Hà Nội");
        mArrAddressCity.add("Đà Nẵng");
        mArrAddressCity.add("Nha Trang");
        mArrAddressCity.add("Hải Phòng");
        mArrAddressCity.add("Thái Bình");
        mArrAddressCity.add("Nam Định");
        mAdapterAddressCity = new CustomSpinnerAdapter(getActivity(), mArrAddressCity,false);
        mSpnAddressCity.setAdapter(mAdapterAddressCity);

        // Town
        mArrAddressTown.add("Văn Chương");
        mArrAddressTown.add("Hàng Bột");
        mArrAddressTown.add("Ô Chợ Dừa");
        mArrAddressTown.add("Thổ Quan");
        mArrAddressTown.add("Quan Thổ");
        mAdapterAddressTown = new CustomSpinnerAdapter(getActivity(), mArrAddressTown,false);
        mSpnAddressTown.setAdapter(mAdapterAddressTown);

        // District
        mArrAddressDistrict.add("Đống Đa");
        mArrAddressDistrict.add("Ba Đình");
        mArrAddressDistrict.add("Hoàn Kiếm");
        mArrAddressDistrict.add("Long Biên");
        mArrAddressDistrict.add("Thanh Xuân");
        mArrAddressDistrict.add("Cầu giấy");
        mAdapterAddressDistrict = new CustomSpinnerAdapter(getActivity(), mArrAddressDistrict,false);
        mSpnAddressDistrict.setAdapter(mAdapterAddressDistrict);


        // Country
        mArrAddressCountry.add("Việt Nam");
        mArrAddressCountry.add("Trung Quốc");
        mArrAddressCountry.add("Nhật Bản");
        mArrAddressCountry.add("Mỹ");
        mArrAddressCountry.add("Nga");
        mArrAddressCountry.add("Hàn Quốc");
        mAdapterAddressCountry = new CustomSpinnerAdapter(getActivity(), mArrAddressCountry,false);
        mSpnAddressCountry.setAdapter(mAdapterAddressCountry);

    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        // Call back Facebook
        mCallBackManager = CallbackManager.Factory.create();

        mSpnSex.setSelection(0);
        mSpnAddressCity.setSelection(0);
        mSpnAddressTown.setSelection(0);
        mSpnAddressDistrict.setSelection(0);

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

                            Toast.makeText(getActivity(), "Email : " + email + " id : " + id + " name : " + name + " profilePicture : " + profilePicUrl, Toast.LENGTH_LONG).show();

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
