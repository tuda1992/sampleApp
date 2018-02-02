package findlocation.bateam.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by doanhtu on 12/25/17.
 */

public class PlaceModel implements Parcelable {

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("FullAddress")
    @Expose
    public String fullAddress;
    @SerializedName("Title")
    @Expose
    public String title;
    @SerializedName("Content")
    @Expose
    public String content;
    @SerializedName("ContactPerson")
    @Expose
    public String contractName;
    @SerializedName("ContactMobile")
    @Expose
    public String mobile;
    @SerializedName("ContactPhone")
    @Expose
    public String phone;
    @SerializedName("Distance")
    @Expose
    public String distance;
    @SerializedName("Price")
    @Expose
    public String price;
    @SerializedName("TotalRows")
    @Expose
    public Integer totalRows;
    @SerializedName("Province")
    @Expose
    public String province;
    @SerializedName("District")
    @Expose
    public String district;
    @SerializedName("Street")
    @Expose
    public String street;
    @SerializedName("Long")
    @Expose
    public String longitude;
    @SerializedName("Lat")
    @Expose
    public String lattitude;
    @SerializedName("Size")
    @Expose
    public Integer size;
    @SerializedName("PostDate")
    @Expose
    public String createdDate;
    @SerializedName("ExpiryDate")
    @Expose
    public String cobDate;
    @SerializedName("IsShowDistance")
    @Expose
    public boolean isShowDistance = false;
    @SerializedName("PageIndex")
    public Integer pageIndex;
    @SerializedName("PageSize")
    public Integer pageSize;
    @SerializedName("Longitude")
    public String longResult;
    @SerializedName("Lattitude")
    public String latResult;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cobDate);
        parcel.writeString(title);
        parcel.writeString(createdDate);
        parcel.writeString(province);
        parcel.writeString(district);
        parcel.writeString(price);
        parcel.writeString(street);
        parcel.writeString(contractName);
        parcel.writeString(phone);
        parcel.writeString(mobile);
        parcel.writeString(content);
        parcel.writeString(lattitude);
        parcel.writeString(longitude);
        parcel.writeString(latResult);
        parcel.writeString(longResult);
        parcel.writeString(distance);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<PlaceModel> CREATOR = new Parcelable.Creator<PlaceModel>() {
        public PlaceModel createFromParcel(Parcel in) {
            return new PlaceModel(in);
        }

        public PlaceModel[] newArray(int size) {
            return new PlaceModel[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private PlaceModel(Parcel in) {
        cobDate = in.readString();
        title = in.readString();
        createdDate = in.readString();
        province = in.readString();
        district = in.readString();
        price = in.readString();
        street = in.readString();
        contractName = in.readString();
        phone = in.readString();
        mobile = in.readString();
        content = in.readString();
        lattitude = in.readString();
        longitude = in.readString();
        distance = in.readString();
        longResult = in.readString();
        latResult = in.readString();
    }

    public PlaceModel() {

    }
}
