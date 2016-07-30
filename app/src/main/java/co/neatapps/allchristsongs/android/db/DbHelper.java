package co.neatapps.allchristsongs.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SONGS_TABLE = "CREATE TABLE " + DbContract.SongEntry.TABLE_NAME + " (" +
                DbContract.SongEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbContract.SongEntry.COLUMN_LANGUAGE + " TEXT NOT NULL, " +
                DbContract.SongEntry.COLUMN_HEADER + " TEXT NOT NULL, " +
                DbContract.SongEntry.COLUMN_BODY + " TEXT NOT NULL, " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_SONGS_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.SongEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
