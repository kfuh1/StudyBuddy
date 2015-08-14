package android.example.com.studdybuddy;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

//TODO  IMPORTANT!!!!!! Make sure to downlaoad "Android Support Library" from, under Extras in the Android SDK Manager
public class MainActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */



    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    private boolean isLarge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (findViewById(R.id.fragment_sessions_large) != null) {

            isLarge = true;
            StudySessionFragment sessionFragment = ((StudySessionFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_sessions_large));
            sessionFragment.setLargeView(isLarge);

        }
        else {
            isLarge = false;

            StudySessionFragment sessionFragment = ((StudySessionFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_sessions));
            sessionFragment.setLargeView(isLarge);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

}
