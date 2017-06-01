package co.neatapps.allchristsongs.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;

import co.neatapps.allchristsongs.allchristiansongs.R;
import co.neatapps.allchristsongs.android.util.Prefs;
import co.neatapps.allchristsongs.android.util.TextViewScaler;
import co.neatapps.allchristsongs.android.util.Utils;

public class SongActivity extends Activity {

    public static final String SONG = "song";

    private TextView vSong;

    private ScaleGestureDetector scaleGestureDetector;

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            scaleGestureDetector.onTouchEvent(event);
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        hideActionBar();

        vSong = (TextView) findViewById(R.id.songText);
        vSong.setText(Utils.formatSongText(getIntent().getStringExtra(SONG) + "\n\n"));

        vSong.setTextSize(TypedValue.COMPLEX_UNIT_PX, Prefs.TEXT_SIZE.getValue(this));
        findViewById(R.id.scrollView).setOnTouchListener(onTouchListener);
        scaleGestureDetector = new ScaleGestureDetector(this, new TextViewScaler(vSong));
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
        Prefs.TEXT_SIZE.setValue(this, (int) vSong.getTextSize());
    }

}
