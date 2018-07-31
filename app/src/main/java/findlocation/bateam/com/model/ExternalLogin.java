package findlocation.bateam.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalLogin {

    @SerializedName("Email")
    @Expose
    public String email;
    @SerializedName("PhoneNumber")
    @Expose
    public String phoneNumber;
}
