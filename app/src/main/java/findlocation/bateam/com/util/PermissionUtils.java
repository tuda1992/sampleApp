package findlocation.bateam.com.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import findlocation.bateam.com.listener.IPermissionCallBack;

/**
 * Created by acv on 12/7/17.
 */

public class PermissionUtils {

    /*  Check permission API>23
      param   context : Activity call permission
              permissions: List permission use (Manifest.permission.xxx)
              requestCode: Request code callback in Activity
              permissionCallBack: function while permission ready*/
    public static void checkPermission(Activity context, String[] permissions, int requestCode, IPermissionCallBack permissionCallBack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean isNeedPermission = false;
            for (int i = 0; i < permissions.length; i++) {
                if (context.checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    isNeedPermission = true;
                    break;
                }
            }
            if (isNeedPermission) {
                context.requestPermissions(permissions, requestCode);
            } else {
                if (permissionCallBack != null) {
                    permissionCallBack.onPermissionReady();
                }
            }
        } else {
            if (permissionCallBack != null) {
                permissionCallBack.onPermissionReady();
            }
        }
    }


    //Show dialog go to setting Permission
    public static void showDialogTurnOnPermission(String message, final Activity context) {
        DialogUtil.showMessageOKCancel(context, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startInstalledAppDetailsActivity(context);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.finish();
            }
        });
    }

    //Intent go to setting permission
    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
        context.finish();
    }

}
