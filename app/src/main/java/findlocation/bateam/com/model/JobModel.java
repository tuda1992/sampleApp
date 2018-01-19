package findlocation.bateam.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by doanhtu on 1/12/18.
 */

public class JobModel implements Parcelable {

    @SerializedName("Age_Requirement")
    @Expose
    public String ageRequirement;
    @SerializedName("Applicaiton_Deadline")
    @Expose
    public String applicaitonDeadline;
    @SerializedName("Benefits")
    @Expose
    public String benefits;
    @SerializedName("Company_Address")
    @Expose
    public String companyAddress;
    @SerializedName("Company_Name")
    @Expose
    public String companyName;
    @SerializedName("Contact_Email")
    @Expose
    public String contactEmail;
    @SerializedName("Contact_Number")
    @Expose
    public String contactNumber;
    @SerializedName("Contact_Person")
    @Expose
    public String contactPerson;
    @SerializedName("Create_Date")
    @Expose
    public String createDate;
    @SerializedName("Education_Requirement")
    @Expose
    public String educationRequirement;
    @SerializedName("Exipry_Date")
    @Expose
    public String exipryDate;
    @SerializedName("Experience_Requirement")
    @Expose
    public String experienceRequirement;
    @SerializedName("Gender_Requirement")
    @Expose
    public String genderRequirement;
    @SerializedName("Industry")
    @Expose
    public String industry;
    @SerializedName("JobID")
    @Expose
    public Integer jobID;
    @SerializedName("Job_Description")
    @Expose
    public String jobDescription;
    @SerializedName("Job_Level")
    @Expose
    public String jobLevel;
    @SerializedName("Job_Link")
    @Expose
    public String jobLink;
    @SerializedName("Job_Source")
    @Expose
    public String jobSource;
    @SerializedName("Job_Title")
    @Expose
    public String jobTitle;
    @SerializedName("Salary")
    @Expose
    public String salary;
    @SerializedName("Trial_Period")
    @Expose
    public String trialPeriod;
    @SerializedName("Working_Area")
    @Expose
    public String workingArea;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ageRequirement);
        parcel.writeString(applicaitonDeadline);
        parcel.writeString(benefits);
        parcel.writeString(companyAddress);
        parcel.writeString(companyName);
        parcel.writeString(contactEmail);
        parcel.writeString(contactNumber);
        parcel.writeString(contactPerson);
        parcel.writeString(createDate);
        parcel.writeString(educationRequirement);
        parcel.writeString(exipryDate);
        parcel.writeString(experienceRequirement);
        parcel.writeString(genderRequirement);
        parcel.writeString(industry);
        parcel.writeInt(jobID);
        parcel.writeString(jobDescription);
        parcel.writeString(jobLevel);
        parcel.writeString(jobLink);
        parcel.writeString(jobSource);
        parcel.writeString(jobTitle);
        parcel.writeString(salary);
        parcel.writeString(trialPeriod);
        parcel.writeString(workingArea);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<JobModel> CREATOR = new Parcelable.Creator<JobModel>() {
        public JobModel createFromParcel(Parcel in) {
            return new JobModel(in);
        }

        public JobModel[] newArray(int size) {
            return new JobModel[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private JobModel(Parcel in) {
        ageRequirement = in.readString();
        applicaitonDeadline = in.readString();
        benefits = in.readString();
        companyAddress = in.readString();
        companyName = in.readString();
        contactEmail = in.readString();
        contactNumber = in.readString();
        contactPerson = in.readString();
        createDate = in.readString();
        educationRequirement = in.readString();
        exipryDate = in.readString();
        experienceRequirement = in.readString();
        genderRequirement = in.readString();
        industry = in.readString();
        jobID = in.readInt();
        jobDescription = in.readString();
        jobLevel = in.readString();
        jobLink = in.readString();
        jobSource = in.readString();
        jobTitle = in.readString();
        salary = in.readString();
        trialPeriod = in.readString();
        workingArea = in.readString();
    }

}
