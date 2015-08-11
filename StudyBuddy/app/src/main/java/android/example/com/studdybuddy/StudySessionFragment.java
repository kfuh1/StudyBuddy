package android.example.com.studdybuddy;

/**
 * Created by John on 7/18/15.
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class StudySessionFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //TODO: Make this adapter array take in a custom "Study Session" object

    private StudySessionAdapter mStudySessionAdapter;

    public ArrayList<StudySession> mStudySessions = new ArrayList<StudySession>(); //Hold teh objects themselves

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StudySessionFragment newInstance(int sectionNumber) {
        StudySessionFragment fragment = new StudySessionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public StudySessionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mStudySessionAdapter = new StudySessionAdapter(getActivity(), mStudySessions);

         while(!getSessions()); //Try and pull from the datastore and update the listView
        mStudySessionAdapter.notifyDataSetChanged();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Temp action to add data
        FloatingActionButton mButton = (FloatingActionButton) rootView.findViewById(R.id.fab);



        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateSessionActivity.class);
                startActivity(intent);
                //newSession();
            }
        });


        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listView_studdysession);
        listView.setAdapter(mStudySessionAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), StudySessionDetailActivity.class);
                Bundle bundle = new Bundle(); //Use a bundle to sotre multiple objects with a key/value pair
                bundle.putString("sessionName", mStudySessionAdapter.getItem(position).getSessionName());
                bundle.putString("sessionDescription", mStudySessionAdapter.getItem(position).getSessionDescription());
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

        //TODO: Set up proper floatingactionbutton to launch a "create" view to make a session

        return rootView;
    }

    public Boolean getSessions() {

        //TODO PARSE: Filter which sessions to save by getting the sessions within X number of yards.
        //TODO Can do in app, but might be expensive or consuming too much data. Maybe use Parse CloudCode w/ JS?

         //Determine if we have a network connection
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            //ONLINE
            Toast toast = Toast.makeText(getActivity(),"CONNECTION!", Toast.LENGTH_SHORT);
            toast.show();
            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("TestObject");
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null) {
                        for (ParseObject object : list) {
                            object.pinInBackground();
                            String test1 = object.getString("foo");
                            mStudySessionAdapter.add(new StudySession(test1, test1));

                        }
                    } else {
                        Log.e("StuddyBuddy", e.toString());
                    }
                }
            });
        }
        //If there is no internet connection, pull from local datastore
        else{

            Toast toast = Toast.makeText(getActivity(),"NO CONNECTION!", Toast.LENGTH_SHORT);
            toast.show();
            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("TestObject");
            parseQuery.fromLocalDatastore();
            parseQuery.findInBackground(new FindCallback<ParseObject>(){
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null) {
                        for (ParseObject object : list) {
                            object.pinInBackground();
                            String test1 = object.getString("foo");
                            mStudySessionAdapter.add(new StudySession(test1, test1));

                        }
                    } else {
                        Log.e("StuddyBuddy", e.toString());
                    }
                }
            });
        }

    mStudySessionAdapter.notifyDataSetChanged();
    return true;


}



    //Make a fake session object upon pressing the FAB
    //TODO this is basically a template for how to store to Parse. Its a key-value pair
    /*
    "sessionName"
    "sessionDesc"
    "locationName"
    "subjectType"
    "user"

    */

    public void newSession(){
        ParseUser user = ParseUser.getCurrentUser();
        mStudySessionAdapter.add(new StudySession("Test", "Test"));
        mStudySessionAdapter.notifyDataSetChanged();
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.put("user",user);

        testObject.saveInBackground();

    }

}
