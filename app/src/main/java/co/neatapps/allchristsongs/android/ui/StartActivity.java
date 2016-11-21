package co.neatapps.allchristsongs.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.neatapps.allchristsongs.allchristiansongs.R;
import co.neatapps.allchristsongs.android.model.Digest;
import co.neatapps.allchristsongs.android.model.Song;
import co.neatapps.allchristsongs.android.util.Constants;
import co.neatapps.allchristsongs.android.util.Utils;

public class StartActivity extends Activity implements TextWatcher, View.OnClickListener {

    private View vSearchInLayout;
    private View vSearchInContainer;
    private TextView vSearchInInfo;
    private TextView vSearchInRevival;
    private TextView vSearchInMaykop;
    private View vSpaceOnTop;
    private MultiAutoCompleteTextView vAutoCompleteTextView; // SpaceTokenizer + http://developer.alexanderklimov.ru/android/views/autocompletetextview.php#multiautocompletetextview
    private ListView listView;

    private List<Song> songs = new ArrayList<>();
    private SongsAdapter songsAdapter;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        hideActionBar();

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initViews();
        setListeners();

        listView = (ListView) findViewById(R.id.list);
    }

    private void initViews() {
        vSearchInLayout = findViewById(R.id.search_in_layout);
        vSearchInInfo = (TextView) findViewById(R.id.search_in_info);
        vSearchInContainer = findViewById(R.id.search_in_container);
        vSearchInRevival = (TextView) findViewById(R.id.chk_revival);
        vSearchInMaykop = (TextView) findViewById(R.id.chk_maykop);
        vSpaceOnTop = findViewById(R.id.space_on_autocomplete_top);
        vAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        vSearchInRevival.setSelected(sharedPref.getBoolean(Constants.REVIVAL_SELECTED, true));
        vSearchInMaykop.setSelected(sharedPref.getBoolean(Constants.MAYKOP_SELECTED, true));

        refreshSearchInInfoText();
    }

    private void setListeners() {
        vSearchInLayout.setOnClickListener(this);
        vSearchInRevival.setOnClickListener(this);
        vSearchInMaykop.setOnClickListener(this);
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
            songsAdapter = new SongsAdapter();
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

            case R.id.chk_revival:
                processSelecting(vSearchInRevival, Constants.REVIVAL_SELECTED);
                break;

            case R.id.chk_maykop:
                processSelecting(vSearchInMaykop, Constants.MAYKOP_SELECTED);
                break;

            case R.id.hint:
                Toast.makeText(this, R.string.search_hint, Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void processSelecting(TextView digestView, String digestPrefsKey) {

        digestView.setSelected(!digestView.isSelected());

        if (countSelectedDigests() == 0) {
            showSearchInLayout();
            Toast.makeText(this, R.string.select_at_least_one, Toast.LENGTH_LONG).show();
            digestView.setSelected(true);
        }

        Utils.saveBoolean(digestView.isSelected(), digestPrefsKey, sharedPref);

        refreshSearchInInfoText();

        // todo: Refresh search results

        Log.d(this.getClass().getSimpleName(), "\t\t" + sharedPref.getBoolean(Constants.REVIVAL_SELECTED, false) + "\t" + sharedPref.getBoolean(Constants.MAYKOP_SELECTED, false));
    }

    private void refreshSearchInInfoText() {
        int count = countSelectedDigests();
        String msg;
        if (count == Digest.DigestType.length()) {
            msg = getString(R.string.selected_all);
        } else if (count == 0) {
            msg = getString(R.string.selected_none);
        } else {
            msg = getString(R.string.selected_several);
        }
        vSearchInInfo.setText(msg);
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

    //--- Utils ---------------------------------------------------

    private void hideSystemBar(Window window) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void hideActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private int countSelectedDigests() {
        int count = 0;
        if (vSearchInRevival.isSelected()) ++count;
        if (vSearchInMaykop.isSelected()) ++count;
        return count;
    }

    public class SongsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return songs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_songs_list, parent, false);

                ViewHolder holder = new ViewHolder();
                holder.header = (TextView) convertView.findViewById(R.id.item_songs_list_header);
                holder.body = (TextView) convertView.findViewById(R.id.item_songs_list_body);

                convertView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();

            holder.header.setText(songs.get(position).getHeader());
            holder.header.setText(songs.get(position).getBody());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(StartActivity.this, SongActivity.class));
                }
            });
            return convertView;
        }

        private class ViewHolder {
            TextView header;
            TextView body;
        }

    }

}
