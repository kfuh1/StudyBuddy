package android.example.com.studdybuddy.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by John on 8/11/15.
 */
public class SessionDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SESSIONS.db";

    public SessionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static SessionDbHelper instance;


    private static final String[] COLUMNS = {SessionContract.Columns.COLUMN_ID, SessionContract.Columns.COLUMN_PID};
    public static  SessionDbHelper getInstance(Context context){
        if (instance == null){
            instance = new SessionDbHelper(context.getApplicationContext());
        }
        return instance;
    }


    @Override

    public void onCreate(SQLiteDatabase db) {

            String CREATE_SESSION_TABLE = "CREATE TABLE " + SessionContract.TABLE +
                    " (" + SessionContract.Columns.COLUMN_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SessionContract.Columns.COLUMN_PID + " TEXT" + ");";

        db.execSQL(CREATE_SESSION_TABLE);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SESSION");
        onCreate(db);
    }

    public void addSession(String sessionParseID){

        Log.d("addSession", sessionParseID);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SessionContract.Columns.COLUMN_PID, sessionParseID);

        db.insert(SessionContract.TABLE, null, values);
        db.close();


    }





    public String getSessionByPId(String PID){


        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(SessionContract.TABLE, COLUMNS, SessionContract.Columns.COLUMN_PID +" like %?%", new String[] {PID}, null, null, null, null);



        if (cursor != null) {
            cursor.moveToFirst();
        }
        Log.d("getSessionById", cursor.getString(0));

        return cursor.getString(1);

    }

    public List<String> getallPIDS() {

        List<String> pids = new LinkedList<String>();

        String query = "SELECT  * FROM " + SessionContract.TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String tempPid = null;
        if(cursor.moveToFirst()){
            do{
                tempPid = new String();
                tempPid = cursor.getString(1);
                pids.add(tempPid);
            } while(cursor.moveToNext());
        }
        return pids;
    }

    public int countPIDS() {

        int count = 0;
        String query = "SELECT COUNT(*) FROM " + SessionContract.TABLE;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);



        if (cursor != null){
            cursor.moveToFirst();


            count = Integer.parseInt(cursor.getString(0));
        }


        return count;
    }
}
