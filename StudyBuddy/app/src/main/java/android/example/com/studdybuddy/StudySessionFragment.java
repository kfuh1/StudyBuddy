package android.example.com.studdybuddy;

/**
 * Created by John on 7/18/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


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


        mStudySessionAdapter = new StudySessionAdapter(getActivity(),mStudySessions);


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Temp action to add data
        FloatingActionButton mButton = (FloatingActionButton) rootView.findViewById(R.id.fab);


        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newSession();
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
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

    //TODO: Set up proper floatingactionbutton to launch a "create" view to make a session

        return rootView;
    }

    //Make a fake session object upon pressing the FAB
    public void newSession(){
        Toast toast = Toast.makeText(getActivity(),"YEP!", Toast.LENGTH_SHORT);
        toast.show();
        mStudySessionAdapter.add(new StudySession("Test","Test"));

    }


}
