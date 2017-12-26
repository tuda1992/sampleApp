package findlocation.bateam.com;

import android.os.Bundle;
import android.os.Handler;

import findlocation.bateam.com.base.BaseActivity;
import findlocation.bateam.com.login.LoginActivity;
import findlocation.bateam.com.util.PrefUtil;

/**
 * Created by doanhtu on 12/26/17.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initDatas(Bundle saveInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PrefUtil.getSharedPreferenceSaveData(SplashActivity.this)) {
                    startActivityAnim(MainActivity.class, null);
                    finishActivityAnim();
                    return;
                }
                startActivityAnim(LoginActivity.class, null);
                finishActivityAnim();
            }
        }, 2000);
    }

    @Override
    protected void getData() {

    }
}
