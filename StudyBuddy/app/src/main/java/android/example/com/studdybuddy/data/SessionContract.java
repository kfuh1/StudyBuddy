package android.example.com.studdybuddy.data;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by John on 8/11/15.
 */
public class SessionContract {

    public static final String DATABASE_NAME = "android.example.com.studdybuddy.db.SESSIONS";
    public static final int DB_VERSION  = 1;
    public static final String TABLE = "session";


    public static final String CONTENT_AUTHORITY = "android.example.com.studdybuddy.mySessions";

    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + TABLE);

    public static final int SESSION_LIST = 1;
    public static final int SESSION_ITEM  = 2;
    public static final String CONTNET_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/com.sessionsDB/"+TABLE;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+ "/com/sessionsDB" + TABLE;


        public class Columns {
                    public static final String COLUMN_ID = "ID";

        public static final String COLUMN_PID = "PID";
        }
//
}
