package rodriguez.diego.com.lab8_contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class AuthorsProvider extends ContentProvider {

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int AUTHOR = 1;
    private static final int AUTHOR_ID = 2;

    static {
        // authority, path, result code
        sURIMatcher.addURI("com.centennialcollege.comp304.authorsprovider", "/author", AUTHOR);
        sURIMatcher.addURI("com.centennialcollege.comp304.authorsprovider", "/author/#", AUTHOR_ID);
    }

    public AuthorsProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
       // throw new UnsupportedOperationException("Not yet implemented");
        DBHelper dbHelper = new DBHelper(this.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tbl_authors", "lastname=?", new String[]{"Twain"});
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // insert a new author.
        if (sURIMatcher.match(uri) == AUTHOR) {
            DBHelper dbHelper = new DBHelper(this.getContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long rowId = db.insert("tbl_authors", null, values);
            Uri  tmpUri = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(tmpUri, null);
            db.close();
            return tmpUri;
        }
        // here we can add code for other tables

        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        sqlBuilder.setTables("tbl_authors");

        DBHelper dbHelper = new DBHelper(this.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = null;

        //check if the URI looks like this: content://com.centennialcollege.comp304.authorsprovider/author

        if (sURIMatcher.match(uri) == AUTHOR) {
            // return all authors

            // method 1
                c = sqlBuilder.query(
                        db,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            // methid 2
            //c = db.query("tbl_authors", null, null,null, null, null, null);

            // method 3
            //c = db.rawQuery("SELECT * FROM tbl_authors", null);
        }

        //content://com.centennialcollege.comp304.authorsprovider/author/4
        if (sURIMatcher.match(uri) == AUTHOR_ID) {
            // TODO implement searching by author id
        }
        //Register to watch a content URI for changes. This can be the URI of a specific data row (for example, "content://my_provider_type/23"),
        // or a a generic URI for a content type.
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
