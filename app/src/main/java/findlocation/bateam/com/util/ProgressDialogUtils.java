package findlocation.bateam.com.util;

import android.app.ProgressDialog;
import android.content.Context;

import findlocation.bateam.com.R;

/**
 * Created by doanhtu on 1/29/18.
 */

public class ProgressDialogUtils {

    private ProgressDialog mProgressDialog;
    private Context mContext;

    public ProgressDialogUtils(Context context){
        this.mContext = context;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(false);
    }

    public void showDialog() {
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.layout_my_progress);
    }

    public void hideDialog(){
        mProgressDialog.dismiss();
    }

}
