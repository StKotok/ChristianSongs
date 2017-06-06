package co.neatapps.allchristsongs.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.neatapps.allchristsongs.android.model.Digest;
import co.neatapps.allchristsongs.android.model.Song;

import static android.provider.BaseColumns._ID;
import static co.neatapps.allchristsongs.android.db.DbContract.SongEntry.COLUMN_BODY;
import static co.neatapps.allchristsongs.android.db.DbContract.SongEntry.COLUMN_HEADER;
import static co.neatapps.allchristsongs.android.db.DbContract.SongEntry.COLUMN_INFO;
import static co.neatapps.allchristsongs.android.db.DbContract.SongEntry.COLUMN_NUMBER;
import static co.neatapps.allchristsongs.android.db.DbContract.SongEntry.COLUMN_TONE;
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

    public List<Song> getAllSongs() {
        open();
        Cursor c = getAllData();
        ArrayList<Song> songs = new ArrayList<>();
        if (c.moveToFirst()) {
            int colNumber = c.getColumnIndex(COLUMN_NUMBER);
            int colInfo = c.getColumnIndex(COLUMN_INFO);
            int colTone = c.getColumnIndex(COLUMN_TONE);
            int colHeader = c.getColumnIndex(COLUMN_HEADER);
            int colBody = c.getColumnIndex(COLUMN_BODY);

            do {
                int number = Integer.parseInt(c.getString(colNumber));
                String info = c.getString(colInfo);
                String tone = c.getString(colTone);
                String header = c.getString(colHeader);
                String body = c.getString(colBody);

                Digest digest = new Digest(Digest.DigestType.revivalSong, number);
                songs.add(new Song(digest, header, body, tone, info));
            } while (c.moveToNext());
        }
        close();
        return songs;
    }

    public void addRec(String number, String info, String tone, String header, String body) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NUMBER, number);
        cv.put(COLUMN_INFO, info);
        cv.put(COLUMN_TONE, tone);
        cv.put(COLUMN_HEADER, header);
        cv.put(COLUMN_BODY, body);
        mDB.insert(TABLE_NAME, null, cv);
    }

    public void addSongs(List<Song> songs) {
        open();
        for (Song song : songs) {
            String number = String.valueOf(song.getDigests().get(0).getNumber());
            addRec(number, song.getMoreInfo(), song.getTone(), song.getHeader(), song.getBody());
        }
        close();
    }

    public void delRec(long id) {
        mDB.delete(TABLE_NAME, _ID + " = " + id, null);
    }

    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

}
