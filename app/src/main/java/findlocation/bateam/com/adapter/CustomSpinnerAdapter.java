package findlocation.bateam.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import findlocation.bateam.com.R;

/**
 * Created by acv on 12/7/17.
 */

public class CustomSpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<String> contentArray;

    public CustomSpinnerAdapter(Context context, List<String> contentArray) {
        this.context = context;
        this.contentArray = contentArray;
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
        textView.setText(contentArray.get(i));
        return view;
    }
}
