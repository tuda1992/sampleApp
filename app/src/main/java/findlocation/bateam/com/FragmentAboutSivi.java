package findlocation.bateam.com;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import findlocation.bateam.com.base.BaseFragment;

/**
 * Created by doanhtu on 3/13/18.
 */

public class FragmentAboutSivi extends BaseFragment {

    private int mIntOut;

    @Override
    protected void onBackPressFragment() {
        mIntOut++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIntOut = 0;
            }
        }, 500);
        if (mIntOut == 2) {
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), "Ấn back 2 lần để thoát ứng dụng", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_about_sivi;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initViews(View view) {

    }


    @Override
    protected void initDatas(Bundle savedInstanceState) {

    }

    @Override
    protected void getData() {

    }
}
