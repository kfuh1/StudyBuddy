    package android.example.com.studdybuddy.data;

    import android.content.ContentProvider;
    import android.content.ContentUris;
    import android.content.ContentValues;
    import android.content.UriMatcher;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteQueryBuilder;
    import android.net.Uri;
    import android.database.SQLException;

    /**
     * Created by John on 8/11/15.
     */
    public class SessionProvider extends ContentProvider {

      private SQLiteDatabase db;
        private SessionDbHelper sessionDbHelper;
        public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


        static {
            uriMatcher.addURI(SessionContract.CONTENT_AUTHORITY,SessionContract.TABLE,SessionContract.SESSION_LIST);
            uriMatcher.addURI(SessionContract.CONTENT_AUTHORITY,SessionContract.TABLE+"/#",SessionContract.SESSION_ITEM);
        }


        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {
            return 0;
        }

        @Override
        public boolean onCreate() {
            boolean ret = true;
            sessionDbHelper = new SessionDbHelper(getContext());
            db = sessionDbHelper.getWritableDatabase();

            if (db == null) {
                ret = false;
            }

            if (db.isReadOnly()) {
                db.close();
                db = null;
                ret = false;
            }

            return ret;
        }

        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            qb.setTables(SessionContract.TABLE);

            switch (uriMatcher.match(uri)) {
                case SessionContract.SESSION_LIST:
                    break;

                case SessionContract.SESSION_ITEM:
                    qb.appendWhere(SessionContract.Columns.COLUMN_ID + " = "+ uri.getLastPathSegment());
                    break;

                default:
                    throw new IllegalArgumentException("Invalid URI: " + uri);
            }

            Cursor cursor = qb.query(db,projection,selection,selectionArgs,null,null,null);

            return cursor;
        }

        @Override
        public String getType(Uri uri) {
            switch (uriMatcher.match(uri)) {
                case SessionContract.SESSION_LIST:
                    return SessionContract.CONTNET_TYPE;

                case SessionContract.SESSION_ITEM:
                    return SessionContract.CONTENT_ITEM_TYPE;

                default:
                    throw new IllegalArgumentException("Invalid URI: "+uri);
            }
        }

        @Override
        public Uri insert(Uri uri, ContentValues values) {
            if (uriMatcher.match(uri) != SessionContract.SESSION_LIST) {
                throw new IllegalArgumentException("Invalid URI: "+uri);
            }

            long id = db.insert(SessionContract.TABLE,null,values);

            if (id>0) {
                return ContentUris.withAppendedId(uri, id);
            }
            throw new SQLException("Error inserting into table: "+SessionContract.TABLE);
        }

        @Override
        public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            int updated = 0;
            switch (uriMatcher.match(uri)) {
                case SessionContract.SESSION_LIST:
                    db.update(SessionContract.TABLE,values,selection,selectionArgs);
                    break;

                case SessionContract.SESSION_ITEM:
                    String where = SessionContract.Columns.COLUMN_ID + " = " + uri.getLastPathSegment();
                    if (!selection.isEmpty()) {
                        where += " AND "+selection;
                    }
                    updated = db.update(SessionContract.TABLE,values,where,selectionArgs);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid URI: "+uri);
            }

            return updated;

        }
    }