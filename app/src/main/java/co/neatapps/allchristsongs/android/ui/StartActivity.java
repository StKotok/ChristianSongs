package co.neatapps.allchristsongs.android.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.neatapps.allchristsongs.allchristiansongs.R;
import co.neatapps.allchristsongs.android.db.DataBase;
import co.neatapps.allchristsongs.android.db.DbContract;
import co.neatapps.allchristsongs.android.model.Song;
import co.neatapps.allchristsongs.android.util.DigestsSelector;
import co.neatapps.allchristsongs.android.util.SongsParser;
import co.neatapps.allchristsongs.android.util.Utils;

public class StartActivity extends Activity implements TextWatcher, View.OnClickListener {

    public static final String CURRENT_DB_PATH = "//data//" + "PackageName" + "//databases//" + "DatabaseName";
    public static final String BACKUP_FOLDER_DATABASE_NAME = "/BackupFolder/DatabaseName";

    private MultiAutoCompleteTextView vAutoCompleteTextView; // SpaceTokenizer + http://developer.alexanderklimov.ru/android/views/autocompletetextview.php#multiautocompletetextview

    private List<Song> songs = new ArrayList<>();
    private SongsAdapter songsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Utils.hideActionBar(getActionBar());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DigestsSelector digestsSelector = new DigestsSelector(StartActivity.this);

        vAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        vAutoCompleteTextView.addTextChangedListener(this);

        deleteDatabase(DbContract.SongEntry.TABLE_NAME);
        DataBase db = new DataBase(this);
        db.addSongs(SongsParser.parse(this));

        songs.addAll(db.getAllSongs());

        ListView vListView = (ListView) findViewById(R.id.list);
        songsAdapter = new SongsAdapter(this, songs);
        vListView.setAdapter(songsAdapter);
    }

    @Override
    public void afterTextChanged(Editable arg0) { /* nothing */ }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* nothing */ }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        showHintForTyping(s);

        if (!Utils.isNaturalNumber(s)) {
            showSongsByString(s.toString());
        } else {
            showSongsByNumder(s.toString());
        }
    }

    private void showSongsByString(String string) {
        // todo: Request songs from DB and set it to "songs" field
        updateList();
    }

    private void showSongsByNumder(String number) {
        // todo: Request songs from DB and set it to "songs" field
        updateList();
    }

    private void updateList() {
        if (songsAdapter == null) {
            songsAdapter = new SongsAdapter(this, songs);
        } else {
            songsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hint:
                Toast.makeText(this, R.string.search_hint, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        int length = vAutoCompleteTextView.getText().length();
        if (length > 0) {
            vAutoCompleteTextView.setText("");
            return;
        }
        super.onBackPressed();
    }

    private void showHintForTyping(CharSequence s) {
        int wordLength = 5;
        String[] split = s.toString().split(" ");
        int longWordsCount = 0;
        for (String word : split) {
            if (word.length() > wordLength) {
                longWordsCount++;
            }
        }
        if (longWordsCount > 2) {
            Toast.makeText(this, R.string.hint_for_typing, Toast.LENGTH_LONG).show();
        }
    }

}
