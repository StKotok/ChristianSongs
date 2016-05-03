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

import co.neatapps.allchristsongs.allchristiansongs.R;

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
        // todo
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hint:
                // todo
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
