package co.neatapps.allchristsongs.android.db;

import android.provider.BaseColumns;

public class DbContract {

    public static class SongEntry implements BaseColumns {

        public static final String TABLE_NAME = "songs";

        public static final String COLUMN_LANGUAGE = "song_language";

        public static final String COLUMN_HEADER = "song_header";

        public static final String COLUMN_BODY = "song_body";

    }

}
