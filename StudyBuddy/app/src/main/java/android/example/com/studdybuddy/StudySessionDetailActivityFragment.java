package android.example.com.studdybuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class StudySessionDetailActivityFragment extends Fragment {

    private String testText;

    public StudySessionDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View rootView =  inflater.inflate(R.layout.fragment_study_session_detail, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null ) {
            testText = bundle.getString("sessionName");
            ((TextView) rootView.findViewById(R.id.detail_text_temp))
                    .setText(testText);
        }



        return rootView;
    }
}
