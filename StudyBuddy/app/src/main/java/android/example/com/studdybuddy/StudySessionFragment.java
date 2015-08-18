package android.example.com.studdybuddy;

/**
 * Created by John on 7/18/15.
 */

import android.content.Context;
import android.content.Intent;
import android.example.com.studdybuddy.data.SessionDbHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StudySessionFragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private boolean islargeView = false;
    //TODO: Make this adapter array take in a custom "Study Session" object

    private StudySessionAdapter mStudySessionAdapter;

    private ArrayList<StudySession> mStudySessions = new ArrayList<StudySession>(); //Hold teh objects themselves

    public StudySessionFragment() {

    }

    @Override
    public void onResume() {

        super.onResume();
        mStudySessions.clear();
        pullLocalData();
        mStudySessionAdapter.notifyDataSetChanged();

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            FetchDataTask dataTask = new FetchDataTask();

            dataTask.execute();
        }
        mStudySessionAdapter.notifyDataSetChanged();
        String strI = Integer.toString(SessionDbHelper.getInstance(getActivity()).countPIDS());

            Toast toast = Toast.makeText(getActivity(), strI , Toast.LENGTH_SHORT);
            toast.show();

    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        mStudySessionAdapter = new StudySessionAdapter(getActivity(), mStudySessions);






        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Temp action to add data
        FloatingActionButton mButton = (FloatingActionButton) rootView.findViewById(R.id.fab);



        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateSessionActivity.class);
                startActivity(intent);

            }
        });


        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listView_studdysession);
        listView.setAdapter(mStudySessionAdapter);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (!islargeView) {
                        Intent intent = new Intent(getActivity(), StudySessionDetailActivity.class);
                        Bundle bundle = new Bundle(); //Use a bundle to sotre multiple objects with a key/value pair
                        bundle.putString("sessionName", mStudySessionAdapter.getItem(position).getSessionName());
                        bundle.putString("sessionDescription", mStudySessionAdapter.getItem(position).getSessionDescription());
                        bundle.putString("locationName", mStudySessionAdapter.getItem(position).getLocationName());
                        bundle.putString("subjectType", mStudySessionAdapter.getItem(position).getSubjectType());
                        bundle.putString("timeToMeet", mStudySessionAdapter.getItem(position).getTimeToMeet());
                        bundle.putString("createdAt", mStudySessionAdapter.getItem(position).getCreateTime());
                        bundle.putString("pid", mStudySessionAdapter.getItem(position).getPid());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                }


            });


        mStudySessionAdapter.setUseLarge(islargeView);

        return rootView;
    }

    public void pullLocalData(){

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("StudySession");
        parseQuery.fromLocalDatastore();
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject object : list) {


                        String sessionName = object.getString("sessionName");
                        String sessionDesc = object.getString("sessionDesc");
                        String locationName = object.getString("locationName");
                        String subjectType = object.getString("subjectType");
                        String timeToMeet = object.getString("timeToMeet");

                        Date date = object.getCreatedAt();
                        DateFormat df = new SimpleDateFormat("HH:mm a");
                        String createTime = df.format(date);
                        mStudySessionAdapter.add(new StudySession(sessionName, sessionDesc,
                                locationName, subjectType, timeToMeet, createTime, object.getObjectId()));
                        mStudySessionAdapter.notifyDataSetChanged();

                    }
                } else {
                    Log.e("StuddyBuddy", e.toString());
                }
            }
        });
    }

    private class FetchDataTask extends AsyncTask<Void, Void, Void> {

        /*
        We chose to use a Async Task instead of something like a syncadapter becuase we would rather get the items on demand rather than
        continually pulling the new data.

         */
        @Override
        protected Void doInBackground(Void... params) {


            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("StudySession");
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null) {
                        mStudySessions.clear();
                        for (ParseObject object : list) {

                            object.pinInBackground();
                            String sessionName = object.getString("sessionName");
                            String sessionDesc = object.getString("sessionDesc");
                            String locationName = object.getString("locationName");
                            String subjectType = object.getString("subjectType");
                            String timeToMeet = object.getString("timeToMeet");

                            Date date = object.getCreatedAt();
                            DateFormat df = new SimpleDateFormat("HH:mm a");
                            String createTime = df.format(date);

                            mStudySessionAdapter.add(new StudySession(sessionName, sessionDesc,
                                    locationName, subjectType, timeToMeet, createTime, object.getObjectId()));


                        }
                    } else {
                        Log.e("StuddyBuddy", e.toString());

                    }
                }
            });


            return null;
        }

    }

    public void setLargeView(boolean status){

        islargeView = status;
        if (mStudySessionAdapter != null) {
            mStudySessionAdapter.setUseLarge(islargeView);
        }

    }

}
