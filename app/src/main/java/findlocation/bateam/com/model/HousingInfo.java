package findlocation.bateam.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by doanhtu on 2/2/18.
 */

public class HousingInfo {

    @SerializedName("Data")
    public List<PlaceModel> data;
    @SerializedName("Pagination")
    public Pagination pagination;

}
