package findlocation.bateam.com.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import findlocation.bateam.com.MainActivity;
import findlocation.bateam.com.R;
import findlocation.bateam.com.SmsReceiver;
import findlocation.bateam.com.api.FastNetworking;
import findlocation.bateam.com.base.BaseActivity;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.IPermissionCallBack;
import findlocation.bateam.com.listener.StringCallBackListener;
import findlocation.bateam.com.model.UserRegister;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.ImagePicker;
import findlocation.bateam.com.util.PermissionUtils;
import findlocation.bateam.com.util.PrefUtil;

/**
 * Created by acv on 12/5/17.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindString(R.string.title_signin)
    String mTitleSignIn;
    @BindString(R.string.title_signup)
    String mTitleSignUp;
    @BindString(R.string.title_forget_password)
    String mTitleForgetPassword;

    public static File mFileLicense = null;
    public static File mFileAvatar = null;



    @Override
    protected int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(mTitleSignIn);
        FragmentSignIn fragmentSignIn = new FragmentSignIn();
        replaceFragment(fragmentSignIn, Constants.FRAGMENT_SIGN_IN);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter("broadCastName"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    // Add this inside your class
    SmsReceiver broadcastReceiver =  new SmsReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle b = intent.getExtras();

            String message = b.getString("message");

            Log.d(TAG, "" + message);

            updateList(message);
        }
    };


    @Override
    protected void initDatas(Bundle saveInstanceStatte) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.checkPermission(this, new String[]{Manifest.permission.READ_SMS}, Constants.REQUEST_READ_SMS, new IPermissionCallBack() {
                @Override
                public void onPermissionReady() {

                }
            });
        }


    }


    @Override
    protected void getData() {

    }

    public void goFragmentSignUp() {
        FragmentSignUpInfo fragmentSignUp = new FragmentSignUpInfo();
        replaceFragment(fragmentSignUp, Constants.FRAGMENT_SIGN_UP_INFO);
        getSupportActionBar().setTitle(mTitleSignUp);
    }

    public void goFragmentSignIn() {
        getSupportActionBar().setTitle(mTitleSignIn);
        finishFragment();
    }

    public void setToolBarSignIn(){
        getSupportActionBar().setTitle(mTitleSignIn);
    }

    public void setToolBarSignUp(){
        getSupportActionBar().setTitle(mTitleSignUp);
    }

    public void goFragmentForgetPassword() {
        FragmentForgetPassword fragmentForgetPassword = new FragmentForgetPassword();
        replaceFragment(fragmentForgetPassword, Constants.FRAGMENT_FORGET_PASSWORD);
        getSupportActionBar().setTitle(mTitleForgetPassword);
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
                            } else if (checkSelfPermission(permissions[i]) == PackageManager.PERMISSION_GRANTED) {
                                isPermissionGranted = true;
                            } else if (checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                                isPermissionGranted = false;
                                break;
                            }
                        }
                        if (isNotAgain) {
                            PermissionUtils.showDialogTurnOnPermission(getString(R.string.text_permission_camera), this);
                        }

                        if (isPermissionGranted) {
                            Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
                            startActivityForResult(chooseImageIntent, Constants.PICK_IMAGE_ID);
                        }
                    }
                } else {
                    Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
                    startActivityForResult(chooseImageIntent, Constants.PICK_IMAGE_ID);
                }
                break;
            case Constants.REQUEST_READ_SMS:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container_body);
        f.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void updateList(final String smsMsg) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container_body);
        if (f != null && f instanceof FragmentSignUpApprove) {
            ((FragmentSignUpApprove) f).updateActiveCode(smsMsg);
        }
    }

}
