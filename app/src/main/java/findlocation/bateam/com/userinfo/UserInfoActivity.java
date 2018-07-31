package findlocation.bateam.com.userinfo;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
import findlocation.bateam.com.base.BaseActivity;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.IPermissionCallBack;
import findlocation.bateam.com.listener.JsonArrayCallBackListener;
import findlocation.bateam.com.listener.StringCallBackListener;
import findlocation.bateam.com.login.FragmentSignUpApprove;
import findlocation.bateam.com.login.LoginActivity;
import findlocation.bateam.com.model.Cities;
import findlocation.bateam.com.model.NationModel;
import findlocation.bateam.com.model.UniversityModel;
import findlocation.bateam.com.model.UserInfo;
import findlocation.bateam.com.model.UserRegister;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.ImagePicker;
import findlocation.bateam.com.util.JSONResourceReader;
import findlocation.bateam.com.util.NetworkUtil;
import findlocation.bateam.com.util.PatternUtil;
import findlocation.bateam.com.util.PermissionUtils;
import findlocation.bateam.com.util.PrefUtil;
import findlocation.bateam.com.widget.ImageLoading;

/**
 * Created by doanhtu on 12/25/17.
 */

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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
    TextView mEdtEmail;
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
    @BindView(R.id.iv_avatar_user)
    ImageLoading mILAvatarUser;

    @BindString(R.string.title_user_info)
    String mStrTitle;
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

    private String mStrUniversityName;
    private File mFileAvatar;
    private Map<String, File> mMapFile = new HashMap<>();

    @OnClick(R.id.tv_dob)
    public void onClickDob() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, mListenerPickter, mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, mDefaultCalendar.get(Calendar.YEAR) - 16);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, mStrOk, datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, mStrNo, datePickerDialog);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.iv_avatar_user)
    public void onClickTakePicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.checkPermission(this, new String[]{Manifest.permission.CAMERA}, Constants.REQUEST_CAMERA, new IPermissionCallBack() {
                @Override
                public void onPermissionReady() {
                    Intent chooseImageIntent = ImagePicker.getPickImageIntent(UserInfoActivity.this);
                    startActivityForResult(chooseImageIntent, Constants.PICK_IMAGE_ID);
                }
            });
        } else {
            Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
            startActivityForResult(chooseImageIntent, Constants.PICK_IMAGE_ID);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bm = ImagePicker.getImageFromResult(this, resultCode, data);
        if (bm != null) {
            mFileAvatar = ImagePicker.convertToFile(this, bm);
            mILAvatarUser.loadBitmap(bm);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.REQUEST_CAMERA:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (grantResults.length > 0) {
                        //Check dialog not show again
                        boolean isNotAgain = false;
                        boolean isPermissionGranted = false;
                        for (int i = 0; i < permissions.length; i++) {
                            if (!shouldShowRequestPermissionRationale(
                                    permissions[i]) && (grantResults[i] != PackageManager.PERMISSION_GRANTED)) {
                                isNotAgain = true;
                                break;
                            } else if (checkSelfPermission(permissions[i]) == PackageManager.PERMISSION_GRANTED) {
                                isPermissionGranted = true;
                            } else if (checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                                isPermissionGranted = false;
                                break;
                            }
                        }
                        if (isNotAgain) {
                            PermissionUtils.showDialogTurnOnPermission(getString(R.string.text_permission_camera), this);
                        }

                        if (isPermissionGranted) {
                            Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
                            startActivityForResult(chooseImageIntent, Constants.PICK_IMAGE_ID);
                        }
                    }
                } else {
                    Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
                    startActivityForResult(chooseImageIntent, Constants.PICK_IMAGE_ID);
                }
                break;
        }
    }

    @OnClick(R.id.btn_change_info)
    public void onClickChangeInfo() {

        if (!NetworkUtil.isHaveInternet(this)) {
            DialogUtil.showDialogErrorInternet(this, null);
            return;
        }

//        String sex = mArrSex.get(mSpnSex.getSelectedItemPosition());
//        String town = mArrAddressTown.get(mSpnAddressTown.getSelectedItemPosition()).name;
//        String district = mArrAddressDistrict.get(mSpnAddressDistrict.getSelectedItemPosition()).name;
//        String city = mArrAddressCity.get(mSpnAddressCity.getSelectedItemPosition()).name;
//        String address = mEdtAddress.getText().toString();
        String familyName = mEdtFamilyName.getText().toString();
//        String middleName = mEdtMiddleName.getText().toString();
//        String firstName = mEdtFirstName.getText().toString();
        String schoolName = mEdtSchool.getText().toString();
//        String className = mEdtClass.getText().toString();
//        String grade = mEdtGrade.getText().toString();
        String email = mEdtEmail.getText().toString();
        String telephone = mEdtTelephone.getText().toString();
        String dob = mTvDob.getText().toString();
//        String country = mArrAddressCountry.get(mSpnAddressCountry.getSelectedItemPosition()).nation;

        // Validate

        if (TextUtils.isEmpty(familyName)) {
            DialogUtil.showDialogError(this, mStrFamilyNameNull, null);
            return;
        }


//        if (TextUtils.isEmpty(firstName)) {
//            DialogUtil.showDialogError(this, mStrFirstNameNull, null);
//            return;
//        }

        if (TextUtils.isEmpty(dob)) {
            DialogUtil.showDialogError(this, mStrDobNull, null);
            return;
        }

        if (TextUtils.isEmpty(email)) {
            DialogUtil.showDialogError(this, mStrUserNameNull, null);
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            DialogUtil.showDialogError(this, mStrUserNameError, null);
            return;
        }

        if (TextUtils.isEmpty(telephone)) {
            DialogUtil.showDialogError(this, mStrTelephoneNull, null);
            return;
        }

        if (telephone.length() < 10) {
            DialogUtil.showDialogError(this, mStrTelephoneError, null);
            return;
        }

        if (!schoolName.equalsIgnoreCase(mStrUniversityName)) {
            DialogUtil.showDialogError(this, mStrSchoolError, null);
            return;
        }

        mUserInfo.email = email;
        mUserInfo.name = familyName;
//        mUserInfo.name = firstName;
//        mUserInfo.middleName = middleName;
        mUserInfo.phoneNumber = telephone;
//        mUserInfo.cityId = mArrAddressCity.get(mSpnAddressCity.getSelectedItemPosition()).id;
//        mUserInfo.districtId = mArrAddressDistrict.get(mSpnAddressDistrict.getSelectedItemPosition()).id;
//        mUserInfo.wardId = mArrAddressTown.get(mSpnAddressTown.getSelectedItemPosition()).id;
//        mUserInfo.nationality = country;
        mUserInfo.schoolName = schoolName;
//        mUserInfo.schoolYear = grade;
//        mUserInfo.specializedSubject = className;
//        if (sex.equalsIgnoreCase("Nam")) {
//            mUserInfo.sex = "0";
//        } else {
//            mUserInfo.sex = "1";
//        }
        mUserInfo.dob = dob;
//        mUserInfo.address = address;

        callApiUpdate();
    }

    private void callApiUpdate() {

        if (mFileAvatar != null) {
            mMapFile.put(Constants.AVATAR, mFileAvatar);
        }

        FastNetworking fastNetworking = new FastNetworking(this, new StringCallBackListener() {
            @Override
            public void onResponse(String string) {
                Log.d(TAG, "onResponse : " + string);
                if (string.contains("Chỉnh sửa Tài khoản thành công")) {
                    DialogUtil.showDialogUpdateSuccess(UserInfoActivity.this, string, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mUserInfo = null;
                            PrefUtil.clearSharedPreference(UserInfoActivity.this);
                            finishAffinity();
                            startActivityAnim(LoginActivity.class, null);

                        }
                    });
                    return;
                }
                DialogUtil.showDialogUpdateFail(UserInfoActivity.this, string, null);

            }

            @Override
            public void onError(String messageError) {
                Log.d(TAG, "onError : " + messageError);
            }
        });
        fastNetworking.callApiUpdate(mMapFile, mUserInfo);
    }

    private Gson mGson;
    private DatePickerDialog.OnDateSetListener mListenerPickter;
    private Calendar mCalendar;
    private Calendar mDefaultCalendar;
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
    private UserInfo mUserInfo;

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mStrTitle);

        mGson = new Gson();
    }

    @Override
    protected void initListeners() {
    }

    @Override
    protected void initDatas(Bundle saveInstanceState) {

        mUserInfo = PrefUtil.getSharedPreferenceUserInfo(this);

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

//        mSpnAddressCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                Log.d(TAG, "mSpnAddressCity onItemSelected");
//                if (mArrAddressCity.size() > 0) {
//                    getDistricts(mArrAddressCity.get(position).id);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                Log.d(TAG, "mSpnAddressCity onNothingSelected");
//            }
//
//        });


//        mSpnAddressDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d(TAG, "mSpnAddressDistrict onItemSelected");
//                if (mArrAddressDistrict.size() > 0) {
//                    getWards(mArrAddressDistrict.get(i).id);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                Log.d(TAG, "mSpnAddressDistrict onNothingSelected");
//            }
//        });


    }

    @Override
    protected void getData() {

//        // Sex
//        mArrSex.add(mStrMen);
//        mArrSex.add(mStrWomen);
//        mAdapter = new SexSpinnerAdapter(this, mArrSex, true);
//        mSpnSex.setAdapter(mAdapter);
//
//        getCities();
//
        // School
        String jsonReaderUniversities;
        try {
            jsonReaderUniversities = JSONResourceReader.readFileJSONUniversityFromRaw(this);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<UniversityModel>>() {
            }.getType();
            List<UniversityModel> universityList = (List<UniversityModel>) gson.fromJson(jsonReaderUniversities, listType);
            mArrSchool.clear();
            mArrSchool.addAll(universityList);
            mAdapterSchool = new AutoCompleteUniversityAdapter(this, mArrSchool);
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
//
//        // Country
//        String jsonReader;
//        try {
//            jsonReader = JSONResourceReader.readFileJSONNationsFromRaw(this);
//            Gson gson = new Gson();
//            Type listType = new TypeToken<List<NationModel>>() {
//            }.getType();
//            List<NationModel> nationList = (List<NationModel>) gson.fromJson(jsonReader, listType);
//            mArrAddressCountry.clear();
//            for (NationModel item : nationList) {
//                if (item.id.equalsIgnoreCase("191")) {
//                    mArrAddressCountry.add(0, item);
//                } else {
//                    mArrAddressCountry.add(item);
//                }
//            }
//
//            mAdapterAddressCountry = new NationSpinnerAdapter(this, mArrAddressCountry, true);
//            mSpnAddressCountry.setAdapter(mAdapterAddressCountry);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        if (mUserInfo != null) {
            mEdtFamilyName.setText(mUserInfo.name);
//            mEdtMiddleName.setText(mUserInfo.middleName);
//            mEdtFirstName.setText(mUserInfo.name);
//            mEdtGrade.setText(mUserInfo.schoolYear);

//            for (int i = 0; i < mArrAddressCountry.size(); i++) {
//                if (mUserInfo.nationality.equalsIgnoreCase(mArrAddressCountry.get(i).nation)) {
//                    mSpnAddressCountry.setSelection(i);
//                    break;
//                }
//            }

            mEdtSchool.setText(mUserInfo.schoolName);
            mStrUniversityName = mUserInfo.schoolName;
            mEdtEmail.setText(mUserInfo.email);
            mEdtTelephone.setText(mUserInfo.phoneNumber);

            if (!TextUtils.isEmpty(mUserInfo.avatar)){
                mILAvatarUser.loadUrl(Constants.BASE_IMAGE + mUserInfo.avatar);
            }else {
                mILAvatarUser.loadUrl(mUserInfo.facebookAvatar);
            }

//            mEdtAddress.setText(mUserInfo.address);
//            mEdtClass.setText(mUserInfo.specializedSubject);
//            mTvDob.setText(mUserInfo.dob);
//            if (mUserInfo.sex.equalsIgnoreCase("0")) {
//                mSpnSex.setSelection(0);
//            } else {
//                mSpnSex.setSelection(1);
//            }
        }


    }

    private void getCities() {
        FastNetworking fastNetworking = new FastNetworking(this, new JsonArrayCallBackListener() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Type listType = new TypeToken<List<Cities>>() {
                }.getType();
                List<Cities> list = (List<Cities>) mGson.fromJson(jsonArray.toString(), listType);
                mArrAddressCity.addAll(list);
                mAdapterAddressCity = new CustomSpinnerAdapter(UserInfoActivity.this, mArrAddressCity, true);
                mSpnAddressCity.setAdapter(mAdapterAddressCity);

                if (mUserInfo != null) {
                    for (int i = 0; i < mArrAddressCity.size(); i++) {
                        if (mUserInfo.cityId.equalsIgnoreCase(mArrAddressCity.get(i).id)) {
                            mSpnAddressCity.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onError(String messageError) {

            }
        });

        fastNetworking.callApiGetCities();
    }

    private void getDistricts(String cityId) {
        FastNetworking fastNetworking = new FastNetworking(this, new JsonArrayCallBackListener() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Type listType = new TypeToken<List<Cities>>() {
                }.getType();
                List<Cities> list = (List<Cities>) mGson.fromJson(jsonArray.toString(), listType);
                mArrAddressDistrict.clear();
                mArrAddressDistrict.addAll(list);
                mAdapterAddressDistrict = new CustomSpinnerAdapter(UserInfoActivity.this, mArrAddressDistrict, true);
                mSpnAddressDistrict.setAdapter(mAdapterAddressDistrict);

                if (mUserInfo != null) {
                    for (int i = 0; i < mArrAddressDistrict.size(); i++) {
                        if (mUserInfo.districtId.equalsIgnoreCase(mArrAddressDistrict.get(i).id)) {
                            mSpnAddressDistrict.setSelection(i);
                            break;
                        }
                    }
                }

            }

            @Override
            public void onError(String messageError) {

            }
        });

        fastNetworking.callApiGetDistricts(cityId);
    }

    private void getWards(String districtId) {
        FastNetworking fastNetworking = new FastNetworking(this, new JsonArrayCallBackListener() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Type listType = new TypeToken<List<Cities>>() {
                }.getType();
                List<Cities> list = (List<Cities>) mGson.fromJson(jsonArray.toString(), listType);
                mArrAddressTown.clear();
                mArrAddressTown.addAll(list);
                mAdapterAddressTown = new CustomSpinnerAdapter(UserInfoActivity.this, mArrAddressTown, true);
                mSpnAddressTown.setAdapter(mAdapterAddressTown);

                if (mUserInfo != null) {
                    for (int i = 0; i < mArrAddressTown.size(); i++) {
                        if (mUserInfo.wardId.equalsIgnoreCase(mArrAddressTown.get(i).id)) {
                            mSpnAddressTown.setSelection(i);
                            break;
                        }
                    }
                }

            }

            @Override
            public void onError(String messageError) {

            }
        });

        fastNetworking.callApiGetWards(districtId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finishActivityAnim();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivityAnim();
    }
}
