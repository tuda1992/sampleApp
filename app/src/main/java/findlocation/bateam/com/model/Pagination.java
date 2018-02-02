package findlocation.bateam.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by doanhtu on 2/2/18.
 */

public class Pagination {

    @SerializedName("RowCount")
    @Expose
    public Integer rowCount;
    @SerializedName("PageCount")
    @Expose
    public Integer pageCount;
    @SerializedName("PageIndex")
    @Expose
    public Integer pageIndex;
    @SerializedName("FormId")
    @Expose
    public String formId;

}
