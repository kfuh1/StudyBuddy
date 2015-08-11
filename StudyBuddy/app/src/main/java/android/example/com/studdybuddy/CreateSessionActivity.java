package android.example.com.studdybuddy;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.AlertDialog;
/**
 * Created by Kathleen on 8/7/2015.
 */
public class CreateSessionActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);
        Spinner timeSpinner = (Spinner) findViewById(R.id.meeting_time_spinner);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meeting_time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adapter);
    }

    /* Method that gets called when SAVE button is clicked*/
    public void saveCreateData(View view){
        //TODO: save the data in the create session screen

        //debug toast
        Toast.makeText(getApplicationContext(), "saving data!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    protected void exitByBackKey(){
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to save your changes to the application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        saveCreateData(findViewById(R.id.fragment_create));
                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }
}
