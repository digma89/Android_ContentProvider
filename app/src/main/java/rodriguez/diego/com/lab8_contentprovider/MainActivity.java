package rodriguez.diego.com.lab8_contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentValues values = new ContentValues();
        values.put("firstname", "J.K._app1");

        values.put("lastname", "Rowling");
        getContentResolver().insert(Uri.parse("content://com.centennialcollege.comp304.authorsprovider/author"), values);

        values.clear();
        values.put("firstname", "Mark_app1");
        values.put("lastname", "Twain");
        getContentResolver().insert(Uri.parse("content://com.centennialcollege.comp304.authorsprovider/author"), values);

        Cursor c = getContentResolver().query(
                Uri.parse("content://com.centennialcollege.comp304.authorsprovider/author"), null, null, null, null);

        String result = "";
        while (c.moveToNext()) {
            result += c.getString(1);
        }
        System.out.println("RESULT PROVIDER APP : " + result);
    }
}
