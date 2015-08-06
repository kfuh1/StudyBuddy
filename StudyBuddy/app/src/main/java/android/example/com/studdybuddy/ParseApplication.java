package android.example.com.studdybuddy;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by John on 8/3/15. Class to handle the Parse  stuff
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.


        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here

        //REMEMBER TO REMOVE THE TWO KEYS WHEN YOU UPLAOD TO GITHUB
        Parse.initialize(this, "API KEY", "API KEY");
    }
}

