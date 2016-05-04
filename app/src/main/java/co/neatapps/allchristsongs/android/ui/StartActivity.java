package co.neatapps.allchristsongs.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.neatapps.allchristsongs.allchristiansongs.R;
import co.neatapps.allchristsongs.android.model.Song;
import co.neatapps.allchristsongs.android.util.Utils;

public class StartActivity extends Activity implements TextWatcher, View.OnClickListener {

    private AutoCompleteTextView autoCompleteTextView;
    private List<Song> songs = new ArrayList<>();
    private ListView listView;
    private SongsAdapter songsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        hideActionBar();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.addTextChangedListener(this);

        listView = (ListView) findViewById(R.id.list);
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
            case R.id.hint:
                Toast.makeText(this, R.string.search_hint, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        int length = autoCompleteTextView.getText().length();
        if (length > 0) {
            autoCompleteTextView.setText("");
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
