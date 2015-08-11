package android.example.com.studdybuddy.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by John on 8/11/15.
 */
public class SessionContract {

    public static final String CONTENT_AUTHORITY = "android.example.com.studdybuddy.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_SESSION = "session";

    public static final class SessionEntry implements BaseColumns{

       public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SESSION).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SESSION;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SESSION;

        // Table name
        public static final String TABLE_NAME = "sessions";

        // The location setting string is what will be sent to openweathermap
        // as the location query.
        //public static final String COLUMN_LOCATION_SETTING = "location_setting";

//        // Human readable location string, provided by the API.  Because for styling,
//        // "Mountain View" is more recognizable than 94043.
//        public static final String COLUMN_CITY_NAME = "city_name";
//
//        // In order to uniquely pinpoint the location on the map when we launch the
//        // map intent, we store the latitude and longitude as returned by openweathermap.
//        public static final String COLUMN_COORD_LAT = "coord_lat";
//        public static final String COLUMN_COORD_LONG = "coord_long";

        public static final String SESSIONNAME  = "sessionName";
        public static final String SESSIONDESC  = "sessionDESC";
        public static final String SESSIONLOCATION = "sessionLocation";
        public static final String SESSIONUSER = "sessionUser";
        public static final String SESSIONSUBJECT = "sessionSubject";



        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
