package android.example.com.studdybuddy;

import android.example.com.studdybuddy.data.SessionDbHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


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
    private String mPID;

    static final String DETAIL_URI = "URI";
    public StudySessionDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_study_session_detail, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null ) {
            /* get and set values as they appeared on create screen */
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
            /* time until meeting based on creation time */
            mTimeToMeet = bundle.getString("timeToMeet");
            ((TextView) rootView.findViewById(R.id.timeToMeetText))
                    .setText(mTimeToMeet);
            mCreateTime = bundle.getString("createdAt");
            ((TextView) rootView.findViewById(R.id.createTimeText))
                    .setText(mCreateTime);
            mPID = bundle.getString("pid");

            List<String> tempPID = SessionDbHelper.getInstance(getActivity()).getallPIDS();
            for (int i = 0; i < tempPID.size(); i++) {
                if (mPID.equals(tempPID.get(i))){
                    ((TextView) rootView.findViewById(R.id.titleText)) /*detail_text_temp*/
                            .setTextColor(Color.parseColor("#673AB7"));
                    ((TextView) rootView.findViewById(R.id.descriptionText)) /*detail_text_temp*/
                            .setTextColor(Color.parseColor("#673AB7"));
                    ((TextView) rootView.findViewById(R.id.classText)) /*detail_text_temp*/
                            .setTextColor(Color.parseColor("#673AB7"));

                    ((TextView) rootView.findViewById(R.id.timeToMeetText)) /*detail_text_temp*/
                            .setTextColor(Color.parseColor("#673AB7"));
                    ((TextView) rootView.findViewById(R.id.createTimeText)) /*detail_text_temp*/
                            .setTextColor(Color.parseColor("#673AB7"));

                    ((TextView) rootView.findViewById(R.id.locationText)) /*detail_text_temp*/
                            .setTextColor(Color.parseColor("#673AB7"));
                }
            }



        }
        return rootView;
    }
}
