package findlocation.bateam.com.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import findlocation.bateam.com.R;

/**
 * Created by acv on 11/29/17.
 */

public abstract class BaseFragment extends Fragment {

    protected String TAG = getClass().getSimpleName();
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutView(), container, false);
        if (view != null) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                        onBackPressFragment();
                        return true;
                    }
                    return false;
                }
            });
        }

        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initDatas(savedInstanceState);
        getData();
        return view;
    }

    protected abstract void onBackPressFragment();

    protected abstract int getLayoutView();

    protected abstract void initListeners();

    protected abstract void initViews(View view);

    protected abstract void initDatas(Bundle savedInstanceState);

    protected abstract void getData();

    protected void finishFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    public void startActivityAnim(Class activity, Bundle b) {
        Intent i = new Intent(getActivity(), activity);
        if (b != null) {
            i.putExtras(b);
        }
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    protected void finishActivityAnim() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    protected void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void addFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected Fragment getFragmentByTag(String tag) {
        return getActivity().getSupportFragmentManager().findFragmentByTag(tag);
    }

}
