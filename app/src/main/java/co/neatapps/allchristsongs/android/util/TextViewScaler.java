package co.neatapps.allchristsongs.android.util;

import android.util.TypedValue;
import android.view.ScaleGestureDetector;
import android.widget.TextView;

public class TextViewScaler extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    private final TextView textView;

    public TextViewScaler(TextView textView) {
        this.textView = textView;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (textView != null) {
            float currentSize = textView.getTextSize();
            float scaleFactor = detector.getScaleFactor();
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentSize * scaleFactor);
        }
        return true;
    }

}
