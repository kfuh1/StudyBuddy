package android.example.com.studdybuddy;

/**
 * Created by John on 7/26/15.
 */
public class StudySession {

    //Temporary Private Variables/Constructor
    private String sessionName;

    private String sessionDescription;
    //TODO: Add things like Parse ID, Lat/Long for location, Location Name, Class Type, ETC

    public StudySession(String sName, String sDesc){

        this.sessionName = sName;
        this.sessionDescription = sDesc;
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
}
