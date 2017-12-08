package findlocation.bateam.com.findlocation;

import android.os.Bundle;
import android.view.View;

import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseFragment;

/**
 * Created by acv on 12/4/17.
 */

public class FragmentFindLocation extends BaseFragment {

    @Override
    protected void onBackPressFragment() {
        getActivity().finish();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_find_location;
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
