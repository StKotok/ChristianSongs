package co.neatapps.allchristsongs.android.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.neatapps.allchristsongs.allchristiansongs.R;
import co.neatapps.allchristsongs.android.model.Song;
import co.neatapps.allchristsongs.android.util.DigestsSelector;
import co.neatapps.allchristsongs.android.util.SongsService;
import co.neatapps.allchristsongs.android.util.Utils;

public class StartActivity extends Activity implements TextWatcher, View.OnClickListener {

    private View vSearchInLayout;
    private View vSearchInContainer;
    private TextView vSearchInInfo;
    private View vSpaceOnTop;
    private MultiAutoCompleteTextView vAutoCompleteTextView; // SpaceTokenizer + http://developer.alexanderklimov.ru/android/views/autocompletetextview.php#multiautocompletetextview
    private ListView vListView;

    private List<Song> songs = new ArrayList<>();
    private SongsAdapter songsAdapter;
    private DigestsSelector digestsSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Utils.hideActionBar(getActionBar());

        // STUB start
        songs.addAll(SongsService.getSongs());
        // STUB end

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initViews();
        setListeners();

        ViewGroup vDigests = (ViewGroup) findViewById(R.id.search_in_container);
        vSearchInInfo = (TextView) findViewById(R.id.search_in_info);
        digestsSelector = new DigestsSelector(vDigests, vSearchInInfo, getLayoutInflater());

        vListView = (ListView) findViewById(R.id.list);
        songsAdapter = new SongsAdapter(this, songs);
        vListView.setAdapter(songsAdapter);
    }

    private void initViews() {
        vSearchInLayout = findViewById(R.id.search_in_layout);
        vSearchInContainer = findViewById(R.id.search_in_container);
        vSpaceOnTop = findViewById(R.id.space_on_autocomplete_top);
        vAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
    }

    private void setListeners() {
        vSearchInLayout.setOnClickListener(this);
        vSpaceOnTop.setOnClickListener(this);
        vAutoCompleteTextView.addTextChangedListener(this);
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
            case R.id.search_in_layout:
            case R.id.space_on_autocomplete_top:
                if (vSearchInContainer.getVisibility() != View.VISIBLE) {
                    showSearchInLayout();
                } else {
                    vSearchInLayout.setSelected(false);
                    vSearchInContainer.setVisibility(View.GONE);
                }
                break;

            case R.id.hint:
                Toast.makeText(this, R.string.search_hint, Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void showSearchInLayout() {
        vSearchInLayout.setSelected(true);
        vSearchInContainer.setVisibility(View.VISIBLE);
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
