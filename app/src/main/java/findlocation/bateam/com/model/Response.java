package findlocation.bateam.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("Code")
    @Expose
    public String code;
    @SerializedName("Message")
    @Expose
    public String message;

}
