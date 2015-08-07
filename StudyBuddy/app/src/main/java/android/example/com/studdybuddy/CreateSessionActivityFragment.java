package android.example.com.studdybuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseObject;

/**
 * Created by Kathleen on 8/7/2015.
 */
public class CreateSessionActivityFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        View rootView = inflater.inflate(R.layout.fragment_create_session, container, false);
        return rootView;

    }
}
