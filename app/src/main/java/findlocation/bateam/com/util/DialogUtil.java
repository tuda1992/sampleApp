package findlocation.bateam.com.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import findlocation.bateam.com.R;

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
                .create()
                .show();
    }

    public static void showDialogError(Context context, String message, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_error))
                .setMessage(message)
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .create()
                .show();
    }

    public static void showDialogErrorInternet(Context context, DialogInterface.OnClickListener okListener) {
        if (context == null) return;
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.title_dialog_error_internet))
                .setMessage(context.getResources().getText(R.string.error_dialog_internet))
                .setPositiveButton(context.getResources().getText(R.string.text_button_ok), okListener)
                .create()
                .show();
    }

}
