package android.example.com.studdybuddy;

/**
 * Created by John on 7/26/15.
 */
public class StudySession {

    //Temporary Private Variables/Constructor
    private String sessionName;
    private String sessionDescription;
    private String locationName;
    private String subjectType;
    private String timeToMeet;
    //TODO: Add things like Parse ID, Lat/Long for location, Location Name, Class Type, ETC

    public StudySession(String sName, String sDesc, String sLocName, String sSubject,
                        String sTimeToMeet){

        this.sessionName = sName;
        this.sessionDescription = sDesc;
        this.locationName = sLocName;
        this.subjectType = sSubject;
        this.timeToMeet = sTimeToMeet;
    }

    //Thank you based JetBrains and getter/setter auto generation

    public String getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(String sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
    public String getLocationName() {
        return locationName;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public String getTimeToMeet() {
        return timeToMeet;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public void setTimeToMeet(String timeToMeet) {
        this.timeToMeet = timeToMeet;
    }
}
