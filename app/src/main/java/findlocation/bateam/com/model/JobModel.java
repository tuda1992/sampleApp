package findlocation.bateam.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by doanhtu on 1/12/18.
 */

public class JobModel {

    @SerializedName("Age_Requirement")
    @Expose
    public Object ageRequirement;
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
    public Object contactEmail;
    @SerializedName("Contact_Number")
    @Expose
    public Object contactNumber;
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
    public Object jobLevel;
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
    public Object trialPeriod;
    @SerializedName("Working_Area")
    @Expose
    public String workingArea;

}
