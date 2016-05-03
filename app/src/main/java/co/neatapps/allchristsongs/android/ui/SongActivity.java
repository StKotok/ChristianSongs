package co.neatapps.allchristsongs.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.neatapps.allchristsongs.allchristiansongs.R;
import co.neatapps.allchristsongs.android.util.Constants;
import co.neatapps.allchristsongs.android.util.SongsService;
import co.neatapps.allchristsongs.android.util.Utils;

import static co.neatapps.allchristsongs.android.util.Constants.TEXT_SIZE;
import static co.neatapps.allchristsongs.android.util.Constants.TEXT_SIZE_STEP;

public class SongActivity extends Activity {
    private TextView textView;
    private float originalTextSize;
    private float textSize;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        hideActionBar();

        if (preferences == null) {
            preferences = this.getSharedPreferences(Constants.PREFERENCES_FILE, MODE_PRIVATE);
        }
        textSize = preferences.getFloat(Constants.TEXT_SIZE, Constants.DEFAULT_TEXT_SIZE);

        String songNumber = getIntent().getStringExtra("song") + "\n\n";

        textView = (TextView) findViewById(R.id.songText);
        textSize = textView.getTextSize();
        originalTextSize = textSize;
        textView.setTextColor(Color.BLACK);
        String songText;
        if (songNumber.startsWith("312")) {
            songText = SongsService.songTextSTUB312();

        } else {
            songText = SongsService.songTextSTUB();
        }

        textView.setText(Utils.formatSongText(songText));

        Button btnPlus = (Button) findViewById(R.id.btnFontSizePlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnFontSizePlus:
                        textSize = textSize + TEXT_SIZE_STEP;
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                        break;
                }
            }
        });
        Button btnMinus = (Button) findViewById(R.id.btnFontSizeMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnFontSizeMinus:
                        if (textSize > TEXT_SIZE_STEP) {
                            textSize = textSize - TEXT_SIZE_STEP;
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                        }
                        break;
                }
            }
        });
    }

    private void hideActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        textSizeToPreferences();
    }

    @Override
    protected void onStop() {
        super.onStop();
        textSizeToPreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textSizeToPreferences();
    }

    private void textSizeToPreferences() {
        if (originalTextSize != textSize) {
            SharedPreferences.Editor prefEditor = preferences.edit();
            prefEditor.putFloat(TEXT_SIZE, textSize);
            prefEditor.commit();
        }
    }
}
