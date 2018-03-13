package findlocation.bateam.com.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.IPermissionCallBack;
import findlocation.bateam.com.util.ImagePicker;
import findlocation.bateam.com.util.PermissionUtils;

/**
 * Created by acv on 12/5/17.
 */

public class FragmentSignUp extends BaseFragment {

    @BindView(R.id.iv_card)
    ImageView mIvCard;
    @BindView(R.id.btn_take_picture)
    FloatingActionButton mBtnTakePicture;
    @BindView(R.id.btn_next)
    FloatingActionButton mBtnNext;

    @OnClick(R.id.btn_take_picture)
    public void onClickTakePicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.checkPermission(getActivity(), new String[]{Manifest.permission.CAMERA}, Constants.REQUEST_CAMERA, new IPermissionCallBack() {
                @Override
                public void onPermissionReady() {
                    Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
                    startActivityForResult(chooseImageIntent, Constants.PICK_IMAGE_ID);
                }
            });
        } else {
            Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
            startActivityForResult(chooseImageIntent, Constants.PICK_IMAGE_ID);
        }
    }

    @OnClick(R.id.btn_next)
    public void onClickButtonNext() {
        FragmentSignUpSelfie fragmentSignUpSelfie = new FragmentSignUpSelfie();
        addFragment(fragmentSignUpSelfie, Constants.FRAGMENT_SIGN_UP_SELFIE);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_sign_up;
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

    @Override
    public void onBackPressFragment() {
        ((LoginActivity) getActivity()).goFragmentSignIn();
        LoginActivity.mFileLicense = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        LoginActivity.mFileLicense = ImagePicker.getFileFromResult(getActivity(), resultCode, data);
        Bitmap bm = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
        if (bm != null) {
            LoginActivity.mFileLicense = ImagePicker.convertToFile(getActivity(), bm);
            mIvCard.setVisibility(View.VISIBLE);
            mIvCard.setImageBitmap(bm);

        }
        if (mIvCard.getVisibility() == View.VISIBLE) {
            mBtnNext.setVisibility(View.VISIBLE);
        }
    }
}
