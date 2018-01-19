package findlocation.bateam.com.findlocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseActivity;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.model.PlaceModel;

/**
 * Created by doanhtu on 12/26/17.
 */

public class InfoPlaceActivity extends BaseActivity {

    private final int CALL_ACTION = 9999;
    private PlaceModel mItem;
    private String mMobile;
    private String mPhone;
    private String mNumberPhone;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_created_date)
    TextView mTvCreatedDate;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.tv_district)
    TextView mTvDistrict;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_contract_name)
    TextView mTvContractName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_title_logo)
    TextView mTvTitleLogo;

    @BindString(R.string.text_info_title)
    String mStrTitle;
    @BindString(R.string.text_info_created_date)
    String mStrCreatedDate;
    @BindString(R.string.text_info_city)
    String mStrCity;
    @BindString(R.string.text_info_district)
    String mStrDistrict;
    @BindString(R.string.text_info_address_detail)
    String mStrAdress;
    @BindString(R.string.text_info_price)
    String mStrPrice;
    @BindString(R.string.text_info_contract_name)
    String mStrContractName;
    @BindString(R.string.text_info_phone)
    String mStrPhone;
    @BindString(R.string.text_info_mobile)
    String mStrMobile;
    @BindString(R.string.text_info_content)
    String mStrContent;

    @OnClick(R.id.ll_mobile)
    public void onClickCallMobile() {
        if (TextUtils.isEmpty(mMobile)) {
            return;
        }
        mNumberPhone = mMobile;
        callPhone(mNumberPhone);
    }

    @OnClick(R.id.ll_phone)
    public void onClickCallPhone() {
        if (TextUtils.isEmpty(mPhone)) {
            return;
        }
        mNumberPhone = mPhone;
        callPhone(mNumberPhone);
    }

    private void callPhone(String phoneNumber) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_ACTION);
            }
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALL_ACTION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone(mNumberPhone);
                }
                return;
            }
        }
    }


    @Override
    protected int getLayoutView() {
        return R.layout.activity_info_place;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        mIvLogo.setVisibility(View.VISIBLE);
        mTvTitleLogo.setVisibility(View.VISIBLE);
        mTvTitleLogo.setText("SiVi");
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initDatas(Bundle saveInstanceState) {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mItem = b.getParcelable(Constants.BUNDLE_PLACE_ITEM);
        }

        if (mItem != null) {
            mMobile = TextUtils.isEmpty(mItem.mobile) ? "" : mItem.mobile;
            mPhone = TextUtils.isEmpty(mItem.phone) || mItem.phone.contains("None") ? "" : mItem.phone;

            String htmlTitle = TextUtils.isEmpty(mItem.title) ? "<i>" + mStrTitle + "</i>" : "<i>" + mStrTitle + "</i>" + " " + mItem.title;
            mTvTitle.setText(Html.fromHtml(htmlTitle));

            String htmlDate = TextUtils.isEmpty(mItem.createdDate) ? "<i>" + mStrCreatedDate + "</i>" : "<i>" + mStrCreatedDate + "</i>" + " " + mItem.createdDate;
            mTvCreatedDate.setText(Html.fromHtml(htmlDate));

            String htmlCity = TextUtils.isEmpty(mItem.city) ? "<i>" + mStrCity + "</i>" : "<i>" + mStrCity + "</i>" + " " + mItem.city;
            mTvCity.setText(Html.fromHtml(htmlCity));

            String htmlDistrict = TextUtils.isEmpty(mItem.district) ? "<i>" + mStrDistrict + "</i>" : "<i>" + mStrDistrict + "</i>" + " " + mItem.district;
            mTvDistrict.setText(Html.fromHtml(htmlDistrict));

            String htmlAddress = TextUtils.isEmpty(mItem.addressDetail) ? "<i>" + mStrAdress + "</i>" : "<i>" + mStrAdress + "</i>" + " " + mItem.addressDetail;
            mTvAddress.setText(Html.fromHtml(htmlAddress));

            NumberFormat formatter = new DecimalFormat("#,###");
            String formatPrice = formatter.format(Double.parseDouble(TextUtils.isEmpty(mItem.price) ? "0" : mItem.price)) + " VNĐ";

            String htmlPrice = TextUtils.isEmpty(mItem.price) ? "<i>" + mStrPrice + "</i>" : "<i>" + mStrPrice + "</i>" + " " + formatPrice;
            mTvPrice.setText(Html.fromHtml(htmlPrice));

            String htmlContract = TextUtils.isEmpty(mItem.contractName) ? "<i>" + mStrContractName + "</i>" : "<i>" + mStrContractName + "</i>" + " " + mItem.contractName;
            mTvContractName.setText(Html.fromHtml(htmlContract));

            String htmlPhone = TextUtils.isEmpty(mItem.phone) || mItem.phone.contains("None") ? "<i>" + mStrPhone + "</i>" : "<i>" + mStrPhone + "</i>" + " <u>" + mItem.phone + "</u>";
            mTvPhone.setText(Html.fromHtml(htmlPhone));

            String htmlMobile = TextUtils.isEmpty(mItem.mobile) ? "<i>" + mStrMobile + "</i>" : "<i>" + mStrMobile + "</i>" + " <u>" + mItem.mobile + "</u>";
            mTvMobile.setText(Html.fromHtml(htmlMobile));

            String htmlContent = TextUtils.isEmpty(mItem.content) ? "<i>" + mStrContent + "</i>" : "<i>" + mStrContent + "</i>" + "<br>" + mItem.content + "</br";
            mTvContent.setText(Html.fromHtml(htmlContent));
        }

    }

    @Override
    protected void getData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finishActivityAnim(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivityAnim();
    }
}
