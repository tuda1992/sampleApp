package findlocation.bateam.com.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import findlocation.bateam.com.R;
import findlocation.bateam.com.model.Cities;
import findlocation.bateam.com.model.NationModel;

/**
 * Created by doanhtu on 2/6/18.
 */

public class NationSpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<NationModel> contentArray;

    private boolean mIsLeft;

    public NationSpinnerAdapter(Context context, List<NationModel> contentArray, boolean isLeft) {
        this.context = context;
        this.contentArray = contentArray;
        this.mIsLeft = isLeft;
    }

    @Override
    public int getCount() {
        return contentArray.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = (LayoutInflater.from(context)).inflate(R.layout.item_spinner, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_item);
        textView.setText(contentArray.get(i).nation);
        if (mIsLeft) {
            textView.setGravity(Gravity.LEFT);
        } else {
            textView.setGravity(Gravity.RIGHT);
        }
        return view;
    }
}

