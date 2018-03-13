package findlocation.bateam.com.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import findlocation.bateam.com.model.UserInfo;
import findlocation.bateam.com.model.UserLogin;

import static findlocation.bateam.com.constant.Constants.SAVE_DATA;
import static findlocation.bateam.com.constant.Constants.USER_INFO;
import static findlocation.bateam.com.constant.Constants.USER_LOGIN;

/**
 * Created by doanhtu on 12/25/17.
 */

public class PrefUtil {

    private final static String PREF_FILE = "PREF";

    /**
     * Set a string shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void setSharedPreferenceString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Set a integer shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void setSharedPreferenceInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Set a Boolean shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void setSharedPreferenceBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * Get a string shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static String getSharedPreferenceString(Context context, String key, String defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getString(key, defValue);
    }

    /**
     * Get a integer shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static int getSharedPreferenceInt(Context context, String key, int defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getInt(key, defValue);
    }

    /**
     * Get a boolean shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static boolean getSharedPreferenceBoolean(Context context, String key, boolean defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getBoolean(key, defValue);
    }

    public static void setSharedPreferenceUserInfo(Context context, String json) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_INFO, json);
        editor.commit();
    }


    public static void setSharedPreferenceUserLogin(Context context, String json) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_LOGIN, json);
        editor.commit();
    }

    public static UserLogin getSharedPreferenceUserLogin(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        String userInfo = settings.getString(USER_LOGIN, "");
        if (TextUtils.isEmpty(userInfo)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(userInfo, UserLogin.class);
        }
    }


    public static UserInfo getSharedPreferenceUserInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        String userInfo = settings.getString(USER_INFO, "");
        if (TextUtils.isEmpty(userInfo)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(userInfo, UserInfo.class);
        }
    }

    public static void setSharedPreferenceSaveData(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(SAVE_DATA, true);
        editor.commit();
    }

    public static boolean getSharedPreferenceSaveData(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getBoolean(SAVE_DATA, false);
    }

    public static void clearSharedPreference(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        settings.edit().remove(USER_INFO).apply();
        settings.edit().remove(SAVE_DATA).apply();
    }
}
