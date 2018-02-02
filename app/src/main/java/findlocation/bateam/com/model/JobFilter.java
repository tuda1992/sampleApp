package findlocation.bateam.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by doanhtu on 2/2/18.
 */

public class JobFilter {

    @SerializedName("WorkingAreas")
    @Expose
    public List<String> workingAreas;
    @SerializedName("JobTypes")
    @Expose
    public List<String> jobTypes;
    @SerializedName("Industries")
    @Expose
    public List<String> industries;

}
