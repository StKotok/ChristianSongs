package co.neatapps.allchristsongs.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static co.neatapps.allchristsongs.android.db.DbContract.SongEntry.COLUMN_BODY;
import static co.neatapps.allchristsongs.android.db.DbContract.SongEntry.COLUMN_HEADER;
import static co.neatapps.allchristsongs.android.db.DbContract.SongEntry.COLUMN_LANGUAGE;
import static co.neatapps.allchristsongs.android.db.DbContract.SongEntry.TABLE_NAME;

public class DataBase {

    private final Context mCtx;

    private DbHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DataBase(Context ctx) {
        mCtx = ctx;
    }

    public void open() {
        mDBHelper = new DbHelper(mCtx);
        mDB = mDBHelper.getWritableDatabase();
    }

    public Cursor getAllData() {
        return mDB.query(TABLE_NAME, null, null, null, null, null, null);
    }

//    public Cursor getSongsByName(Lis) {
//        return mDB.query(TABLE_NAME, null, null, null, null, null, null);
//    }

    public void addRec(String txt, int img) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LANGUAGE, txt);
        cv.put(COLUMN_HEADER, txt);
        cv.put(COLUMN_BODY, txt);
        mDB.insert(TABLE_NAME, null, cv);
    }

    public void delRec(long id) {
        mDB.delete(TABLE_NAME, _ID + " = " + id, null);
    }

    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

}
