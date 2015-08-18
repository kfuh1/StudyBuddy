package android.example.com.studdybuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.example.com.studdybuddy.data.SessionContract;
import android.example.com.studdybuddy.data.SessionDbHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
        final ParseObject sessionObject = new ParseObject("StudySession");
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

        if(titleText.getText().toString().equals("") || titleText.getText().toString().equals("Enter a title") || descText.getText().toString().equals("")  || descText.getText().toString().equals("Enter a description") || locationText.getText().toString().equals("") ||locationText.getText().toString().equals("Enter a location")){
            Toast toast = Toast.makeText(getApplicationContext(), "Error! Please fill out all text fields :D ", Toast.LENGTH_LONG);
            toast.show();
        }

        else if(titleText.getText().toString().length() >= 20){
            Toast toast = Toast.makeText(getApplicationContext(), "Error! Title too long.\nMax number of characters is 20", Toast.LENGTH_LONG);
            toast.show();
        }
        else if(descText.getText().toString().length() >= 45){
            Toast toast = Toast.makeText(getApplicationContext(), "Error! Description too long.\nMax number of characters is 45", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
        /* store key value pairs into Parse database */
            sessionObject.put("sessionName", titleText.getText().toString());
            sessionObject.put("sessionDesc", descText.getText().toString());
            sessionObject.put("locationName", locationText.getText().toString());
            sessionObject.put("subjectType", subjectName);
            sessionObject.put("timeToMeet", timeToMeet);



            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected() && user != null) {
                sessionObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            // Saved successfully.

                            String id = sessionObject.getObjectId();

                            SQLiteDatabase db =  SessionDbHelper.getInstance(getApplicationContext()).getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.clear();
                            values.put(SessionContract.Columns.COLUMN_PID, id);
                            Uri uri = SessionContract.CONTENT_URI;
                            getApplicationContext().getContentResolver().insert(uri, values);

//                            Toast toast = Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG);
//                            toast.show();

                        } else {
                            // The save failed.

                        }
                    }
                });


                finish();
            } else {
                sessionObject.saveEventually();
                finish();
            }
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
