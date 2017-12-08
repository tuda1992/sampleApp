package findlocation.bateam.com.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import findlocation.bateam.com.R;

/**
 * Created by acv on 11/29/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        initViews();
        initListeners();
        initDatas(savedInstanceState);
        getData();


    }

    // Get layout view id
    protected abstract int getLayoutView();

    // Init View
    protected abstract void initViews();

    // Init listener
    protected abstract void initListeners();

    // Init daata
    protected abstract void initDatas(Bundle saveInstanceStatte);

    // Get data from server
    protected abstract void getData();


    protected void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void addFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void finishFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
    }

    public void startActivityAnim(Class activity, Bundle b) {
        Intent i = new Intent(this, activity);
        if (b != null) {
            i.putExtras(b);
        }
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    protected void finishActivityAnim() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    protected Fragment getFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

}
