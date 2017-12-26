package findlocation.bateam.com.findlocation;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseActivity;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.model.PlaceModel;

/**
 * Created by doanhtu on 12/26/17.
 */

public class InfoPlaceActivity extends BaseActivity {

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

    private PlaceModel mItem;


    @Override
    protected int getLayoutView() {
        return R.layout.activity_info_place;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
            mTvTitle.setText(TextUtils.isEmpty(mItem.title) ? mStrTitle : mStrTitle + " " + mItem.title);
            mTvCreatedDate.setText(TextUtils.isEmpty(mItem.createdDate) ? mStrCreatedDate : mStrCreatedDate + " " + mItem.createdDate);
            mTvCity.setText(TextUtils.isEmpty(mItem.city) ? mStrCity : mStrCity + " " + mItem.city);
            mTvDistrict.setText(TextUtils.isEmpty(mItem.district) ? mStrDistrict : mStrDistrict + " " + mItem.district);
            mTvAddress.setText(TextUtils.isEmpty(mItem.addressDetail) ? mStrAdress : mStrAdress + " " + mItem.addressDetail);
            mTvPrice.setText(TextUtils.isEmpty(mItem.price) ? mStrPrice : mStrPrice + " " + mItem.price);
            mTvContractName.setText(TextUtils.isEmpty(mItem.contractName) ? mStrContractName : mStrContractName + " " + mItem.contractName);
            mTvPhone.setText(TextUtils.isEmpty(mItem.phone) || mItem.phone.contains("None") ? mStrPhone : mStrPhone + " " + mItem.phone);
            mTvMobile.setText(TextUtils.isEmpty(mItem.mobile) ? mStrMobile : mStrMobile + " " + mItem.mobile);
            mTvContent.setText(TextUtils.isEmpty(mItem.content) ? mStrContent : mStrContent + "\n" + mItem.content);
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
