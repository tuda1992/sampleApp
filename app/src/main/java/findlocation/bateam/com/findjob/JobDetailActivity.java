package findlocation.bateam.com.findjob;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseActivity;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.model.JobModel;

/**
 * Created by doanhtu on 1/12/18.
 */

public class JobDetailActivity extends BaseActivity {

    private JobModel mItem;

    @BindView(R.id.tv_job_title)
    TextView mTvJobTitle;
    @BindView(R.id.tv_company)
    TextView mTvCompany;
    @BindView(R.id.tv_offer)
    TextView mTvOffer;
    @BindView(R.id.tv_experience)
    TextView mTvExperience;
    @BindView(R.id.tv_certificate)
    TextView mTvCertificate;
    @BindView(R.id.tv_hiring)
    TextView mTvHiring;
    @BindView(R.id.tv_career)
    TextView mTvCareer;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_level)
    TextView mTvLevel;
    @BindView(R.id.tv_form)
    TextView mTvForm;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_age)
    TextView mTvAge;


    @BindView(R.id.ll_offer)
    LinearLayout mLlOffer;
    @BindView(R.id.ll_experience)
    LinearLayout mLlExperience;
    @BindView(R.id.ll_certificate)
    LinearLayout mLlCertificate;
    @BindView(R.id.ll_hiring)
    LinearLayout mLlHiring;
    @BindView(R.id.ll_career)
    LinearLayout mLlCareer;
    @BindView(R.id.ll_location)
    LinearLayout mLlLocation;
    @BindView(R.id.ll_level)
    LinearLayout mLlLevel;
    @BindView(R.id.ll_form)
    LinearLayout mLlForm;
    @BindView(R.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R.id.ll_age)
    LinearLayout mLlAge;
    @BindView(R.id.ll_benefits)
    LinearLayout mLlBenefits;

    @BindView(R.id.tv_description)
    TextView mTvDescription;
    @BindView(R.id.tv_benefits)
    TextView mTvBenefits;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindString(R.string.title_detail_job)
    String mStrTitle;

    @OnClick(R.id.btn_apply)
    public void onClickApplyJob() {
        String url = "https://www.vietnamworks.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_job_detail;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(mStrTitle);
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
            mTvJobTitle.setText(mItem.jobTitle);
            mTvCompany.setText(mItem.companyName);

            if (TextUtils.isEmpty(mItem.salary) || mItem.salary.contains("NULL")) {
                mLlOffer.setVisibility(View.GONE);
            } else {
                mTvOffer.setText(mItem.salary);
            }

            if (TextUtils.isEmpty(mItem.experienceRequirement) || mItem.experienceRequirement.contains("NULL")) {
                mLlExperience.setVisibility(View.GONE);
            } else {
                mTvExperience.setText(mItem.experienceRequirement);
            }

            if (TextUtils.isEmpty(mItem.educationRequirement) || mItem.educationRequirement.contains("NULL")) {
                mLlCertificate.setVisibility(View.GONE);
            } else {
                mTvCertificate.setText(mItem.educationRequirement);
            }

            if (TextUtils.isEmpty(mItem.industry) || mItem.industry.contains("NULL")) {
                mLlCareer.setVisibility(View.GONE);
            } else {
                mTvCareer.setText(mItem.industry);
            }

            if (TextUtils.isEmpty(mItem.workingArea) || mItem.workingArea.contains("NULL")) {
                mLlLocation.setVisibility(View.GONE);
            } else {
                mTvLocation.setText(mItem.workingArea);
            }

            if (TextUtils.isEmpty(mItem.jobLevel) || mItem.jobLevel.contains("NULL")) {
                mLlLevel.setVisibility(View.GONE);
            } else {
                mTvLevel.setText(mItem.jobLevel);
            }

            if (TextUtils.isEmpty(mItem.ageRequirement) || mItem.ageRequirement.contains("NULL")) {
                mLlAge.setVisibility(View.GONE);
            } else {
                mTvAge.setText(mItem.ageRequirement);
            }

            if (TextUtils.isEmpty(mItem.genderRequirement) || mItem.genderRequirement.contains("NULL")) {
                mLlSex.setVisibility(View.GONE);
            } else {
                mTvSex.setText(mItem.genderRequirement);
            }

            mTvDescription.setText(mItem.jobDescription);
            if (TextUtils.isEmpty(mItem.benefits) || mItem.benefits.contains("NULL")) {
                mLlBenefits.setVisibility(View.GONE);
            } else {
                mTvBenefits.setText(mItem.benefits);
            }

        }

    }

    @Override
    protected void getData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivityAnim();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finishActivityAnim(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
