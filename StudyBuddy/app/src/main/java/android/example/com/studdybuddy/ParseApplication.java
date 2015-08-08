package android.example.com.studdybuddy;

import android.app.Application;
import android.util.Log;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

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
        Parse.initialize(this, “KEY”, “KEY”);

        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d("StuddyBuddy", "Anonymous login failed.");
                } else {
                    Log.d("StuddyBuddy", "Anonymous user logged in.");
                }
            }
        });
    }
}

