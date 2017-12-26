package findlocation.bateam.com;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import findlocation.bateam.com.base.BaseActivity;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.findjob.FragmentFindJob;
import findlocation.bateam.com.findlocation.FragmentFindLocation;
import findlocation.bateam.com.findlocation.FragmentUploadLocation;
import findlocation.bateam.com.login.FragmentSignIn;
import findlocation.bateam.com.login.LoginActivity;
import findlocation.bateam.com.navigation.FragmentDrawer;
import findlocation.bateam.com.userinfo.FragmentUserInfo;
import findlocation.bateam.com.util.ImagePicker;
import findlocation.bateam.com.util.PermissionUtils;
import findlocation.bateam.com.util.PrefUtil;

public class MainActivity extends BaseActivity implements FragmentDrawer.FragmentDrawerListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private int mCurrentTab = -1;
    private boolean mIsMaster;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mIsMaster = bundle.getBoolean(Constants.BUNDLE_IS_MASTER, false);
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar, mIsMaster);
        drawerFragment.setDrawerListener(this);

        displayView(0);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initDatas(Bundle saveInstanceStatte) {

    }

    @Override
    protected void getData() {

    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        if (mCurrentTab == position) {
            return;
        }

        BaseFragment fragment = null;
        String title = getString(R.string.app_name);
        String fragmentName = "";

        if (mIsMaster) {
            if (position == 1) {
                mCurrentTab = -1;
                PrefUtil.clearSharedPreference(this);
                startActivityAnim(LoginActivity.class, null);
                finishActivityAnim();
                return;
            }

            switch (position) {
                case 0:
                    mCurrentTab = 0;
                    fragment = new FragmentUploadLocation();
                    title = getString(R.string.title_upload_location);
                    fragmentName = Constants.FRAGMENT_UPLOAD_LOCATION;
                    break;
            }
        } else {
            if (position == 2) {
                mCurrentTab = -1;
                PrefUtil.clearSharedPreference(this);
                startActivityAnim(LoginActivity.class, null);
                finishActivityAnim();
                return;
            }

            switch (position) {
                case 0:
                    mCurrentTab = 0;
                    fragment = new FragmentFindLocation();
                    title = getString(R.string.title_find_location);
                    fragmentName = Constants.FRAGMENT_FIND_LOCATION;
                    break;
                case 1:
                    mCurrentTab = 1;
                    fragment = new FragmentFindJob();
                    title = getString(R.string.title_find_job);
                    fragmentName = Constants.FRAGMENT_FIND_JOB;
                    break;
            }
        }

        if (fragment != null) {
            replaceFragment(fragment, fragmentName);
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
