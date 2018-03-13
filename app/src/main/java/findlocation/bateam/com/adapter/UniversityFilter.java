package findlocation.bateam.com.adapter;

import android.util.Log;
import android.widget.Filter;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import findlocation.bateam.com.model.UniversityModel;

/**
 * Created by doanhtu on 2/6/18.
 */

public class UniversityFilter extends Filter {

    AutoCompleteUniversityAdapter mAdapter;
    List<UniversityModel> originalList;
    List<UniversityModel> filteredList;

    public UniversityFilter(AutoCompleteUniversityAdapter adapter, List<UniversityModel> originalList) {
        super();
        this.mAdapter = adapter;
        this.originalList = originalList;
        this.filteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredList.clear();
        final FilterResults results = new FilterResults();

        if (constraint == null || constraint.length() == 0) {
            filteredList.addAll(originalList);
        } else {
            final String filterPattern = constraint.toString().toLowerCase().trim();

            // Your filtering logic goes in here
            for (final UniversityModel dog : originalList) {
                if (deAccent(dog.universityName.toLowerCase()).contains((deAccent(filterPattern)).toLowerCase())) {
                    filteredList.add(dog);
                }
            }
        }
        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    private String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        mAdapter.mFilterUniver.clear();
        mAdapter.mFilterUniver.addAll((List) results.values);
        mAdapter.notifyDataSetChanged();
    }
}
