package android.example.com.studdybuddy.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by John on 8/11/15.
 */
public class SessionProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SessionDbHelper mOpenHelper;

    static final int SESSION = 100;



    @Override
    public boolean onCreate() {
        mOpenHelper = new SessionDbHelper(getContext());
        return true;
    }


    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SessionContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, SessionContract.PATH_SESSION, SESSION);



        return matcher;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)){

            case(SESSION):{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SessionContract.SessionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case SESSION:
                return SessionContract.SessionEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){
            case(SESSION):{

                long _id = db.insert(SessionContract.SessionEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = SessionContract.SessionEntry.buildSessionUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;

            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {



        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
//        // this makes delete all rows return the number of rows deleted
    if ( null == selection ) selection = "1";
      switch (match) {
          case SESSION:
               rowsDeleted = db.delete(
                       SessionContract.SessionEntry.TABLE_NAME, selection, selectionArgs);
             break;
//
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);}
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case SESSION:

                rowsUpdated = db.update(SessionContract.SessionEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
