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
    private String mTitle;
    private String mDesc;
    private String mSubject;
    private String mLocation;
    private String mTimeToMeet;
    private String mCreateTime;

    public StudySessionDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View rootView =  inflater.inflate(R.layout.fragment_study_session_detail, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null ) {
            mTitle= bundle.getString("sessionName");
            ((TextView) rootView.findViewById(R.id.titleText)) /*detail_text_temp*/
                    .setText(mTitle);
            mDesc = bundle.getString("sessionDescription");
            ((TextView) rootView.findViewById(R.id.descriptionText))
                    .setText(mDesc);
            mSubject = bundle.getString("subjectType");
            ((TextView) rootView.findViewById(R.id.classText))
                    .setText(mSubject);
            mLocation = bundle.getString("locationName");
            ((TextView) rootView.findViewById(R.id.locationText))
                    .setText(mLocation);
            mTimeToMeet = bundle.getString("timeToMeet");
            ((TextView) rootView.findViewById(R.id.timeToMeetText))
                    .setText(mTimeToMeet);
            mCreateTime = bundle.getString("createdAt");
            ((TextView) rootView.findViewById(R.id.createTimeText))
                    .setText(mCreateTime);

        }



        return rootView;
    }
}
