package findlocation.bateam.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by doanhtu on 12/25/17.
 */

public class UserInfo {
    
    @SerializedName("Id")
    @Expose
    public String id;
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
    @SerializedName("SchoolName")
    @Expose
    public String schoolName;
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
    @SerializedName("Avatar")
    @Expose
    public String avatar;
    @SerializedName("StudentCard")
    @Expose
    public String studentCard;
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("SecurityToken")
    @Expose
    public String securityToken;
    @SerializedName("Sex")
    public String sex;
    @SerializedName("Address")
    public String address;
    @SerializedName("DateOfBirth")
    public String dob;

}
