package findlocation.bateam.com.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import findlocation.bateam.com.R;
import findlocation.bateam.com.listener.DialogOnClick;
import findlocation.bateam.com.model.UserLogin;

/**
 * Created by acv on 12/7/17.
 */

public class DialogUtil {

    //Show message dialog
    public static void showMessageOKCancel(Context context, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setNegativeButton(context.getResources().getText(R.string.text_button_no), cancelListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogError(Context context, String message, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_error))
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogErrorJob(Context context, String message, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_error_job))
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogResetPwFail(Context context, String message, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_reset_pw_fail))
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogUpdateFail(Context context, String message, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_update_fail))
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogErrorNotActive(Context context, String message, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_error_not_active))
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogErrorActive(Context context, String message, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_error_active))
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogSuccess(Context context, String message, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_success))
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogResetPwSuccess(Context context, String message, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_reset_pw_success))
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogUpdateSuccess(Context context, String message, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_update_success))
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogErrorLowInternet(Context context, String message, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_error_internet))
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogErrorInternet(Context context, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_error_internet))
                .setMessage(context.getResources().getText(R.string.error_dialog_internet))
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialogLogin(final Activity context, final DialogOnClick okListener) {
        if (context == null) return;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_login);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        dialog.getWindow().setLayout(width, height*3/4); //Controlling width and height.

        final EditText edtEmail = (EditText) dialog.findViewById(R.id.edt_user_name);
        final EditText edtPassword = (EditText) dialog.findViewById(R.id.edt_user_password);
        final TextView tvForgetPass = (TextView)dialog.findViewById(R.id.tv_forgot_pass);

        tvForgetPass.setPaintFlags(tvForgetPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if (PrefUtil.getSharedPreferenceUserLogin(context) != null) {
            UserLogin userLogin = PrefUtil.getSharedPreferenceUserLogin(context);
            edtEmail.setText(userLogin.userName);
            edtPassword.setText(userLogin.password);
        }

        Button btnLogin = (Button) dialog.findViewById(R.id.btn_signin);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager input = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(edtEmail.getWindowToken(), 0);
                input.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);

                if (!NetworkUtil.isHaveInternet(context)) {
                    DialogUtil.showDialogErrorInternet(context, null);
                    return;
                }


                if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
                    showDialogError(context, "Bạn chưa điền thông tin email", null);
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString().trim()).matches()) {
                    showDialogError(context, "Bạn điền sai định dạng email", null);
                    return;
                }

                if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
                    showDialogError(context, "Bạn chưa điền thông tin mật khẩu", null);
                    return;
                }

                if (okListener != null) {
                    okListener.onClickLogin(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim());
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager input = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(edtEmail.getWindowToken(), 0);
                input.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);
                dialog.dismiss();
            }
        });

        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (okListener != null) {
                    InputMethodManager input = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    input.hideSoftInputFromWindow(edtEmail.getWindowToken(), 0);
                    input.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);
                    okListener.onClickForgotPass();
                    dialog.dismiss();
                }
            }
        });


        dialog.show();

    }

}
