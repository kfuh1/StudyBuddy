package android.example.com.studdybuddy.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.example.com.studdybuddy.data.SessionContract.SessionEntry;
/**
 * Created by John on 8/11/15.
 */
public class SessionDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "sessions.db";

    public SessionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override

    public void onCreate(SQLiteDatabase db) {

            final String SQL_CREATE_SESSION_TABLE = "CREATE TABLE" + SessionEntry.TABLE_NAME + " (" +
                    SessionEntry._ID + " INTEGER PRIMARY KEY," +
                    SessionEntry.SESSIONNAME + " TEXT UNIQUE NOT NULL, " +
                    SessionEntry.SESSIONDESC + " TEXT UNIQUE NOT NULL, " +
                    SessionEntry.SESSIONLOCATION + " TEXT  NOT NULL, " +
                    SessionEntry.SESSIONUSER + " TEXT UNIQUE NOT NULL, " +
                    SessionEntry.SESSIONSUBJECT + "TEXT NOT NULL" +
                    " );";

            db.execSQL(SQL_CREATE_SESSION_TABLE);

    }

    public void resetDB(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + SessionEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SessionEntry.TABLE_NAME);
        onCreate(db);
    }
}
