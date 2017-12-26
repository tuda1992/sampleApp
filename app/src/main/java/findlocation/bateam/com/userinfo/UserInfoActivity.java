package findlocation.bateam.com.userinfo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.CallbackManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.adapter.CustomSpinnerAdapter;
import findlocation.bateam.com.base.BaseActivity;

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
    @BindView(R.id.spn_school)
    Spinner mSpnSchool;
    @BindView(R.id.edt_class)
    EditText mEdtClass;
    @BindView(R.id.edt_grade)
    EditText mEdtGrade;
    @BindView(R.id.edt_email)
    EditText mEdtEmail;
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

    @BindString(R.string.text_button_date_picker_ok)
    String mStrOk;
    @BindString(R.string.text_button_date_picker_no)
    String mStrNo;
    @BindString(R.string.title_user_info)
    String mStrTitle;
    @BindString(R.string.text_sex_men)
    String mStrMen;
    @BindString(R.string.text_sex_women)
    String mStrWomen;

    @OnClick(R.id.tv_dob)
    public void onClickDob() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, mListenerPickter, mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, mDefaultCalendar.get(Calendar.YEAR) - 16);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.MONTH, 0);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, mStrOk, datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, mStrNo, datePickerDialog);
        datePickerDialog.getDatePicker().setMaxDate(mDefaultCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.btn_change_info)
    public void onClickChangeInfo(){

    }

    private DatePickerDialog.OnDateSetListener mListenerPickter;
    private Calendar mCalendar;
    private Calendar mDefaultCalendar;
    private CallbackManager mCallBackManager;
    private CustomSpinnerAdapter mAdapter;
    private CustomSpinnerAdapter mAdapterAddressCity;
    private CustomSpinnerAdapter mAdapterAddressTown;
    private CustomSpinnerAdapter mAdapterAddressDistrict;
    private CustomSpinnerAdapter mAdapterAddressCountry;
    private CustomSpinnerAdapter mAdapterSchool;
    private List<String> mArrSex = new ArrayList<>();
    private List<String> mArrAddressCity = new ArrayList<>();
    private List<String> mArrAddressTown = new ArrayList<>();
    private List<String> mArrAddressDistrict = new ArrayList<>();
    private List<String> mArrAddressCountry = new ArrayList<>();
    private List<String> mArrSchool = new ArrayList<>();

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

        // Sex
        mArrSex.add(mStrMen);
        mArrSex.add(mStrWomen);
        mAdapter = new CustomSpinnerAdapter(this, mArrSex, false);
        mSpnSex.setAdapter(mAdapter);

        // City
        mArrAddressCity.add("Hà Nội");
        mArrAddressCity.add("Đà Nẵng");
        mArrAddressCity.add("Nha Trang");
        mArrAddressCity.add("Hải Phòng");
        mArrAddressCity.add("Thái Bình");
        mArrAddressCity.add("Nam Định");
        mAdapterAddressCity = new CustomSpinnerAdapter(this, mArrAddressCity, false);
        mSpnAddressCity.setAdapter(mAdapterAddressCity);

        // Town
        mArrAddressTown.add("Văn Chương");
        mArrAddressTown.add("Hàng Bột");
        mArrAddressTown.add("Ô Chợ Dừa");
        mArrAddressTown.add("Thổ Quan");
        mArrAddressTown.add("Quan Thổ");
        mAdapterAddressTown = new CustomSpinnerAdapter(this, mArrAddressTown, false);
        mSpnAddressTown.setAdapter(mAdapterAddressTown);

        // District
        mArrAddressDistrict.add("Đống Đa");
        mArrAddressDistrict.add("Ba Đình");
        mArrAddressDistrict.add("Hoàn Kiếm");
        mArrAddressDistrict.add("Long Biên");
        mArrAddressDistrict.add("Thanh Xuân");
        mArrAddressDistrict.add("Cầu giấy");
        mAdapterAddressDistrict = new CustomSpinnerAdapter(this, mArrAddressDistrict, false);
        mSpnAddressDistrict.setAdapter(mAdapterAddressDistrict);


        // Country
        mArrAddressCountry.add("Việt Nam");
        mArrAddressCountry.add("Trung Quốc");
        mArrAddressCountry.add("Nhật Bản");
        mArrAddressCountry.add("Mỹ");
        mArrAddressCountry.add("Nga");
        mArrAddressCountry.add("Hàn Quốc");
        mAdapterAddressCountry = new CustomSpinnerAdapter(this, mArrAddressCountry, false);
        mSpnAddressCountry.setAdapter(mAdapterAddressCountry);

        // School
        mArrSchool.add("Học viện Ngân Hàng");
        mArrSchool.add("Học viện Hàng Không");
        mArrSchool.add("Học viện Ngoại Giao");
        mArrSchool.add("Đại học Ngoại Thương");
        mArrSchool.add("Đại học Kinh Tế Quốc Dân");
        mArrSchool.add("Đại học Bách Khoa");
        mAdapterSchool = new CustomSpinnerAdapter(this, mArrSchool, false);
        mSpnSchool.setAdapter(mAdapterSchool);

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initDatas(Bundle saveInstanceState) {
        mSpnSex.setSelection(0);
        mSpnAddressCity.setSelection(0);
        mSpnAddressTown.setSelection(0);
        mSpnAddressDistrict.setSelection(0);
        mSpnSchool.setSelection(0);

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

    }

    @Override
    protected void getData() {

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
