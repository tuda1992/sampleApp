package findlocation.bateam.com.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import findlocation.bateam.com.R;
import findlocation.bateam.com.model.UniversityModel;

/**
 * Created by doanhtu on 2/6/18.
 */

public class AutoCompleteUniversityAdapter extends ArrayAdapter<UniversityModel> {

    private final List<UniversityModel> mListUniversities;
    public List<UniversityModel> mFilterUniver = new ArrayList<>();

    public AutoCompleteUniversityAdapter(Context context, List<UniversityModel> universities) {
        super(context, 0, universities);
        this.mListUniversities = universities;
    }

    @Override
    public int getCount() {
        return mFilterUniver.size();
    }

    @Override
    public Filter getFilter() {
        return new UniversityFilter(this, mListUniversities);
    }

    @Nullable
    @Override
    public UniversityModel getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item from filtered list.
        UniversityModel universityModel = mFilterUniver.get(position);

        // Inflate your custom row layout as usual.
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.item_spinner, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.tv_item);
        tvName.setText(universityModel.universityName);

        return convertView;
    }

}
