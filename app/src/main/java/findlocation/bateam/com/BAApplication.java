package findlocation.bateam.com;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import findlocation.bateam.com.util.TypefaceUtil;

/**
 * Created by acv on 12/7/17.
 */

public class BAApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
        printHashKey(getApplicationContext());
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/segoeui.ttf");
    }

    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("BAApplication", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("BAApplication", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("BAApplication", "printHashKey()", e);
        }
    }

}
