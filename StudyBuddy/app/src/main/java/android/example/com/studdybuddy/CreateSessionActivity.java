package android.example.com.studdybuddy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Kathleen on 8/7/2015.
 */
public class CreateSessionActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        /* set dropdown list of meeting time choice */
        Spinner timeSpinner = (Spinner) findViewById(R.id.meeting_time_spinner);
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this,
                R.array.meeting_time_array, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        /* set dropdown list of subject choice */
        Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);
        ArrayAdapter<CharSequence> subjectAdapter = ArrayAdapter.createFromResource(this,
                R.array.subject_array, android.R.layout.simple_spinner_item);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectAdapter);
    }

    /* Method that gets called when SAVE button is clicked*/
    public void saveCreateData(View view) {
        ParseObject sessionObject = new ParseObject("StudySession");
        ParseUser user = ParseUser.getCurrentUser();

        /* get all fields from view */
        EditText titleText = (EditText) findViewById(R.id.title_text);
        EditText descText = (EditText) findViewById(R.id.desc_text);
        EditText locationText = (EditText) findViewById(R.id.location_text);
        Spinner timeSpinner = (Spinner) findViewById(R.id.meeting_time_spinner);
        Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);
        String timeToMeet;
        String subjectName;
        if (timeSpinner.getSelectedItem() == null) {
            timeToMeet = "";
        } else {
            timeToMeet = timeSpinner.getSelectedItem().toString();
        }
        if (subjectSpinner.getSelectedItem() == null) {
            subjectName = "";
        } else {
            subjectName = subjectSpinner.getSelectedItem().toString();
        }

        /* store key value pairs into Parse database */
        sessionObject.put("sessionName", titleText.getText().toString());
        sessionObject.put("sessionDesc", descText.getText().toString());
        sessionObject.put("locationName", locationText.getText().toString());
        sessionObject.put("subjectType", subjectName);
        sessionObject.put("timeToMeet", timeToMeet);

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && user != null) {
            sessionObject.saveInBackground();
            finish();
        }
        else {
            sessionObject.saveEventually();
            finish();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
