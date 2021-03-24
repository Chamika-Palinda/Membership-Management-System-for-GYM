package gym_manger_interfaces;

import javafx.scene.input.DataFormat;

public class DefaultClass {
    public String FirstName;
    private String LastName;
    private String MembershipType;
    public  String Date;

    public DefaultClass(){
        this.FirstName="";
        this.LastName="";
        this.MembershipType="";
    }

    public DefaultClass(String FirstName, String LastName){
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.MembershipType=MembershipType;
    }


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getMembershipType() {
        return MembershipType;
    }

    public void setMembershipType(String MembershipType) {
        this.MembershipType = MembershipType;
    }

    public String getDate(){ return Date;}

    public void setDate(String Date){this.Date=Date;}


}
