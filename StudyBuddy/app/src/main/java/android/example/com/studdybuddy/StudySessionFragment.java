package android.example.com.studdybuddy;

/**
 * Created by John on 7/18/15.
 */

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

        import java.util.ArrayList;


public class StudySessionFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //TODO: Make this adapter array take in a custom "Study Session" object

    private ArrayAdapter<String> mStudySessionAdapter;
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


        mStudySessionAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_studysession,
                R.id.list_item_studysession_name_textview,
                new ArrayList<String>()
        );

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listView_studdysession);
        listView.setAdapter(mStudySessionAdapter);

    //TODO: Set up proper floatingactionbutton to launch a "create" view to make a session
        mStudySessionAdapter.add("Hello World");
        mStudySessionAdapter.add("Hello World");
        mStudySessionAdapter.add("Hello World");
        mStudySessionAdapter.add("Hello World");


        return rootView;
    }
}

