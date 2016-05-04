package co.neatapps.allchristsongs.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import co.neatapps.allchristsongs.allchristiansongs.R;
import co.neatapps.allchristsongs.android.util.Utils;

public class StartActivity extends Activity implements TextWatcher, View.OnClickListener {
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> autoCompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        hideSystemBar(getWindow());
        setContentView(R.layout.activity_start);
        hideActionBar();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.addTextChangedListener(this);
//        autoCompleteTextView.setAdapter(new SongsAdapter());

        autoCompleteTextView.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable arg0) {
        // todo
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // todo
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!Utils.isNaturalNumber(s)) {
            showSongsByString(s.toString());
        } else {
            showSongsByNumder(s.toString());
        }
    }

    private void showSongsByString(String s) {
        // todo
        Toast.makeText(this, "Строка " + s, Toast.LENGTH_SHORT).show();
    }

    private void showSongsByNumder(String number) {
        // todo
        Toast.makeText(this, "#" + number, Toast.LENGTH_SHORT).show();
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

}
