package findlocation.bateam.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by doanhtu on 1/12/18.
 */

public class JobModel implements Parcelable {

    @SerializedName("Id")
    @Expose
    public String id;
    @SerializedName("JobTitle")
    @Expose
    public String jobTitle;
    @SerializedName("JobLevel")
    @Expose
    public String jobLevel;
    @SerializedName("JobDescription")
    @Expose
    public String jobDescription;
    @SerializedName("Industry")
    @Expose
    public String industry;
    @SerializedName("CompanyName")
    @Expose
    public String companyName;
    @SerializedName("CompanyAddress")
    @Expose
    public String companyAddress;
    @SerializedName("ContactNumber")
    @Expose
    public String contactNumber;
    @SerializedName("ContactEmail")
    @Expose
    public String contactEmail;
    @SerializedName("ContactPerson")
    @Expose
    public String contactPerson;
    @SerializedName("WorkingArea")
    @Expose
    public String workingArea;
    @SerializedName("Salary")
    @Expose
    public String salary;
    @SerializedName("Benefits")
    @Expose
    public String benefits;
    @SerializedName("AgeRequirement")
    @Expose
    public String ageRequirement;
    @SerializedName("GenderRequirement")
    @Expose
    public String genderRequirement;
    @SerializedName("ExperienceRequirement")
    @Expose
    public String experienceRequirement;
    @SerializedName("EducationRequirement")
    @Expose
    public String educationRequirement;
    @SerializedName("TrialPeriod")
    @Expose
    public String trialPeriod;
    @SerializedName("CreateDate")
    @Expose
    public String createDate;
    @SerializedName("ExipryDate")
    @Expose
    public String exipryDate;
    @SerializedName("ApplicaitonDeadline")
    @Expose
    public String applicaitonDeadline;
    @SerializedName("JobSource")
    @Expose
    public String jobSource;
    @SerializedName("JobLink")
    @Expose
    public String jobLink;
    @SerializedName("JobType")
    @Expose
    public String jobType;

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
        parcel.writeString(id);
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
        id = in.readString();
        jobDescription = in.readString();
        jobLevel = in.readString();
        jobLink = in.readString();
        jobSource = in.readString();
        jobTitle = in.readString();
        salary = in.readString();
        trialPeriod = in.readString();
        workingArea = in.readString();
    }

    public JobModel(){

    }

}
