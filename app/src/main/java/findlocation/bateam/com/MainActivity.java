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
import findlocation.bateam.com.login.FragmentSignIn;
import findlocation.bateam.com.login.LoginActivity;
import findlocation.bateam.com.navigation.FragmentDrawer;
import findlocation.bateam.com.userinfo.FragmentUserInfo;
import findlocation.bateam.com.util.ImagePicker;
import findlocation.bateam.com.util.PermissionUtils;

public class MainActivity extends BaseActivity implements FragmentDrawer.FragmentDrawerListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private int mCurrentTab = -1;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
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

        if (position == 3) {
            mCurrentTab = -1;
            startActivityAnim(LoginActivity.class,null);
            finishActivityAnim();
            return;
        }

        BaseFragment fragment = null;
        String title = getString(R.string.app_name);
        String fragmentName = "";
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
            case 2:
                mCurrentTab = 2;
                fragment = new FragmentUserInfo();
                title = getString(R.string.title_user_info);
                fragmentName = Constants.FRAGMENT_USER_INFO;
                break;
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
}
