package findlocation.bateam.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by doanhtu on 12/25/17.
 */

public class PlaceModel implements Parcelable {

    @SerializedName("cob_date")
    @Expose
    public String cobDate;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("created_date")
    @Expose
    public String createdDate;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("district")
    @Expose
    public String district;
    @SerializedName("address_detail")
    @Expose
    public String addressDetail;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("area")
    @Expose
    public String area;
    @SerializedName("contract_name")
    @Expose
    public String contractName;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("lattitude")
    @Expose
    public String lattitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cobDate);
        parcel.writeString(title);
        parcel.writeString(createdDate);
        parcel.writeString(city);
        parcel.writeString(district);
        parcel.writeString(price);
        parcel.writeString(area);
        parcel.writeString(contractName);
        parcel.writeString(phone);
        parcel.writeString(mobile);
        parcel.writeString(content);
        parcel.writeString(lattitude);
        parcel.writeString(longitude);
        parcel.writeString(addressDetail);
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
        city = in.readString();
        district = in.readString();
        price = in.readString();
        area = in.readString();
        contractName = in.readString();
        phone = in.readString();
        mobile = in.readString();
        content = in.readString();
        lattitude = in.readString();
        longitude = in.readString();
        addressDetail = in.readString();
    }

}
