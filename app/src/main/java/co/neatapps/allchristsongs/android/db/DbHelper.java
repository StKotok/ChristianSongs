/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + DbContract.SongEntry.TABLE_NAME + " (" +
                DbContract.SongEntry._ID + " INTEGER PRIMARY KEY," +
                DbContract.SongEntry.COLUMN_LOCATION_SETTING + " TEXT UNIQUE NOT NULL, " +
                DbContract.SongEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
                DbContract.SongEntry.COLUMN_COORD_LAT + " REAL NOT NULL, " +
                DbContract.SongEntry.COLUMN_COORD_LONG + " REAL NOT NULL" +
                ");";

        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + DbContract.DigestEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                DbContract.DigestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                DbContract.DigestEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL, " +
                DbContract.DigestEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                DbContract.DigestEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
                DbContract.DigestEntry.COLUMN_WEATHER_ID + " INTEGER NOT NULL," +

                DbContract.DigestEntry.COLUMN_MIN_TEMP + " REAL NOT NULL, " +
                DbContract.DigestEntry.COLUMN_MAX_TEMP + " REAL NOT NULL, " +

                DbContract.DigestEntry.COLUMN_HUMIDITY + " REAL NOT NULL, " +
                DbContract.DigestEntry.COLUMN_PRESSURE + " REAL NOT NULL, " +
                DbContract.DigestEntry.COLUMN_WIND_SPEED + " REAL NOT NULL, " +
                DbContract.DigestEntry.COLUMN_DEGREES + " REAL NOT NULL, " +

                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + DbContract.DigestEntry.COLUMN_LOC_KEY + ") REFERENCES " +
                DbContract.SongEntry.TABLE_NAME + " (" + DbContract.SongEntry._ID + "), " +

                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + DbContract.DigestEntry.COLUMN_DATE + ", " +
                DbContract.DigestEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.SongEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.DigestEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
