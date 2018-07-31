package findlocation.bateam.com.api;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.JsonArrayCallBackListener;
import findlocation.bateam.com.listener.JsonObjectCallBackListener;
import findlocation.bateam.com.listener.StringCallBackListener;
import findlocation.bateam.com.model.PlaceModel;
import findlocation.bateam.com.model.UserInfo;
import findlocation.bateam.com.model.UserRegister;
import findlocation.bateam.com.util.DialogUtil;
import findlocation.bateam.com.util.NetworkUtil;
import findlocation.bateam.com.util.ProgressDialogUtils;

/**
 * Created by doanhtu on 1/29/18.
 */

public class FastNetworking {

    //    public static final String BASE_URL = "http://sividns.southeastasia.cloudapp.azure.com/api";
    public static final String BASE_URL = "http://sividns.southeastasia.cloudapp.azure.com/SiviTest/api";

    public static final String URL_LOGIN = "/Users/Login";
    public static final String URL_ACTIVE_USER = "/Users/ActiveUser";
    public static final String URL_REGISTER = "/Users/Register";
    public static final String URL_UPDATE = "/Users/Update";
    public static final String URL_CITY = "/Cities/List";
    public static final String URL_DISTRICTS = "/Districts/ListByCityId";
    public static final String URL_HOUSE = "/HousingInfos/ListByDistance";
    public static final String URL_JOB = "/JobInfos/Filter";
    public static final String URL_WARD = "/Wards/ListByDistrictId";
    public static final String URL_FILTER = "/JobInfos/ListJobInfoFilters";
    public static final String URL_FORGOT_PW = "/Users/ResetPassword";
    public static final String URL_UPDATE_PW = "/Users/UpdatePassword";
    public static final String URL_EXTERNAL_REGISTER = "/Users/ExternalRegister";
    public static final String URL_EXTERNAL_LOGIN = "/Users/ExternalLogin";
    public static final String URL_RESEND = "/Users/SendActivationCode";

    private Context mContext;
    private JsonObjectCallBackListener mListenerObject;
    private JsonArrayCallBackListener mListenerArray;
    private StringCallBackListener mListenerString;
    private ProgressDialogUtils mProgress;

    public FastNetworking(Context context, JsonObjectCallBackListener listenerObject) {
        this.mContext = context;
        this.mListenerObject = listenerObject;
        mProgress = new ProgressDialogUtils(context);
    }

    public FastNetworking(Context context, JsonArrayCallBackListener listenerArray) {
        this.mContext = context;
        this.mListenerArray = listenerArray;
        mProgress = new ProgressDialogUtils(context);
    }

    public FastNetworking(Context context, StringCallBackListener listenerString) {
        this.mContext = context;
        this.mListenerString = listenerString;
        mProgress = new ProgressDialogUtils(context);
    }

    public void callApiLogin(JSONObject jsonObject) {

        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        mProgress.showDialog();

        try {
            jsonObject.put("VersionApp", mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        HashMap<String, String> headers = initCustomHeader("");
        AndroidNetworking.post(BASE_URL + URL_LOGIN)
                .addHeaders(headers)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mProgress.hideDialog();
                        if (mListenerObject != null)
                            mListenerObject.onResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerObject != null)
                            mListenerArray.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }

    public void callApiLoginFbGmail(JSONObject jsonObject) {

        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        mProgress.showDialog();

        try {
            jsonObject.put("VersionApp", mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        HashMap<String, String> headers = initCustomHeader("");
        AndroidNetworking.post(BASE_URL + URL_EXTERNAL_LOGIN)
                .addHeaders(headers)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mProgress.hideDialog();
                        if (mListenerObject != null)
                            mListenerObject.onResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerObject != null)
                            mListenerArray.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }

    public void callApiResend(String email) {
        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        mProgress.showDialog();

        HashMap<String, String> headers = initCustomHeader("");
        AndroidNetworking.get(BASE_URL + URL_RESEND)
                .addHeaders(headers)
                .addQueryParameter("email", email)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mProgress.hideDialog();
                        if (mListenerObject != null)
                            mListenerObject.onResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerObject != null)
                            mListenerArray.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }

    public void callApiHouseInfo(JSONObject jsonObject, String token, boolean isShowLoading) {
        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        mProgress.showDialog();

        HashMap<String, String> headers = initCustomContentType();
        AndroidNetworking.post(BASE_URL + URL_HOUSE)
                .addHeaders(headers)
                .addStringBody("application/json")
                .addQueryParameter(Constants.SECURITY_TOKEN, token)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        mProgress.hideDialog();
                        if (mListenerObject != null) {
                            mListenerObject.onResponse(jsonObject);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerObject != null)
                            mListenerArray.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }


    public void callApiActive(JSONObject jsonObject) {
        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }

        mProgress.showDialog();

        HashMap<String, String> headers = initCustomHeader("");
        AndroidNetworking.post(BASE_URL + URL_ACTIVE_USER)
                .addHeaders(headers)
                .addStringBody("application/json")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String s) {
                        mProgress.hideDialog();
                        if (mListenerString != null)
                            mListenerString.onResponse(s);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerString != null)
                            mListenerString.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }

    public void callApiResetPw(String email) {
        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }

        mProgress.showDialog();

        HashMap<String, String> headers = initCustomHeader("");
        AndroidNetworking.post(BASE_URL + URL_FORGOT_PW)
                .addHeaders(headers)
                .addStringBody("application/json")
                .addQueryParameter("email", email)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String s) {
                        mProgress.hideDialog();
                        if (mListenerString != null)
                            mListenerString.onResponse(s);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerString != null)
                            mListenerString.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }

    public void callApiUpdatePw(JSONObject jsonObject) {
        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }

        mProgress.showDialog();

        HashMap<String, String> headers = initCustomHeader("");
        AndroidNetworking.post(BASE_URL + URL_UPDATE_PW)
                .addHeaders(headers)
                .addStringBody("application/json")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String s) {
                        mProgress.hideDialog();
                        if (mListenerString != null)
                            mListenerString.onResponse(s);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerString != null)
                            mListenerString.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }


    public void callApiJobInfo(JSONObject jsonObject, String token, boolean isShowLoading) {
        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        if (isShowLoading) {
            mProgress.showDialog();
        }
        HashMap<String, String> headers = initCustomContentType();
        AndroidNetworking.post(BASE_URL + URL_JOB)
                .addHeaders(headers)
                .addStringBody("application/json")
                .addQueryParameter(Constants.SECURITY_TOKEN, token)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mProgress.hideDialog();
                        if (mListenerArray != null)
                            mListenerArray.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerArray != null)
                            mListenerArray.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }

    public void callApiGetCities() {
        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        AndroidNetworking.get(BASE_URL + URL_CITY)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (mListenerArray != null)
                            mListenerArray.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerArray != null)
                            mListenerArray.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }

    public void callApiGetJobFilter(String token) {
        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        mProgress.showDialog();
        AndroidNetworking.get(BASE_URL + URL_FILTER)
                .addQueryParameter(Constants.SECURITY_TOKEN, token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        mProgress.hideDialog();
                        if (mListenerObject != null)
                            mListenerObject.onResponse(jsonObject);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerObject != null)
                            mListenerObject.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }

    public void callApiGetDistricts(String cityId) {
        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        AndroidNetworking.get(BASE_URL + URL_DISTRICTS)
                .addQueryParameter(Constants.CITY_ID, cityId)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (mListenerArray != null)
                            mListenerArray.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerArray != null)
                            mListenerArray.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }

    public void callApiGetWards(String districtId) {
        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        AndroidNetworking.get(BASE_URL + URL_WARD)
                .addQueryParameter(Constants.DISTRICT_ID, districtId)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (mListenerArray != null)
                            mListenerArray.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerArray != null)
                            mListenerArray.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }

    public void callApiRegister(Map<String, File> mapFile, UserRegister userRegister) {

        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        mProgress.showDialog();

        HashMap<String, String> headers = initCustomContentType();
        AndroidNetworking.upload(BASE_URL + URL_REGISTER)
                .addHeaders(headers)
//                .addMultipartFile(mapFile)
                .addMultipartParameter(userRegister)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        mProgress.hideDialog();
                        if (mListenerObject != null)
                            mListenerObject.onResponse(jsonObject);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerObject != null)
                            mListenerObject.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });

//                .getAsString(new StringRequestListener() {
//
//                    @Override
//                    public void onResponse(String s) {
//                        mProgress.hideDialog();
//                        if (mListenerString != null)
//                            mListenerString.onResponse(s);
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        mProgress.hideDialog();
//                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
//                        if (mListenerString != null)
//                            mListenerString.onError("Đường truyền của bạn đang gặp vấn đề !!!");
//                    }
//                });
    }

    public void callApiRegisterFbGmail(UserRegister userRegister) {

        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        mProgress.showDialog();

        HashMap<String, String> headers = initCustomContentType();
        AndroidNetworking.upload(BASE_URL + URL_EXTERNAL_REGISTER)
                .addHeaders(headers)
                .addMultipartParameter(userRegister)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        mProgress.hideDialog();
                        if (mListenerObject != null)
                            mListenerObject.onResponse(jsonObject);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerObject != null)
                            mListenerObject.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });
    }


    public void callApiUpdate(Map<String, File> mapFile, UserInfo userInfo) {
        if (!NetworkUtil.isHaveInternet(mContext)) {
            DialogUtil.showDialogErrorInternet(mContext, null);
            return;
        }
        mProgress.showDialog();

        HashMap<String, String> headers = initCustomContentType();

        AndroidNetworking.upload(BASE_URL + URL_UPDATE)
                .addHeaders(headers)
                .addQueryParameter(Constants.SECURITY_TOKEN, userInfo.securityToken)
                .addMultipartFile(mapFile)
                .addMultipartParameter(userInfo)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String s) {
                        mProgress.hideDialog();
                        if (mListenerString != null)
                            mListenerString.onResponse(s);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgress.hideDialog();
                        DialogUtil.showDialogErrorLowInternet(mContext, "Đường truyền của bạn đang gặp vấn đề !!!", null);
                        if (mListenerString != null)
                            mListenerString.onError("Đường truyền của bạn đang gặp vấn đề !!!");
                    }
                });

    }

    @NonNull
    private HashMap<String, String> initCustomHeader(String token) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }

    @NonNull
    private HashMap<String, String> initCustomContentType() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        return headers;
    }

}
