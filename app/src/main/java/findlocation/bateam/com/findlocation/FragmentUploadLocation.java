package findlocation.bateam.com.findlocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.cameraview.CameraView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.adapter.FindLocationAdapter;
import findlocation.bateam.com.adapter.UploadLocationAdapter;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.IPermissionCallBack;
import findlocation.bateam.com.util.CameraUtil;
import findlocation.bateam.com.util.ImagePicker;
import findlocation.bateam.com.util.PermissionUtils;

/**
 * Created by acv on 12/14/17.
 */

public class FragmentUploadLocation extends BaseFragment implements UploadLocationAdapter.ICallBackItemClick {

    @BindView(R.id.camera_view)
    CameraView mCameraView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.btn_take_picture)
    Button mBtnTakePickture;
    @BindString(R.string.text_button_next)
    String mStrBtnNext;
    @BindString(R.string.text_button_take_picture)
    String mStrBtnTakePicture;


    private UploadLocationAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mIsCapture = true;
    private List<Bitmap> mListData = new ArrayList<>();

    @OnClick(R.id.btn_take_picture)
    public void onClickCapture() {
        if (mListData.size() == 5) {
            startActivityAnim(UploadLocationActivity.class, null);
        } else {
            if (mIsCapture && mListData.size() < 5) {
                mCameraView.takePicture();
                mIsCapture = false;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsCapture = true;
                    }
                }, 1000);

            }
        }
    }

    private CameraView.Callback mCameraCallback = new CameraView.Callback() {
        @Override
        public void onCameraOpened(CameraView cameraView) {
            super.onCameraOpened(cameraView);
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            super.onCameraClosed(cameraView);
        }

        @Override
        public void onPictureTaken(CameraView cameraView, byte[] data) {
            super.onPictureTaken(cameraView, data);
            Bitmap bitmap = CameraUtil.resizeBitmap(data, cameraView);
            mListData.add(bitmap);
            mAdapter.notifyDataSetChanged();
            Log.d(TAG, "onPictureTaken data = " + data);
            if (mListData.size() == 5) {
                mBtnTakePickture.setText(mStrBtnNext);
            } else {
                mBtnTakePickture.setText(mStrBtnTakePicture);
            }

        }
    };

    @Override
    protected void onBackPressFragment() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_upload_location;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {

        // Set up recyclerview
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRvData.setLayoutManager(mLinearLayoutManager);
        mRvData.setHasFixedSize(true);
        mAdapter = new UploadLocationAdapter(getActivity(), mListData, this);
        mRvData.setAdapter(mAdapter);
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.checkPermission(getActivity(), new String[]{Manifest.permission.CAMERA}, Constants.REQUEST_CAMERA, new IPermissionCallBack() {
                @Override
                public void onPermissionReady() {
                    mCameraView.removeCallback(mCameraCallback);
                    mCameraView.addCallback(mCameraCallback);
                    mCameraView.start();
                }
            });
        } else {
            mCameraView.removeCallback(mCameraCallback);
            mCameraView.addCallback(mCameraCallback);
            mCameraView.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mCameraView.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.REQUEST_CAMERA:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (grantResults.length > 0) {
                        //Check dialog not show again
                        boolean isNotAgain = false;
                        boolean isPermissionGranted = false;
                        for (int i = 0; i < permissions.length; i++) {
                            if (!shouldShowRequestPermissionRationale(
                                    permissions[i]) && (grantResults[i] != PackageManager.PERMISSION_GRANTED)) {
                                isNotAgain = true;
                                break;
                            } else if (getActivity().checkSelfPermission(permissions[i]) == PackageManager.PERMISSION_GRANTED) {
                                isPermissionGranted = true;
                            } else if (getActivity().checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                                isPermissionGranted = false;
                                break;
                            }
                        }
                        if (isNotAgain) {
                            PermissionUtils.showDialogTurnOnPermission(getString(R.string.text_permission_camera), getActivity());
                        }

                        if (isPermissionGranted) {
                            mCameraView.removeCallback(mCameraCallback);
                            mCameraView.addCallback(mCameraCallback);
                            mCameraView.start();
                        }
                    }
                } else {
                    mCameraView.removeCallback(mCameraCallback);
                    mCameraView.addCallback(mCameraCallback);
                    mCameraView.start();
                }
                break;
        }
    }

    @Override
    public void onItemClick(int position) {

    }
}
