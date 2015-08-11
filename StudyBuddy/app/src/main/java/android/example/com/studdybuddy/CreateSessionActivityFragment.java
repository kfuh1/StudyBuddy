package android.example.com.studdybuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.parse.ParseObject;

/**
 * Created by Kathleen on 8/7/2015.
 */
public class CreateSessionActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        View rootView = inflater.inflate(R.layout.fragment_create_session, container, false);

        /* set dropdown list of meeting time choice */
        Spinner timeSpinner = (Spinner) getActivity().findViewById(R.id.meeting_time_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.meeting_time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adapter);

        return rootView;

    }
    //TODO: implement onItemSelected when something from Spinner gets chosen
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }
    //TODO: implement onItemSelected when nothing from Spinner is chosen
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
