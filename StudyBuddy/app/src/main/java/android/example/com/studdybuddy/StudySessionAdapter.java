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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudySession studySession = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_studysession, parent, false);
        }
        TextView sessionName = (TextView) convertView.findViewById(R.id.list_item_studysession_name_textview);
        TextView sessionDesc = (TextView) convertView.findViewById(R.id.list_item_studysession_desc_textview);

        sessionName.setText(studySession.getSessionName());
        sessionDesc.setText(studySession.getSessionDescription());
        return convertView;
    }
}