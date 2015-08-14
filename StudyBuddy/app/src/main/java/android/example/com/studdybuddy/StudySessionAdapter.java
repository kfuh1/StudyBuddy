package android.example.com.studdybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by John on 7/26/15.
 */

//Custom Adapter to take in a StudySession Object and display its contents in teh list view
public class StudySessionAdapter extends ArrayAdapter<StudySession> {
    public StudySessionAdapter(Context context, ArrayList<StudySession> sessions) {
        super(context, 0, sessions);
    }

    private boolean useLarge;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudySession studySession = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_studysession, parent, false);
        }

        if (!useLarge) {
            TextView sessionName = (TextView) convertView.findViewById(R.id.list_item_studysession_name_textview);
            TextView sessionDesc = (TextView) convertView.findViewById(R.id.list_item_studysession_desc_textview);
            TextView sessionTil = (TextView) convertView.findViewById(R.id.list_item_studdysession_tilTime_textview);
            TextView sessionCreate = (TextView) convertView.findViewById(R.id.list_item_studdysession_creationTime_textview);

            sessionName.setText(studySession.getSessionName());
            sessionDesc.setText(studySession.getLocationName());
            sessionTil.setText("In " + studySession.getTimeToMeet());
            sessionCreate.setText("From " + studySession.getCreateTime());
        }
        else{
            TextView sessionName = (TextView) convertView.findViewById(R.id.list_item_studysession_name_textview);
            TextView sessionDesc = (TextView) convertView.findViewById(R.id.list_item_studysession_desc_textview);
            TextView sessionLoc = (TextView) convertView.findViewById(R.id.list_item_studysession_loc_textview);
            TextView sessionSubj = (TextView) convertView.findViewById(R.id.list_item_studysession_subj_textview);
            TextView sessionTil = (TextView) convertView.findViewById(R.id.list_item_studdysession_tilTime_textview);
            TextView sessionCreate = (TextView) convertView.findViewById(R.id.list_item_studdysession_creationTime_textview);

            sessionName.setText(studySession.getSessionName());
            sessionDesc.setText(studySession.getSessionDescription());
            sessionLoc.setText(studySession.getLocationName());
            sessionSubj.setText(studySession.getSubjectType());
            sessionTil.setText("In " + studySession.getTimeToMeet());
            sessionCreate.setText("From " + studySession.getCreateTime());
        }
        return convertView;
    }

    public void setUseLarge(boolean status){
        useLarge = status;
    }
}
