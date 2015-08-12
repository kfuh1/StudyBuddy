package android.example.com.studdybuddy;

/**
 * Created by John on 7/18/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.example.com.studdybuddy.data.SessionContract;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class StudySessionFragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //TODO: Make this adapter array take in a custom "Study Session" object

    public StudySessionAdapter mStudySessionAdapter;

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
    public void onResume() {

        super.onResume();
        mStudySessionAdapter.notifyDataSetChanged();

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        FetchDataTask weatherTask = new FetchDataTask();
//
//        weatherTask.execute();
//        mStudySessionAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mStudySessionAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mStudySessionAdapter = new StudySessionAdapter(getActivity(), mStudySessions);

         //while(!getSessions()); //Try and pull from the datastore and update the listView

        FetchDataTask weatherTask = new FetchDataTask();

        weatherTask.execute();
        mStudySessionAdapter.notifyDataSetChanged();


    //Copy the Parse Database to a local one for backup.
        Vector<ContentValues> cVVector = new Vector<ContentValues>(mStudySessionAdapter.getCount());
        for (int i = 0; i < mStudySessionAdapter.getCount(); i++){
            ContentValues sessionValues = new ContentValues();

            StudySession pulledInfo = mStudySessionAdapter.getItem(i);
            sessionValues.put(SessionContract.SessionEntry.SESSIONNAME, pulledInfo.getSessionName());
            sessionValues.put(SessionContract.SessionEntry.SESSIONDESC, pulledInfo.getSessionDescription());


            cVVector.add(sessionValues);
        }

        if ( cVVector.size() > 0 ) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            getActivity().getContentResolver().bulkInsert(SessionContract.SessionEntry.CONTENT_URI, cvArray);

        }


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
                bundle.putString("sessionLocation", mStudySessionAdapter.getItem(position).getLocationName());
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

        //TODO: Set up proper floatingactionbutton to launch a "create" view to make a session



        return rootView;
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



   // @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//
//        Uri weatherForLocationUri = SessionContract.SessionEntry.buildSessionUri();
//
//        return new CursorLoader(getActivity(),
//                weatherForLocationUri,
//                FORECAST_COLUMNS,
//                null,
//                null,
//                sortOrder);
//    }

//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//
//    }

    private class FetchDataTask extends AsyncTask<Void, Void, Void> {

        /*
        We chose to use a Async Task instead of something like a syncadapter becuase we would rather get the items on demand rather than
        continually pulling the new data.

         */
        @Override
        protected Void doInBackground(Void... params) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                //ONLINE
//                Toast toast = Toast.makeText(getActivity(), "CONNECTION!", Toast.LENGTH_SHORT);
//                toast.show();
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("StudySession");
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            for (ParseObject object : list) {
                                object.pinInBackground();
                                String sessionName = object.getString("sessionName");
                                String sessionDesc = object.getString("sessionDesc");
                                String locationName = object.getString("locationName");
                                String subjectType = object.getString("subjectType");
                                String timeToMeet = object.getString("timeToMeet");
                                mStudySessionAdapter.add(new StudySession(sessionName, sessionDesc,
                                        locationName, subjectType, timeToMeet));
                                //mStudySessionAdapter.notifyDataSetChanged();

                            }
                        } else {
                            Log.e("StuddyBuddy", e.toString());
                            //return null;
                        }
                    }
                });
            }
            //If there is no internet connection, pull from local datastore
            else {

//                Toast toast = Toast.makeText(getActivity(), "NO CONNECTION!", Toast.LENGTH_SHORT);
//                toast.show();
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("StudySession");
                parseQuery.fromLocalDatastore();
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            for (ParseObject object : list) {
                                //object.pinInBackground();
                                String sessionName = object.getString("sessionName");
                                String sessionDesc = object.getString("sessionDesc");
                                String locationName = object.getString("locationName");
                                String subjectType = object.getString("subjectType");
                                String timeToMeet = object.getString("timeToMeet");
                                mStudySessionAdapter.add(new StudySession(sessionName, sessionDesc,
                                        locationName, subjectType, timeToMeet));

                                //mStudySessionAdapter.notifyDataSetChanged();

                            }
                        } else {
                            Log.e("StuddyBuddy", e.toString());
                            //return null;
                        }
                    }
                });
            }
            return null;
        }

    }

}
