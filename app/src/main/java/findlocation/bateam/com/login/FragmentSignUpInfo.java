package findlocation.bateam.com.login;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.adapter.AutoCompleteUniversityAdapter;
import findlocation.bateam.com.adapter.CustomSpinnerAdapter;
import findlocation.bateam.com.adapter.NationSpinnerAdapter;
import findlocation.bateam.com.adapter.SexSpinnerAdapter;
import findlocation.bateam.com.api.FastNetworking;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.JsonArrayCallBackListener;
import findlocation.bateam.com.listener.StringCallBackListener;
import findlocation.bateam.com.model.Cities;
import findlocation.bateam.com.model.NationModel;
import findlocation.bateam.com.model.UniversityModel;
import findlocation.bateam.com.model.UserRegister;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.JSONResourceReader;
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
    AutoCompleteTextView mEdtSchool;
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
    FloatingActionButton mBtnSend;
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
    @BindString(R.string.error_dialog_first_name_null)
    String mStrFirstNameNull;
    @BindString(R.string.error_dialog_dob_null)
    String mStrDobNull;
    @BindString(R.string.error_dialog_telephone_null)
    String mStrTelephoneNull;
    @BindString(R.string.error_dialog_telephone_error)
    String mStrTelephoneError;
    @BindString(R.string.error_dialog_school_error)
    String mStrSchoolError;
    @BindString(R.string.error_dialog_city_error)
    String mStrCityError;
    @BindString(R.string.error_dialog_district_error)
    String mStrDistrictError;
    @BindString(R.string.error_dialog_town_error)
    String mStrTownError;

    @BindString(R.string.text_signup_address_city)
    String mStrCity;
    @BindString(R.string.text_signup_address_district)
    String mStrDistrict;
    @BindString(R.string.text_signup_address_town)
    String mStrTown;

    private String mStrUniversityName;
    private Gson mGson;
    private DatePickerDialog.OnDateSetListener mListenerPickter;
    private Calendar mCalendar;
    private Calendar mDefaultCalendar;
    private CallbackManager mCallBackManager;
    private SexSpinnerAdapter mAdapter;
    private CustomSpinnerAdapter mAdapterAddressCity;
    private CustomSpinnerAdapter mAdapterAddressTown;
    private CustomSpinnerAdapter mAdapterAddressDistrict;
    private NationSpinnerAdapter mAdapterAddressCountry;
    private AutoCompleteUniversityAdapter mAdapterSchool;
    private List<String> mArrSex = new ArrayList<>();
    private List<Cities> mArrAddressCity = new ArrayList<>();
    private List<Cities> mArrAddressTown = new ArrayList<>();
    private List<Cities> mArrAddressDistrict = new ArrayList<>();
    private List<NationModel> mArrAddressCountry = new ArrayList<>();
    private ArrayList<UniversityModel> mArrSchool = new ArrayList<>();
    private Map<String, File> mMapFile = new HashMap<>();
    private UserRegister mUserRegister = new UserRegister();
    private Cities mCities;

    @OnClick(R.id.btn_send)
    public void onClickSend() {

        if (!NetworkUtil.isHaveInternet(getActivity())) {
            DialogUtil.showDialogErrorInternet(getActivity(), null);
            return;
        }

        boolean isSaveFb = mCbFb.isChecked();
        String sex = mArrSex.get(mSpnSex.getSelectedItemPosition());

        String town = "";
        if (mArrAddressTown.size() > 0) {
            town = mArrAddressTown.get(mSpnAddressTown.getSelectedItemPosition()).name;
        }
        String district = "";
        if (mArrAddressDistrict.size() > 0) {
            district = mArrAddressDistrict.get(mSpnAddressDistrict.getSelectedItemPosition()).name;
        }
        String city = "";
        if (mArrAddressCity.size() > 0) {
            city = mArrAddressCity.get(mSpnAddressCity.getSelectedItemPosition()).name;
        }

        String address = mEdtAddress.getText().toString();
        String familyName = mEdtFamilyName.getText().toString();
        String middleName = mEdtMiddleName.getText().toString();
        String firstName = mEdtFirstName.getText().toString();
        String schoolName = mEdtSchool.getText().toString();
        String className = mEdtClass.getText().toString();
        String grade = mEdtGrade.getText().toString();
        String email = mEdtEmail.getText().toString();
        String password = mEdtPassword.getText().toString();
        String rePassword = mEdtRePassword.getText().toString();
        String telephone = mEdtTelephone.getText().toString();
        String dob = mTvDob.getText().toString();
        String country = mArrAddressCountry.get(mSpnAddressCountry.getSelectedItemPosition()).nation;

        // Validate

        if (TextUtils.isEmpty(familyName)) {
            DialogUtil.showDialogError(getActivity(), mStrFamilyNameNull, null);
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

        if (TextUtils.isEmpty(city) || city.equalsIgnoreCase(mStrCity)) {
            DialogUtil.showDialogError(getActivity(), mStrCityError, null);
            return;
        }

        if (TextUtils.isEmpty(district) || district.equalsIgnoreCase(mStrDistrict)) {
            DialogUtil.showDialogError(getActivity(), mStrDistrictError, null);
            return;
        }

        if (TextUtils.isEmpty(town) || town.equalsIgnoreCase(mStrTown)) {
            DialogUtil.showDialogError(getActivity(), mStrTownError, null);
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

        if (!schoolName.equalsIgnoreCase(mStrUniversityName)) {
            DialogUtil.showDialogError(getActivity(), mStrSchoolError, null);
            return;
        }


        mUserRegister.email = email;
        mUserRegister.familyName = familyName;
        mUserRegister.name = firstName;
        mUserRegister.middleName = middleName;
        mUserRegister.password = password;
        mUserRegister.phoneNumber = telephone;
        mUserRegister.cityId = mArrAddressCity.get(mSpnAddressCity.getSelectedItemPosition()).id;
        mUserRegister.districtId = mArrAddressDistrict.get(mSpnAddressDistrict.getSelectedItemPosition()).id;
        mUserRegister.wardId = mArrAddressTown.get(mSpnAddressTown.getSelectedItemPosition()).id;
        mUserRegister.nationality = country;
        mUserRegister.schoolName = schoolName;
        mUserRegister.schoolYear = grade;
        mUserRegister.specializedSubject = className;
        if (sex.equalsIgnoreCase("Nam")) {
            mUserRegister.sex = "0";
        } else {
            mUserRegister.sex = "1";
        }
        mUserRegister.dob = dob;
        mUserRegister.address = address;


        // After Validate

        if (isSaveFb) {
            loginFacebook();
            return;
        }

        callApiRegister();
    }

    private void callApiRegister() {
        mMapFile.put(Constants.AVATAR, LoginActivity.mFileAvatar);
        mMapFile.put(Constants.STUDENT_CARD, LoginActivity.mFileLicense);

        FastNetworking fastNetworking = new FastNetworking(getActivity(), new StringCallBackListener() {
            @Override
            public void onResponse(String string) {
                Log.d(TAG, "onResponse : " + string);
                if (string.contains("Đăng ký tài khoản thành công")) {
                    DialogUtil.showDialogSuccess(getActivity(), string, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            LoginActivity.mFileAvatar = null;
                            LoginActivity.mFileLicense = null;
                            FragmentSignUpApprove fragmentSignUpApprove = new FragmentSignUpApprove();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.BUNDLE_EMAIL, mEdtEmail.getText().toString());
                            fragmentSignUpApprove.setArguments(bundle);
                            replaceFragment(fragmentSignUpApprove, Constants.FRAGMENT_SIGN_UP_APPROVE);
                        }
                    });
                } else {
                    DialogUtil.showDialogError(getActivity(), string, null);
                }
            }

            @Override
            public void onError(String messageError) {
                Log.d(TAG, "onError : " + messageError);
            }
        });
        fastNetworking.callApiRegister(mMapFile, mUserRegister);
    }

    @OnClick(R.id.tv_dob)
    public void onClickDob() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), mListenerPickter, mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, mDefaultCalendar.get(Calendar.YEAR) - 16);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, mStrOk, datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, mStrNo, datePickerDialog);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
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
        mGson = new Gson();
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        // Call back Facebook
        mCallBackManager = CallbackManager.Factory.create();

        mSpnSex.setSelection(0);
        mSpnAddressCity.setSelection(0);
        mSpnAddressTown.setSelection(0);
        mSpnAddressDistrict.setSelection(0);

        mDefaultCalendar = Calendar.getInstance();
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

        mSpnAddressCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d(TAG, "mSpnAddressCity onItemSelected");
                if (mArrAddressCity.size() > 0) {
                    if (!mArrAddressCity.get(position).id.equalsIgnoreCase("-1"))
                        getDistricts(mArrAddressCity.get(position).id);
                    else {
                        mCities = new Cities();
                        mCities.id = "-1";
                        mCities.cityId = "-1";
                        mCities.name = mStrDistrict;
                        mArrAddressDistrict.clear();
                        mArrAddressDistrict.add(0, mCities);
                        mAdapterAddressDistrict = new CustomSpinnerAdapter(getActivity(), mArrAddressDistrict, true);
                        mSpnAddressDistrict.setAdapter(mAdapterAddressDistrict);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d(TAG, "mSpnAddressCity onNothingSelected");
            }

        });


        mSpnAddressDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "mSpnAddressDistrict onItemSelected");
                if (mArrAddressDistrict.size() > 0) {
                    if (!mArrAddressDistrict.get(i).id.equalsIgnoreCase("-1"))
                        getWards(mArrAddressDistrict.get(i).id);
                    else {
                        mCities = new Cities();
                        mCities.id = "-1";
                        mCities.cityId = "-1";
                        mCities.name = mStrTown;
                        mArrAddressTown.clear();
                        mArrAddressTown.add(0, mCities);
                        mAdapterAddressTown = new CustomSpinnerAdapter(getActivity(), mArrAddressTown, true);
                        mSpnAddressTown.setAdapter(mAdapterAddressTown);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d(TAG, "mSpnAddressDistrict onNothingSelected");
            }
        });

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

                            mUserRegister.facebookEmail = email;
                            mUserRegister.facebookAvatar = profilePicUrl;
                            mUserRegister.facebookUserName = name;
                            mUserRegister.facebookId = id;

                            Toast.makeText(getActivity(), "Email : " + email + " id : " + id + " name : " + name + " profilePicture : " + profilePicUrl, Toast.LENGTH_LONG).show();

                            callApiRegister();

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
        // Sex
        mArrSex.add(mStrMen);
        mArrSex.add(mStrWomen);
        mAdapter = new SexSpinnerAdapter(getActivity(), mArrSex, true);
        mSpnSex.setAdapter(mAdapter);

        getCities();

        // School
        String jsonReaderUniversities;
        try {
            jsonReaderUniversities = JSONResourceReader.readFileJSONUniversityFromRaw(getActivity());
            Gson gson = new Gson();
            Type listType = new TypeToken<List<UniversityModel>>() {
            }.getType();
            List<UniversityModel> universityList = (List<UniversityModel>) gson.fromJson(jsonReaderUniversities, listType);
            mArrSchool.clear();
            mArrSchool.addAll(universityList);
            mAdapterSchool = new AutoCompleteUniversityAdapter(getActivity(), mArrSchool);
            mEdtSchool.setAdapter(mAdapterSchool);
            mEdtSchool.setThreshold(1);

            mEdtSchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String universityName = mAdapterSchool.mFilterUniver.get(i).universityName;
                    mEdtSchool.setText(universityName);
                    mStrUniversityName = universityName;
                    mEdtSchool.setSelection(universityName.length());
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Country
        String jsonReader;
        try {
            jsonReader = JSONResourceReader.readFileJSONNationsFromRaw(getActivity());
            Gson gson = new Gson();
            Type listType = new TypeToken<List<NationModel>>() {
            }.getType();
            List<NationModel> nationList = (List<NationModel>) gson.fromJson(jsonReader, listType);
            mArrAddressCountry.clear();
            for (NationModel item : nationList) {
                if (item.id.equalsIgnoreCase("191")) {
                    mArrAddressCountry.add(0, item);
                } else {
                    mArrAddressCountry.add(item);
                }
            }

            mAdapterAddressCountry = new NationSpinnerAdapter(getActivity(), mArrAddressCountry, true);
            mSpnAddressCountry.setAdapter(mAdapterAddressCountry);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getCities() {
        FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonArrayCallBackListener() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Type listType = new TypeToken<List<Cities>>() {
                }.getType();
                List<Cities> list = (List<Cities>) mGson.fromJson(jsonArray.toString(), listType);
                mArrAddressCity.addAll(list);
                mCities = new Cities();
                mCities.id = "-1";
                mCities.cityId = "-1";
                mCities.name = mStrCity;
                mArrAddressCity.add(0, mCities);
                mAdapterAddressCity = new CustomSpinnerAdapter(getActivity(), mArrAddressCity, true);
                mSpnAddressCity.setAdapter(mAdapterAddressCity);
            }

            @Override
            public void onError(String messageError) {

            }
        });

        fastNetworking.callApiGetCities();
    }

    private void getDistricts(String cityId) {
        FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonArrayCallBackListener() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Type listType = new TypeToken<List<Cities>>() {
                }.getType();
                List<Cities> list = (List<Cities>) mGson.fromJson(jsonArray.toString(), listType);
                mArrAddressDistrict.clear();
                mArrAddressDistrict.addAll(list);

                mCities = new Cities();
                mCities.id = "-1";
                mCities.cityId = "-1";
                mCities.name = mStrDistrict;

                mArrAddressDistrict.add(0, mCities);
                mAdapterAddressDistrict = new CustomSpinnerAdapter(getActivity(), mArrAddressDistrict, true);
                mSpnAddressDistrict.setAdapter(mAdapterAddressDistrict);
            }

            @Override
            public void onError(String messageError) {

            }
        });

        fastNetworking.callApiGetDistricts(cityId);
    }

    private void getWards(String districtId) {
        FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonArrayCallBackListener() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Type listType = new TypeToken<List<Cities>>() {
                }.getType();
                List<Cities> list = (List<Cities>) mGson.fromJson(jsonArray.toString(), listType);
                mArrAddressTown.clear();
                mArrAddressTown.addAll(list);

                mCities = new Cities();
                mCities.id = "-1";
                mCities.cityId = "-1";
                mCities.name = mStrTown;

                mArrAddressTown.add(0, mCities);
                mAdapterAddressTown = new CustomSpinnerAdapter(getActivity(), mArrAddressTown, true);
                mSpnAddressTown.setAdapter(mAdapterAddressTown);
            }

            @Override
            public void onError(String messageError) {

            }
        });

        fastNetworking.callApiGetWards(districtId);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

}
