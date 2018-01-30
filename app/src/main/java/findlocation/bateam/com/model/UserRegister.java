package findlocation.bateam.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by doanhtu on 1/30/18.
 */

public class UserRegister {

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("FamilyName")
    @Expose
    public String familyName;
    @SerializedName("MiddleName")
    @Expose
    public String middleName;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Nationality")
    @Expose
    public String nationality;
    @SerializedName("CityId")
    @Expose
    public String cityId;
    @SerializedName("DistrictId")
    @Expose
    public String districtId;
    @SerializedName("WardId")
    @Expose
    public String wardId;
    @SerializedName("SchoolId")
    @Expose
    public String schoolId;
    @SerializedName("SchoolYear")
    @Expose
    public String schoolYear;
    @SerializedName("SpecializedSubject")
    @Expose
    public String specializedSubject;
    @SerializedName("Email")
    @Expose
    public String email;
    @SerializedName("Password")
    @Expose
    public String password;
    @SerializedName("PhoneNumber")
    @Expose
    public String phoneNumber;
    @SerializedName("FacebookEmail")
    @Expose
    public String facebookEmail;
    @SerializedName("FacebookUserName")
    @Expose
    public String facebookUserName;
    @SerializedName("FacebookAvatar")
    @Expose
    public String facebookAvatar;
    @SerializedName("FacebookId")
    @Expose
    public String facebookId;
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("SecurityToken")
    @Expose
    public String securityToken;

}
