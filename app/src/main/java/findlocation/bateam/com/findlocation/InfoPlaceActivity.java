package findlocation.bateam.com.findlocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.adapter.UltraPagerAdapter;
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
    private String[] mStrSeparated;

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
    @BindView(R.id.ultra_viewpager)
    UltraViewPager mUtViewPager;

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
    @BindString(R.string.title_detail_location)
    String mStrTitleLocation;


    private UltraViewPager.Orientation mGravityIndicator;

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
        getSupportActionBar().setTitle(mStrTitleLocation);

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

            Log.d(TAG, "URL ITEM = " + mItem.imageLink);

            if (!TextUtils.isEmpty(mItem.imageLink) || !mItem.imageLink.equalsIgnoreCase("NULL")) {
                mStrSeparated = mItem.imageLink.replace("~", "").split(";");

                mUtViewPager.setVisibility(View.VISIBLE);
                List<String> listUrl = new ArrayList<>();
                for (int i = 0; i < mStrSeparated.length; i++) {
                    listUrl.add(mStrSeparated[i]);
                }

                mUtViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
                //initialize UltraPagerAdapter，and add child view to UltraViewPager
                UltraPagerAdapter adapter = new UltraPagerAdapter(this, false, listUrl, new UltraPagerAdapter.ItemClickCallBackListener() {
                    @Override
                    public void onItemClick(String url) {
                        Log.d(TAG, "onItemClick " + url);

                        FragmentDialogImage fragmentDialogImage = FragmentDialogImage.newInstance(url);
                        fragmentDialogImage.show(getSupportFragmentManager(), "fragmentDialogImage");

                    }
                });
                mUtViewPager.setAdapter(adapter);
                mUtViewPager.setMultiScreen(0.6f);
                mUtViewPager.setItemRatio(1.0f);

                mUtViewPager.setAutoMeasureHeight(true);
                mUtViewPager.setPageTransformer(false, new UltraDepthScaleTransformer());
                mGravityIndicator = UltraViewPager.Orientation.HORIZONTAL;

                if (mUtViewPager.getIndicator() == null) {
                    mUtViewPager.initIndicator();
                    mUtViewPager.getIndicator().setOrientation(mGravityIndicator);
                }

            } else {
                mUtViewPager.setVisibility(View.GONE);
            }


            mMobile = TextUtils.isEmpty(mItem.mobile) ? "" : mItem.mobile;
            mPhone = TextUtils.isEmpty(mItem.phone) || mItem.phone.contains("None") ? "" : mItem.phone;

            mTvTitle.setText(mItem.title);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
            Date d = null;
            try {
                d = sdf.parse(mItem.createdDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedTime = output.format(d);

            String htmlDate = TextUtils.isEmpty(mItem.createdDate) ? "<b>" + mStrCreatedDate + "</b>" : "<b>" + mStrCreatedDate + "</b>" + " " + formattedTime;
            mTvCreatedDate.setText(Html.fromHtml(htmlDate));

            String htmlCity = TextUtils.isEmpty(mItem.province) ? "<b>" + mStrCity + "</b>" : "<b>" + mStrCity + "</b>" + " " + mItem.province;
            mTvCity.setText(Html.fromHtml(htmlCity));

            String htmlDistrict = TextUtils.isEmpty(mItem.district) ? "<b>" + mStrDistrict + "</b>" : "<b>" + mStrDistrict + "</b>" + " " + mItem.district;
            mTvDistrict.setText(Html.fromHtml(htmlDistrict));

            String htmlAddress = TextUtils.isEmpty(mItem.street) ? "<b>" + mStrAdress + "</b>" : "<b>" + mStrAdress + "</b>" + " " + mItem.street;
            mTvAddress.setText(Html.fromHtml(htmlAddress));

            NumberFormat formatter = new DecimalFormat("#,###");
            String formatPrice = "";
            try {
                formatPrice = formatter.format(Double.parseDouble(TextUtils.isEmpty(mItem.price) ? "0" : mItem.price)) + " VNĐ";
            } catch (Exception e) {
                formatPrice = mItem.price;
            }

            String htmlPrice = TextUtils.isEmpty(mItem.price) ? "<b>" + mStrPrice + "</b>" : "<b>" + mStrPrice + "</b>" + " " + formatPrice;
            mTvPrice.setText(Html.fromHtml(htmlPrice));

            String htmlContract = TextUtils.isEmpty(mItem.contractName) ? "<b>" + mStrContractName + "</b>" : "<b>" + mStrContractName + "</b>" + " " + mItem.contractName;
            mTvContractName.setText(Html.fromHtml(htmlContract));

            String htmlPhone = TextUtils.isEmpty(mItem.phone) || mItem.phone.contains("None") ? "<b>" + mStrPhone + "</b>" : "<b>" + mStrPhone + "</b>" + " <u>" + mItem.phone.replace(".", "") + "</u>";
            mTvPhone.setText(Html.fromHtml(htmlPhone));

            String htmlMobile = TextUtils.isEmpty(mItem.mobile) ? "<b>" + mStrMobile + "</b>" : "<b>" + mStrMobile + "</b>" + " <u>" + mItem.mobile.replace(".", "") + "</u>";
            mTvMobile.setText(Html.fromHtml(htmlMobile));

            String htmlContent = TextUtils.isEmpty(mItem.content) ? "<b>" + mStrContent + "</b>" : "<b>" + mStrContent + "</b>" + "<br>" + mItem.content + "</br";
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
